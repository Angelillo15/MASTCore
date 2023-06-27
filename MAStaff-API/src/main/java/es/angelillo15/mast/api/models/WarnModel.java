package es.angelillo15.mast.api.models;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.database.PluginConnection;
import es.angelillo15.mast.api.pagination.Page;
import lombok.Data;

import java.util.ArrayList;

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

    /**
     * Get all the warns of a user
     * @param user The user to get the warns from
     * @return An ArrayList of WarnModel
     */
    public static ArrayList<WarnModel> getWarns(UserModel user) {
        Storm storm = PluginConnection.getStorm();

        ArrayList<WarnModel> warns = new ArrayList<>();
        try {
            storm.buildQuery(WarnModel.class)
                    .where("user", Where.EQUAL, user.getId())
                    .execute()
                    .get()
                    .forEach(warns::add);
        } catch (Exception e) {
            MAStaffInstance.getLogger().debug("Error while getting warns: " + e.getMessage());
        }

        return warns;
    }

    /**
     * Get all the active warns of a user
     * @param user The user to get the warns from
     * @return An ArrayList of actives WarnModel
     */
    public static ArrayList<WarnModel> getActiveWarns(UserModel user) {
        ArrayList<WarnModel> warns = getWarns(user);

        for (WarnModel warn : getWarns(user)) {
            if (!warn.getActive()) warns.remove(warn);
        }

        return warns;
    }

    /**
     * Get page of warns
     * @param user The user to get the warns from
     * @param page The page to get
     * @return An ArrayList of WarnModel
     */
    public static Page<ArrayList<WarnModel>> getWarns(UserModel user, int page) {
        ArrayList<WarnModel> warns = getWarns(user);

        int max = 10;
        int start = (page - 1) * max;
        int end = Math.min(start + max, warns.size());

        Page<ArrayList<WarnModel>> pageObj = new Page<>();

        pageObj.setCurrentPage(page);
        pageObj.setObjects(new ArrayList<>(warns.subList(start, end)));
        pageObj.setMaxPages((int) Math.ceil(warns.size() / (double) max));
        pageObj.setPageSize(max);

        return pageObj;
    }

    /**
     * Get page of active warns
     * @param user The user to get the warns from
     * @param page The page to get
     * @return An ArrayList of active WarnModel
     */
    public static Page<ArrayList<WarnModel>> getActiveWarns(UserModel user, int page) {
        ArrayList<WarnModel> warns = getActiveWarns(user);

        int max = 10;
        int start = (page - 1) * max;
        int end = Math.min(start + max, warns.size());

        Page<ArrayList<WarnModel>> pageObj = new Page<>();

        pageObj.setCurrentPage(page);
        pageObj.setObjects(new ArrayList<>(warns.subList(start, end)));
        pageObj.setMaxPages((int) Math.ceil(warns.size() / (double) max));
        pageObj.setPageSize(max);

        return pageObj;
    }
}
