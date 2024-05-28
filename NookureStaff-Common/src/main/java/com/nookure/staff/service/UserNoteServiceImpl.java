package com.nookure.staff.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.config.bukkit.partials.messages.NoteMessages;
import com.nookure.staff.api.model.NoteModel;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.service.UserNoteService;
import com.nookure.staff.api.util.Object2Text;
import io.ebean.Database;
import io.ebean.PagedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

@Singleton
public class UserNoteServiceImpl implements UserNoteService {
  @Inject
  private AtomicReference<Database> db;
  @Inject
  private ConfigurationContainer<NoteMessages> noteMessages;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;

  private static final int PER_PAGE = 1;

  @Override
  public void addNote(@NotNull CommandSender staff, @NotNull String targetUsername, @NotNull String note, boolean showOnJoin, boolean showOnlyToAdministrators) {
    requireNonNull(staff, "The staff member cannot be null");
    requireNonNull(targetUsername, "The target username cannot be null");
    requireNonNull(note, "The note cannot be null");

    PlayerModel player = getByUsername(targetUsername);

    if (player == null) {
      staff.sendMiniMessage(messages.get().playerNotFound(), "player", targetUsername);
      return;
    }

    NoteModel noteModel = new NoteModel()
        .setPlayer(player)
        .setNote(note)
        .setShowOnJoin(showOnJoin)
        .setShowOnlyToAdministrators(showOnlyToAdministrators);

    staff.sendMiniMessage(noteMessages.get().savingData());
    noteModel.save();
    staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().successfullyCreated(), player, noteModel));
    displayNote(staff, player, noteModel);
  }

  @Override
  public void removeNote(@NotNull CommandSender staff, @NotNull Long id) {
    NoteModel note = db.get().find(NoteModel.class).where().eq("id", id).findOne();

    if (note == null) {
      staff.sendMiniMessage(noteMessages.get().noteNotFound(), "note.id", id.toString());
      return;
    }

    staff.sendMiniMessage(noteMessages.get().deletingNote());

    note.delete();
    staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().noteDeleted(), note));
  }

  @Override
  public void displayNotes(@NotNull CommandSender staff, @NotNull String targetUsername, int page) {
    PlayerModel player = getByUsername(targetUsername);

    if (player == null) {
      staff.sendMiniMessage(messages.get().playerNotFound(), "player", targetUsername);
      return;
    }

    displayNotesChat(staff, player, page);
  }

  public void displayNotesChat(@NotNull CommandSender staff, @NotNull PlayerModel player, int page) {
    PagedList<NoteModel> notes = db.get().find(NoteModel.class)
        .where()
        .eq("player", player)
        .orderBy("whenCreated desc")
        .setFirstRow(page * PER_PAGE)
        .setMaxRows(PER_PAGE)
        .findPagedList();

    if (notes.getTotalCount() == 0) {
      staff.sendMiniMessage(noteMessages.get().userWithoutNotes(), "player", player.getName());
      return;
    }

    staff.sendMiniMessage(
        Object2Text.replaceText(
            getNotesPaginationHeader(player, notes, page),
            player
        )
    );

    notes.getList().forEach(note -> displayNote(
        staff,
        player,
        note
    ));

    if (staff.isConsole()) return;

    staff.sendMiniMessage(
        Object2Text.replaceText(
            getNotesPaginationFooter(notes, page),
            player
        )
    );
  }

  private String getNotesPaginationFooter(@NotNull PagedList<NoteModel> notes, int page) {
    StringBuilder noteFooterMessage = new StringBuilder();

    if (notes.hasPrev()) {
      noteFooterMessage.append(
          noteMessages.get().paginationPrevious()
              .replace("{prev_page}", String.valueOf(page - 1))
      );
    }

    for (int i = 0; i < notes.getTotalPageCount(); i++) {
      if (i == page) {
        noteFooterMessage.append(noteMessages.get().currentPaginationNumber().replace("{page}", String.valueOf(i)));
      } else {
        noteFooterMessage.append(noteMessages.get().paginationFooterNumber().replace("{page}", String.valueOf(i)));
      }

      if (i < notes.getTotalPageCount() - 1) {
        noteFooterMessage.append(noteMessages.get().separator());
      }
    }

    if (notes.hasNext()) {
      noteFooterMessage.append(
          noteMessages.get().paginationNext()
              .replace("{next_page}", String.valueOf(page + 1))
      );
    }

    return noteFooterMessage.toString();
  }

  @Override
  public void toggleShowOnJoin(@NotNull CommandSender staff, @NotNull Long id) {
    NoteModel note = db.get().find(NoteModel.class).where().eq("id", id).findOne();

    if (note == null) {
      staff.sendMiniMessage(noteMessages.get().noteNotFound(), "note.id", id.toString());
      return;
    }

    note.setShowOnJoin(!note.getShowOnJoin());
    note.save();

    staff.sendMiniMessage(Object2Text.replaceText(
        note.getShowOnJoin() ? noteMessages.get().setShowOnJoinOn() : noteMessages.get().setShowOnJoinOff(),
        note)
    );
  }

  private String getNotesPaginationHeader(@NotNull PlayerModel player, PagedList<NoteModel> notes, int page) {
    return noteMessages.get().paginationHeader()
        .replace("{player.name}", player.getName())
        .replace("{page}", String.valueOf(page + 1))
        .replace("{total_pages}", String.valueOf(notes.getTotalPageCount()));
  }

  @Override
  public void displayNote(@NotNull CommandSender staff, @NotNull PlayerModel player, @NotNull NoteModel note) {
    staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().noteDisplayHeader(), player, note));

    if (staff.hasPermission(Permissions.STAFF_NOTES_ADMIN)) {
      staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().noteDisplayBodyAdmin(), player, note));
    } else {
      staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().noteDisplayBody(), player, note));
    }

    if (staff.hasPermission(Permissions.STAFF_NOTES_ADMIN) && staff.isPlayer()) {
      staff.sendMiniMessage(Object2Text.replaceText(noteMessages.get().noteDisplayFooter(), player, note));
    }
  }

  @Nullable
  private PlayerModel getByUsername(@NotNull String username) {
    requireNonNull(username, "The username cannot be null");
    return db.get().find(PlayerModel.class).where().eq("name", username).findOne();
  }
}
