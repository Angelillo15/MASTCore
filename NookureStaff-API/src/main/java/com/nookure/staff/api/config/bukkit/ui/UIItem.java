package com.nookure.staff.api.config.bukkit.ui;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class UIItem {
  @Setting
  @Comment("""
      The material of the item, to see all materials go to:
      https://jd.papermc.io/paper/1.20.6/org/bukkit/Material.html
      """)
  private Material material = Material.AIR;

  @Setting
  @Comment("""
      The display name of the item
      Here you must use miniMessage format
      """)
  private String displayName = "ItemName";

  @Setting
  @Comment("""
      This can be just a number (0) or a range (0-5)
      """)
  private String slot = "0";

  @Setting
  @Nullable
  @Comment("""
      The lore of the item
      Here you must use miniMessage format
      """)
  private List<String> lore;

  @Setting
  @Comment("""
      The enchantments of the item, use this format to add an enchantment:
       enchantmentName:level
      to see all enchantments go to:
        https://jd.papermc.io/paper/1.20.6/org/bukkit/enchantments/Enchantment.html
      """)
  @Nullable
  private List<String> enchantments;
}
