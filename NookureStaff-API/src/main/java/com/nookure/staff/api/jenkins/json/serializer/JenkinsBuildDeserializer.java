package com.nookure.staff.api.jenkins.json.serializer;

import com.google.gson.*;
import com.nookure.staff.api.jenkins.json.JenkinsBuild;

import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;

public class JenkinsBuildDeserializer implements JsonDeserializer<JenkinsBuild> {
  @Override
  public JenkinsBuild deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    return new JenkinsBuild(
        requireNonNull(jsonObject.get("_class").getAsString()),
        jsonObject.get("number").getAsInt(),
        requireNonNull(jsonObject.get("url").getAsString())
    );
  }
}
