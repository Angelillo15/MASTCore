package com.nookure.staff.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.model.NoteModel;
import com.nookure.staff.api.model.PlayerModel;
import com.nookure.staff.api.service.UserNoteService;
import com.nookure.staff.api.util.Object2Text;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

@Singleton
public class UserNoteServiceImpl implements UserNoteService {
  @Inject
  private AtomicReference<Database> db;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;

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

    staff.sendMiniMessage(messages.get().note.savingData());
    noteModel.save();
    staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.successfullyCreated(), player, noteModel));
    displayNote(staff, player, noteModel);
  }

  @Override
  public void removeNote(@NotNull CommandSender staff, @NotNull Long id) {
    NoteModel note = db.get().find(NoteModel.class).where().eq("id", id).findOne();

    if (note == null) {
      staff.sendMiniMessage(messages.get().note.noteNotFound(), "note.id", id.toString());
      return;
    }

    staff.sendMiniMessage(messages.get().note.deletingNote());

    note.delete();
    staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.noteDeleted(), note));
  }

  @Override
  public void displayNotes(@NotNull CommandSender staff, @NotNull String targetUsername, int page) {

  }

  @Override
  public void displayNote(@NotNull CommandSender staff, @NotNull PlayerModel player, @NotNull NoteModel note) {
    staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.noteDisplayHeader(), player, note));

    if (staff.hasPermission(Permissions.STAFF_NOTES_ADMIN)) {
      staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.noteDisplayBodyAdmin(), player, note));
    } else {
      staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.noteDisplayBody(), player, note));
    }

    if (staff.hasPermission(Permissions.STAFF_NOTES_ADMIN) && staff.isPlayer()) {
      staff.sendMiniMessage(Object2Text.replaceText(messages.get().note.noteDisplayFooter(), player, note));
    }
  }

  @Nullable
  private PlayerModel getByUsername(@NotNull String username) {
    requireNonNull(username, "The username cannot be null");
    return db.get().find(PlayerModel.class).where().eq("name", username).findOne();
  }
}
