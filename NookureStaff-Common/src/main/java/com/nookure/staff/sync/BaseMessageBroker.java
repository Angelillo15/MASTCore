package com.nookure.staff.sync;

import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import com.nookure.staff.api.sync.MessageBroker;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class BaseMessageBroker implements MessageBroker {
  @Override
  public <T extends Redis2ServerQuestion> T decodePacket(byte @NotNull [] packet, @NotNull Class<T> responseClass) {
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet);
         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

      return (T) objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
