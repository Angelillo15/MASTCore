package com.nookure.staff.api.sync;

import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

public record PacketContainer(
    byte @NotNull [] packet,
    UUID packetUUID,
    @NotNull Class<? extends Redis2ServerQuestion> responseClass
) {
  public byte[] serialize() {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

      dataOutputStream.writeInt(packet.length);
      dataOutputStream.write(packet);
      dataOutputStream.writeLong(packetUUID.getMostSignificantBits());
      dataOutputStream.writeLong(packetUUID.getLeastSignificantBits());
      dataOutputStream.flush();

      objectOutputStream.writeObject(responseClass);
      objectOutputStream.flush();

      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Error while serializing PacketContainer", e);
    }
  }

  public static PacketContainer deserialize(byte @NotNull [] data) throws IOException {
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
         DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

      int packetLength = dataInputStream.readInt();
      byte[] packet = new byte[packetLength];
      dataInputStream.readFully(packet);

      UUID packetUUID = new UUID(dataInputStream.readLong(), dataInputStream.readLong());

      Class<? extends Redis2ServerQuestion> responseClass = (Class<? extends Redis2ServerQuestion>) objectInputStream.readObject();
      return new PacketContainer(packet, packetUUID, responseClass);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "PacketContainer{" +
        "packet=" + Arrays.toString(packet) +
        ", responseClass=" + responseClass +
        '}';
  }
}
