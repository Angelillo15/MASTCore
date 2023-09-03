package es.angelillo15.mast.api.chat.api.chat.hover.content;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import es.angelillo15.mast.api.chat.api.chat.BaseComponent;
import java.lang.reflect.Type;

public class EntitySerializer
    implements JsonSerializer<es.angelillo15.mast.api.chat.api.chat.hover.content.Entity>,
        JsonDeserializer<es.angelillo15.mast.api.chat.api.chat.hover.content.Entity> {

  @Override
  public es.angelillo15.mast.api.chat.api.chat.hover.content.Entity deserialize(
      JsonElement element, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject value = element.getAsJsonObject();

    return new es.angelillo15.mast.api.chat.api.chat.hover.content.Entity(
        (value.has("type")) ? value.get("type").getAsString() : null,
        value.get("id").getAsString(),
        (value.has("name")) ? context.deserialize(value.get("name"), BaseComponent.class) : null);
  }

  @Override
  public JsonElement serialize(Entity content, Type type, JsonSerializationContext context) {
    JsonObject object = new JsonObject();
    object.addProperty("type", (content.getType() != null) ? content.getType() : "minecraft:pig");
    object.addProperty("id", content.getId());
    if (content.getName() != null) {
      object.add("name", context.serialize(content.getName()));
    }
    return object;
  }
}
