package com.nookure.staff.paper.note.command;

import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandParent;

@CommandData(
    name = "note",
    description = "Manage user notes",
    permission = "nookure.staff.note",
    aliases = {"notes"},
    subCommands = {
        AddNoteCommand.class,
        RemoveNoteCommand.class,
        ListNoteCommand.class
    }
)
public class ParentNoteCommand extends CommandParent {
}
