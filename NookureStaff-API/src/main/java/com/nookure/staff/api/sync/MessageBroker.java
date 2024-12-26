package com.nookure.staff.api.sync;

import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public interface MessageBroker {
  void sendPacket(byte @NotNull [] key, byte @NotNull [] packet);

  default void sendPacket(@NotNull UUID server, byte @NotNull [] packet) {
    sendPacket(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8), packet);
  }

  <T extends Redis2ServerQuestion> T sendPacketAndWait(byte @NotNull [] key, @Nullable UUID server, byte @NotNull [] packet, @NotNull Class<T> responseClass);

  default <T extends Redis2ServerQuestion> T sendPacketAndWait(@NotNull UUID server, byte @NotNull [] packet, @NotNull Class<T> responseClass) {
    return sendPacketAndWait(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8), server, packet, responseClass);
  }

  <T extends Redis2ServerQuestion> T decodePacket(byte @NotNull [] packet, @NotNull Class<T> responseClass);

  default byte @NotNull [] generateReplyKey(@NotNull UUID server) {
    return "nkstaff:".concat(server.toString()).getBytes(StandardCharsets.UTF_8);
  }
}
