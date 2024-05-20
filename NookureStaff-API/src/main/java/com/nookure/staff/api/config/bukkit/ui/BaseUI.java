package com.nookure.staff.api.config.bukkit.ui;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class BaseUI {
  @Setting
  private String title = "Your inventory title here";
  @Setting
  private int row = 5;
  @Setting
  private List<UIItem> items;
  public BaseUI() {
  }

  public BaseUI(@NotNull String title, @NotNull int row) {
    this.title = title;
    this.row = row;
  }
}
