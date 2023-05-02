package es.angelillo15.mast.api.punishments.data.migrations;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import es.angelillo15.mast.api.database.PluginConnection;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Collection;

@Data
@Table(name="mastaff_ipbans")
public class IpBansTable extends StormModel {
    @Column(length = 16)
    private String ip;

    @Column(
            keyType = KeyType.FOREIGN,
            references = { BansTable.class }
    )
    private Integer banId;

    @SneakyThrows
    public BansTable getBanTable() {
        Storm storm = PluginConnection.getStorm();
        Collection<BansTable> bans = storm.buildQuery(BansTable.class)
                .where("id", Where.EQUAL, banId)
                .limit(1)
                .execute()
                .join();

        return bans.isEmpty() ? null : bans.iterator().next();
    }

    public static BansTable getIpBanned(String ip) {
        Storm storm = PluginConnection.getStorm();

        try {
            Collection<IpBansTable> bans = storm.buildQuery(IpBansTable.class)
                    .where("ip", Where.EQUAL, ip)
                    .limit(1)
                    .execute()
                    .join();

            return bans.iterator().next().getBanTable();
        } catch (Exception e) {
            return null;
        }
    }
}
