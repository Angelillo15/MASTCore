package es.angelillo15.mast.api.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "mastaff_report_comments")
@EqualsAndHashCode(callSuper = true)
public class ReportComments extends StormModel {

    @Column(length = 4096)
    private String comment;

    @Column(
            keyType = KeyType.FOREIGN,
            references = {ReportModel.class}
    )
    private Integer reportId;
}