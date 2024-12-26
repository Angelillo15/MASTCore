package com.nookure.staff.api.sync;

import com.nookure.staff.api.event.sync.Redis2ServerQuestion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class PacketContainerTest {
  private static final byte[] PACKET_DATA = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
  private static final UUID PACKET_UUID = UUID.randomUUID();

  @Test
  public void serializeAndDeserializeTest() throws IOException {
    PacketContainer packetContainer = new PacketContainer(PACKET_DATA, PACKET_UUID, Redis2ServerQuestion.class);

    byte[] serializedPacket = packetContainer.serialize();

    PacketContainer deserializedPacketContainer = PacketContainer.deserialize(serializedPacket);

    assert Arrays.equals(PACKET_DATA, deserializedPacketContainer.packet());
    assert Redis2ServerQuestion.class.equals(deserializedPacketContainer.responseClass());
    assert PACKET_UUID.equals(deserializedPacketContainer.packetUUID());
  }
}
