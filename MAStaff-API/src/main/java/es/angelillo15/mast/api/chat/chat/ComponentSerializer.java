package es.angelillo15.mast.api.chat.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.Set;
import es.angelillo15.mast.api.chat.api.chat.BaseComponent;
import es.angelillo15.mast.api.chat.api.chat.ItemTag;
import es.angelillo15.mast.api.chat.api.chat.KeybindComponent;
import es.angelillo15.mast.api.chat.api.chat.ScoreComponent;
import es.angelillo15.mast.api.chat.api.chat.SelectorComponent;
import es.angelillo15.mast.api.chat.api.chat.TextComponent;
import es.angelillo15.mast.api.chat.api.chat.TranslatableComponent;
import es.angelillo15.mast.api.chat.api.chat.hover.content.Entity;
import es.angelillo15.mast.api.chat.api.chat.hover.content.EntitySerializer;
import es.angelillo15.mast.api.chat.api.chat.hover.content.Item;
import es.angelillo15.mast.api.chat.api.chat.hover.content.ItemSerializer;
import es.angelillo15.mast.api.chat.api.chat.hover.content.Text;
import es.angelillo15.mast.api.chat.api.chat.hover.content.TextSerializer;

public class ComponentSerializer implements JsonDeserializer<BaseComponent>
{

    public static final ThreadLocal<Set<BaseComponent>> serializedComponents = new ThreadLocal<Set<BaseComponent>>();
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Gson gson = new GsonBuilder().
            registerTypeAdapter( BaseComponent.class, new ComponentSerializer() ).
            registerTypeAdapter( TextComponent.class, new TextComponentSerializer() ).
            registerTypeAdapter( TranslatableComponent.class, new TranslatableComponentSerializer() ).
            registerTypeAdapter( KeybindComponent.class, new KeybindComponentSerializer() ).
            registerTypeAdapter( ScoreComponent.class, new ScoreComponentSerializer() ).
            registerTypeAdapter( SelectorComponent.class, new SelectorComponentSerializer() ).
            registerTypeAdapter( Entity.class, new EntitySerializer() ).
            registerTypeAdapter( Text.class, new TextSerializer() ).
            registerTypeAdapter( Item.class, new ItemSerializer() ).
            registerTypeAdapter( ItemTag.class, new ItemTag.Serializer() ).
            create();

    public static BaseComponent[] parse(String json)
    {
        JsonElement jsonElement = JSON_PARSER.parse( json );

        if ( jsonElement.isJsonArray() )
        {
            return gson.fromJson( jsonElement, BaseComponent[].class );
        } else
        {
            return new BaseComponent[]
            {
                gson.fromJson( jsonElement, BaseComponent.class )
            };
        }
    }

    public static String toString(Object object)
    {
        return gson.toJson( object );
    }

    public static String toString(BaseComponent component)
    {
        return gson.toJson( component );
    }

    public static String toString(BaseComponent... components)
    {
        if ( components.length == 1 )
        {
            return gson.toJson( components[0] );
        } else
        {
            return gson.toJson( new TextComponent( components ) );
        }
    }

    @Override
    public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if ( json.isJsonPrimitive() )
        {
            return new TextComponent( json.getAsString() );
        }
        JsonObject object = json.getAsJsonObject();
        if ( object.has( "translate" ) )
        {
            return context.deserialize( json, TranslatableComponent.class );
        }
        if ( object.has( "keybind" ) )
        {
            return context.deserialize( json, KeybindComponent.class );
        }
        if ( object.has( "score" ) )
        {
            return context.deserialize( json, ScoreComponent.class );
        }
        if ( object.has( "selector" ) )
        {
            return context.deserialize( json, SelectorComponent.class );
        }
        return context.deserialize( json, TextComponent.class );
    }
}
