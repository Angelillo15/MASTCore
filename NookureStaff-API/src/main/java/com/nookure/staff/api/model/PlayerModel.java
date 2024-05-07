package com.nookure.staff.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "nookure_staff_players")
public class PlayerModel extends BaseDomain {
  @Column
  String name;
  @Column(unique = true)
  UUID uuid;
  @Column(nullable = false)
  Instant lastLogin;
  @Column(nullable = false)
  Instant firstLogin;
  @Column(nullable = false)
  String lastIp = "0.0.0.0";
  @Column(nullable = false)
  String firstIp = "0.0.0.0";

  public String getName() {
    return name;
  }

  public PlayerModel setName(String name) {
    this.name = name;
    return this;
  }

  public UUID getUuid() {
    return uuid;
  }

  public PlayerModel setUuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public Instant getLastLogin() {
    return lastLogin;
  }

  public PlayerModel setLastLogin(Instant lastLogin) {
    this.lastLogin = lastLogin;
    return this;
  }

  public Instant getFirstLogin() {
    return firstLogin;
  }

  public PlayerModel setFirstLogin(Instant firstLogin) {
    this.firstLogin = firstLogin;
    return this;
  }

  public String getLastIp() {
    return lastIp;
  }

  public PlayerModel setLastIp(String lastIp) {
    this.lastIp = lastIp;
    return this;
  }

  public String getFirstIp() {
    return firstIp;
  }

  public PlayerModel setFirstIp(String firstIp) {
    this.firstIp = firstIp;
    return this;
  }
}
