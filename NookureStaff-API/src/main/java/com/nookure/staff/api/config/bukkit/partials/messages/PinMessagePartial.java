package com.nookure.staff.api.config.bukkit.partials.messages;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class PinMessagePartial {
  @Setting
  public final String thePinIsIncorrect = "<red>The PIN is incorrect.";
  @Setting
  public final String correctPin = "<green>The PIN is correct.";
  @Setting
  public final String pinSet = "<green>Your PIN has been set to {pin}.";
  @Setting
  public final String youMustHaveToSetAPin = """
      {center}<bold>◆ <red>Nookure <white>Network</white> ◆</bold>
      
      {center}<gray>You <red>must</red> have to set a <red>PIN</red> before you can play on this server.
      {center}<gray>Use <red><u><click:run_command:'/setpin'>/setpin</click></u></red> to set a PIN.
      {center}<gray>You have {time} to set a PIN.
      """;
  @Setting
  public final String pinTimeExpired = "<red>Your time to set a PIN has expired.";
}
