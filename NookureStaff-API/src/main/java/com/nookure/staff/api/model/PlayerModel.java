package com.nookure.staff.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "nookure_staff_players")
public class PlayerModel extends BaseDomain {
  String name;
  String uuid;

  public String getName() {
    return name;
  }

  public PlayerModel setName(String name) {
    this.name = name;
    return this;
  }

  public String getUuid() {
    return uuid;
  }

  public PlayerModel setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }
}
