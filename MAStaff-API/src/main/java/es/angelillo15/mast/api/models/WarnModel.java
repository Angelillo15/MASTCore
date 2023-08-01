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
import lombok.EqualsAndHashCode;

import java.sql.SQLException;
import java.util.ArrayList;

@Data
@SuppressWarnings("unchecked")
@Table(name = "mastaff_warns")
@EqualsAndHashCode(callSuper = true)
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
    private Integer active;

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
            warns.addAll(storm.buildQuery(WarnModel.class)
                    .where("user", Where.EQUAL, user.getId())
                    .execute()
                    .get());
        } catch (Exception e) {
            MAStaffInstance.getLogger().debug("Error while getting warns: " + e.getMessage());
        }

        MAStaffInstance.getLogger().debug("Warns size for " + user.getUsername() + ": " + warns.size());

        ArrayList<WarnModel> finalWarns = (ArrayList<WarnModel>) warns.clone();

        new Thread(() -> {
            if (finalWarns.isEmpty()) return;

            finalWarns.forEach(warn -> {
                if (warn.getUntil() < System.currentTimeMillis()) {
                    warn.setActive(0);
                    try {
                        PluginConnection.getStorm().save(warn);
                    } catch (SQLException e) {
                        MAStaffInstance.getLogger().debug("Error while saving warn: " + e.getMessage());
                    }
                }
            });
        }).start();

        return warns;
    }

    /**
     * Get all the active warns of a user
     * @param user The user to get the warns from
     * @return An ArrayList of actives WarnModel
     */
    public static ArrayList<WarnModel> getActiveWarns(UserModel user) {
        ArrayList<WarnModel> warns = getWarns(user);

        warns.removeIf(warn -> !warn.getActive().equals(1) || warn.getUntil() < System.currentTimeMillis());

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
