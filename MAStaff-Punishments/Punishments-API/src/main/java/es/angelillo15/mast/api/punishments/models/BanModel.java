package es.angelillo15.mast.api.punishments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanModel {
    private int id;
    private UUID uuid;
    private String bannedByUUID;
    private String bannedBy;
    private String reason;
    private String removedBy;
    private String removedByUUID;
    private String removedReason;
    private long removedByDate;
    private boolean active;
    private long time;
    private long until;
    private boolean ipBan;
}
