package com.nookure.staff.api.model;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

@Table(name = "nookure_staff_data")
public class StaffDataModel extends StormModel {
  @Column(name = "UUID")
  private String UUID;

  @Column
  private Boolean staffMode;

  @Column
  private Boolean vanished;

  @Column
  private Boolean staffChatEnabled = false;

  /**
   * Get the staff data model from the UUID
   *
   * @param storm the storm instance
   * @param uuid  the uuid
   * @return the staff data model or null if not found
   */
  @Nullable
  public static StaffDataModel getFromUUID(@NotNull Storm storm, @NotNull UUID uuid) {
    Objects.requireNonNull(storm, "storm is null");
    Objects.requireNonNull(uuid, "uuid is null");

    try {
      Iterator<StaffDataModel> iterator = storm
          .buildQuery(StaffDataModel.class)
          .where("UUID", Where.EQUAL, uuid.toString())
          .execute()
          .join()
          .iterator();

      if (iterator.hasNext()) {
        return iterator.next();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String getUUID() {
    return UUID;
  }

  public void setUUID(UUID UUID) {
    this.UUID = UUID.toString();
  }

  public Boolean isStaffMode() {
    return staffMode;
  }

  public void setStaffMode(boolean staffMode) {
    this.staffMode = staffMode;
  }

  public Boolean isVanished() {
    return vanished;
  }

  public void setVanished(boolean vanished) {
    this.vanished = vanished;
  }

  public Boolean isStaffChatEnabled() {
    return staffChatEnabled;
  }

  public void setStaffChatEnabled(boolean staffChatEnabled) {
    this.staffChatEnabled = staffChatEnabled;
  }

  @Override
  public String toString() {
    return "StaffDataModel{" +
        "UUID='" + UUID + '\'' +
        ", staffMode=" + staffMode +
        ", vanished=" + vanished +
        ", staffChatEnabled=" + staffChatEnabled +
        '}';
  }
}
