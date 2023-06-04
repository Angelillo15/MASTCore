package es.angelillo15.mast.api.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;

@Data
@Table(name = "mastaff_warns")
public class WarnModel extends StormModel {
    @Column(
            keyType = KeyType.FOREIGN,
            references = {UserModel.class}
    )
    private Integer user;

    @Column(
            length = 4096
    )
    private String reason;

    @Column(
            keyType = KeyType.FOREIGN,
            references = {UserModel.class}
    )
    private Integer warnedBy;

    @Column (
            keyType = KeyType.FOREIGN,
            references = {UserModel.class}
    )
    private Integer removedBy;

    @Column(
            length = 4096
    )
    private String removeReason;

    @Column
    private Long time;

    @Column
    private Long removedTime;

    @Column
    private Boolean active;

    @Column
    private Long until;
}
