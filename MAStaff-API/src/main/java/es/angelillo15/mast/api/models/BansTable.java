package es.angelillo15.mast.api.models;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.cache.BanCache;
import es.angelillo15.mast.api.database.PluginConnection;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.UUID;

@Data
@Table(name = "mastaff_bans")
public class BansTable extends StormModel {
    @Column(length = 36)
    private String uuid;

    @Column(length = 16)
    private String username;

    @Column(length = 4096)
    private String reason;

    @Column(length = 36)
    private String banned_by_uuid;

    @Column(length = 16)
    private String banned_by_name;

    @Column(length = 36)
    private String unbanned_by_uuid;

    @Column(length = 16)
    private String unbanned_by_name;

    @Column(length = 4096)
    private String unban_reason;

    @Column
    private Long time;

    @Column
    private Long until;

    @Column
    private Boolean active;

    @Column
    private Boolean ipban;

    public boolean isPermanent() {
        return until == 0;
    }
    public boolean isExpired() {
        return !isPermanent() && until < System.currentTimeMillis();
    }

    public void unBan(){
        unBan("CONSOLE", "Expired", "CONSOLE");
    }
    @SneakyThrows
    public void unBan(String unbanned_by_name, String unban_reason, String unbanned_by_uuid) {
        Storm storm = PluginConnection.getStorm();

        setActive(false);
        setUnbanned_by_uuid(unbanned_by_uuid);
        setUnbanned_by_name(unbanned_by_name);
        setUnban_reason(unban_reason);
        storm.save(this);


        try {
            IpBansTable ipBansTable = storm.buildQuery(IpBansTable.class)
                    .where("ban_id", Where.EQUAL, getId())
                    .limit(1)
                    .execute()
                    .join()
                    .iterator()
                    .next();

            if (ipBansTable != null) {
                storm.delete(ipBansTable);
            }
        } catch (Exception ignored) { }
    }

    @SneakyThrows
    public static boolean isBanned(String username) {
        Storm storm = PluginConnection.getStorm();

        if (BanCache.isPunished(username)) return true;

        Boolean isBanned = storm.buildQuery(BansTable.class)
                .where("username", Where.EQUAL, username)
                .where("active", Where.EQUAL, true)
                .limit(1)
                .execute()
                .join()
                .size() > 0;

        return isBanned;
    }

    @SneakyThrows
    public static boolean isPermBanned(String username) {
        Storm storm = PluginConnection.getStorm();

        if (BanCache.isPunished(username)) return true;

        boolean isBanned = storm.buildQuery(BansTable.class)
                .where("username", Where.EQUAL, username)
                .where("active", Where.EQUAL, true)
                .where("until", Where.EQUAL, 0)
                .limit(1)
                .execute()
                .join()
                .size() > 0;

        return isBanned;
    }

    public static BansTable getBan(String username) {
        Storm storm = PluginConnection.getStorm();

        try {
            BansTable ban =  storm.buildQuery(BansTable.class)
                    .where("username", Where.EQUAL, username)
                    .where("active", Where.EQUAL, true)
                    .limit(1)
                    .execute()
                    .join()
                    .iterator()
                    .next();

            if (ban.isExpired()) {
                ban.unBan();
                return null;
            }
            return ban ;
        } catch (Exception e) {
            return null;
        }
    }

    @SneakyThrows
    public static BansTable getBan(Integer id) {
        Storm storm = PluginConnection.getStorm();

        return storm.buildQuery(BansTable.class)
                .where("id", Where.EQUAL, id)
                .limit(1)
                .execute()
                .join()
                .iterator()
                .next();
    }

    public static BansTable getBan(UUID uuid) {
        Storm storm = PluginConnection.getStorm();
        try {
            return storm.buildQuery(BansTable.class)
                    .where("uuid", Where.EQUAL, uuid.toString())
                    .where("active", Where.EQUAL, true)
                    .limit(1)
                    .execute()
                    .join()
                    .iterator()
                    .next();
        } catch (Exception e) {
            return null;
        }

    }
}
