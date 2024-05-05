package com.nookure.staff.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class PlayerRow {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "first_join")
  private Long firstJoin;

  @Column(name = "last_join")
  private Long lastJoin;

  @Column(name = "last_ip")
  private String lastIp;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public PlayerRow setName(String name) {
    this.name = name;
    return this;
  }

  public String getUuid() {
    return uuid;
  }

  public PlayerRow setUuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public Long getFirstJoin() {
    return firstJoin;
  }

  public PlayerRow setFirstJoin(Long firstJoin) {
    this.firstJoin = firstJoin;
    return this;
  }

  public Long getLastJoin() {
    return lastJoin;
  }

  public PlayerRow setLastJoin(Long lastJoin) {
    this.lastJoin = lastJoin;
    return this;
  }

  public String getLastIp() {
    return lastIp;
  }

  public PlayerRow setLastIp(String lastIp) {
    this.lastIp = lastIp;
    return this;
  }
}
