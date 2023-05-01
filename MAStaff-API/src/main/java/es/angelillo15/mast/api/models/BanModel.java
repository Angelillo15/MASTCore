package es.angelillo15.mast.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanModel {
    private int id = -1;
    private UUID uuid = null;
    private String username = null;
    private String bannedByUUID = null;
    private String bannedBy = null;
    private String reason = null;
    private String removedBy = null;
    private String removedByUUID = null;
    private String removedReason = null;
    private long removedByDate = -1;
    private boolean active = false;
    private long time = -1;
    private long until;
    private boolean ipBan = false;

    public boolean isPermanent() {
        return until == 0;
    }

    public boolean isExpired() {
        return until != 0 && until < System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return "BanModel{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", bannedByUUID='" + bannedByUUID + '\'' +
                ", bannedBy='" + bannedBy + '\'' +
                ", reason='" + reason + '\'' +
                ", removedBy='" + removedBy + '\'' +
                ", removedByUUID='" + removedByUUID + '\'' +
                ", removedReason='" + removedReason + '\'' +
                ", removedByDate=" + removedByDate +
                ", active=" + active +
                ", time=" + time +
                ", until=" + until +
                ", ipBan=" + ipBan +
                '}';
    }
}
