package es.angelillo15.mast.api.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;

@Table(name="mast_reports")
@Data
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
