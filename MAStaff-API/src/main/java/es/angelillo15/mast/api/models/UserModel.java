package es.angelillo15.mast.api.models;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "mastaff_user_data")
@EqualsAndHashCode(callSuper = true)
public class UserModel extends StormModel {

    public static final String UNKNOWN = "unknown";
    @Column(
            name = "UUID",
            length = 36,
            unique = true
    )
    private String UUID = UNKNOWN;
    @Column(
            name = "username",
            length = 16
    )
    private String username = UNKNOWN;
    @Column(
            length = 16
    )
    private String lastIp = UNKNOWN;
    @Column(
            length = 16
    )
    private String regIp = UNKNOWN;
    @Column(
    )
    private Long firstLogin = 0L;
    @Column(
    )
    private Long lastLogin = 0L;

    public static boolean exists(String where, String value) {
        Storm storage = PluginConnection.getStorm();
        boolean exists = false;

        try {
            exists = storage.buildQuery(UserModel.class)
                    .where(where, Where.EQUAL ,value)
                    .execute()
                    .join()
                    .iterator()
                    .hasNext();
        } catch (Exception e) {
            MAStaffInstance.getLogger().debug("Error while checking if user exists: " + e.getMessage());
        }

        return exists;
    }
}
