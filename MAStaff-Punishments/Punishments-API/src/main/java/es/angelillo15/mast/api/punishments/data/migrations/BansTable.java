package es.angelillo15.mast.api.punishments.data.migrations;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.punishments.cache.BanCache;
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

    @SneakyThrows
    public void unBan() {
        Storm storm = PluginConnection.getStorm();

        active = false;
        storm.save(this);

        if (!ipban) return;

        IpBansTable ipBansTable = storm.buildQuery(IpBansTable.class)
                .where("banId", Where.EQUAL, getId())
                .limit(1)
                .execute()
                .join()
                .iterator()
                .next();

        if (ipBansTable != null) {
            storm.delete(ipBansTable);
        }
    }

    @SneakyThrows
    public static boolean isBanned(String username) {
        Storm storm = PluginConnection.getStorm();

        if (BanCache.isPunished(username)) return true;

        boolean isBanned = storm.buildQuery(BansTable.class)
                .where("username", Where.EQUAL, username)
                .where("active", Where.EQUAL, true)
                .limit(1)
                .execute()
                .join()
                .size() > 0;

        if (isBanned) {
            new Thread(() -> {
                BanCache.addPunishment(username, getBan(username));
            }).start();
        }

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

        if (isBanned) {
            new Thread(() -> {
                BanCache.addPunishment(username, getBan(username));
            }).start();
        }

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

    @SneakyThrows
    public IpBansTable getIpBanTable(){
        Storm storm = PluginConnection.getStorm();

        if (!ipban) return null;

        return storm.buildQuery(IpBansTable.class)
                .where("banId", Where.EQUAL, getId())
                .limit(1)
                .execute()
                .join()
                .iterator()
                .next();
    }
}
