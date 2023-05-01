package es.angelillo15.mast.api.punishments.data.migrations;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;

@Data
@Table(name = "mastaff_bans")
public class BansTable extends StormModel {
    @Column(keyType = KeyType.PRIMARY, autoIncrement = true)
    private int id;

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
    private long time;

    @Column
    private long until;

    @Column
    private boolean active;

    @Column
    private boolean ipban;
}
