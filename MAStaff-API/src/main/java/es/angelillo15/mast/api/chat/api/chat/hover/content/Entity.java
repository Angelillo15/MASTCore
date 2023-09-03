package es.angelillo15.mast.api.chat.api.chat.hover.content;

import es.angelillo15.mast.api.chat.api.chat.BaseComponent;
import es.angelillo15.mast.api.chat.api.chat.HoverEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Entity extends Content {

  /**
   * Namespaced entity ID.
   *
   * <p>Will use 'minecraft:pig' if null.
   */
  private String type;
  /**
   * Entity UUID in hyphenated hexadecimal format.
   *
   * <p>Should be valid UUID. TODO : validate?
   */
  @NonNull private String id;
  /**
   * Name to display as the entity.
   *
   * <p>This is optional and will be hidden if null.
   */
  private BaseComponent name;

  @Override
  public HoverEvent.Action requiredAction() {
    return HoverEvent.Action.SHOW_ENTITY;
  }
}
