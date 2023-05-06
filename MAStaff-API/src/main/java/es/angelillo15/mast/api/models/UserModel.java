package es.angelillo15.mast.api.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;

@Data
@Table(name = "mastaff_user_data")
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
}
