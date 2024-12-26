package com.nookure.staff.messaging;

import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.messaging.EventMessenger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DecoderPluginMessenger extends EventMessenger {
  @Override
  public void prepare() {
    // Nothing to do here
  }

  @Override
  public void publish(@Nullable PlayerWrapper sender, byte @NotNull [] data) {
    // Nothing to do here
  }
}
