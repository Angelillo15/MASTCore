package com.nookure.mast.api.webhook.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.nookure.mast.api.webhook.WebHook;

import java.lang.reflect.Type;

public final class WebHookSerializer implements JsonSerializer<WebHook>, CommonSerializer {
  @Override
  public JsonElement serialize(
      final WebHook src,
      final Type typeOfSrc,
      final JsonSerializationContext context
  ) {
    final JsonObject object = new JsonObject();
    object.addProperty("content", src.content());
    this.addNonNull(object, "username", src.username());
    this.addNonNull(object, "avatar_url", src.avatarURL());
    this.addNonNull(object, "tts", src.tts());
    this.addNonNull(object, "allowed_mentions", src.allowedMentions());
    this.addNonNull(object, "thread_name", src.threadName());

    final var embeds = src.embeds();
    if (embeds != null) {
      object.add("embeds", context.serialize(embeds));
    }

    return object;
  }
}
