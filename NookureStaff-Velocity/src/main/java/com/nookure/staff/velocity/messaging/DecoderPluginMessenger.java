package com.nookure.staff.velocity.messaging;

import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.messaging.EventMessenger;
import org.jetbrains.annotations.NotNull;

public class DecoderPluginMessenger extends EventMessenger {

  @Override
  public void prepare() {
    // Nothing to do here
  }

  @Override
  public void publish(@NotNull PlayerWrapper sender, byte @NotNull [] data) {
    // Nothing to do here
  }
}