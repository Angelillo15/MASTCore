package com.nookure.mast.api.webhook.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;

public final class DateSerializer implements JsonSerializer<OffsetDateTime> {
  @Override
  public JsonElement serialize(final OffsetDateTime src, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}