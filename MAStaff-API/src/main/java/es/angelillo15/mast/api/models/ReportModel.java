package es.angelillo15.mast.api.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name="mastaff_reports")
@EqualsAndHashCode(callSuper = true)
public class ReportModel extends StormModel {
    @Column(
            references = { UserModel.class },
            keyType = KeyType.FOREIGN
    )
    private Integer reported;

    @Column
    private Integer reporter;

    @Column
    private String reason;

    @Column
    private String server;

    @Column
    private Long date;

    @Column
    private Boolean status;

    @Column
    private String location;
}
