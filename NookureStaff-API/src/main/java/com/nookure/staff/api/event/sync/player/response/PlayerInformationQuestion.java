package com.nookure.staff.api.event.sync.player.response;

import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import com.nookure.staff.api.event.sync.Redis2ServerResponse;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PlayerInformationQuestion implements Redis2ServerQuestion {
  private final UUID packetUUID;
  private final UUID player;

  /**
   * PlayerInformationQuestion constructor.
   *
   * @param packetUUID The uuid of the packet.
   * @param player     The uuid of the player who is being asked for information.
   */
  public PlayerInformationQuestion(@NotNull UUID packetUUID, @NotNull UUID player) {
    this.packetUUID = packetUUID;
    this.player = player;
  }


  @Override
  public @NotNull UUID packetUUID() {
    return packetUUID;
  }

  @Override
  public byte @NotNull [] serialize() {
    try (var byteStream = new ByteArrayOutputStream();
         var dataStream = new DataOutputStream(byteStream)) {
      dataStream.writeUTF(player.toString());
      return byteStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public @NotNull Redis2ServerResponse generateResponse() {
    return null;
  }
}
