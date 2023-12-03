package com.nookure.mast.api.webhook;

import org.jetbrains.annotations.NotNull;

public record Replacer(@NotNull String key, @NotNull String value) {
  public Replacer {
    if (key.isBlank()) {
      throw new IllegalArgumentException("Key cannot be blank");
    }
  }
}
