package es.angelillo15.mast.api.chat.api.chat;

import es.angelillo15.mast.api.chat.chat.TranslationRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class TranslatableComponent
    extends es.angelillo15.mast.api.chat.api.chat.BaseComponent {

  private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

  /**
   * The key into the Minecraft locale files to use for the translation. The text depends on the
   * client's locale setting. The console is always en_US
   */
  private String translate;
  /** The components to substitute into the translation */
  private List<es.angelillo15.mast.api.chat.api.chat.BaseComponent> with;

  /**
   * Creates a translatable component from the original to clone it.
   *
   * @param original the original for the new translatable component.
   */
  public TranslatableComponent(TranslatableComponent original) {
    super(original);
    setTranslate(original.getTranslate());

    if (original.getWith() != null) {
      List<es.angelillo15.mast.api.chat.api.chat.BaseComponent> temp =
          new ArrayList<es.angelillo15.mast.api.chat.api.chat.BaseComponent>();
      for (es.angelillo15.mast.api.chat.api.chat.BaseComponent baseComponent : original.getWith()) {
        temp.add(baseComponent.duplicate());
      }
      setWith(temp);
    }
  }

  /**
   * Creates a translatable component with the passed substitutions
   *
   * @param translate the translation key
   * @param with the {@link java.lang.String}s and {@link
   *     es.angelillo15.mast.api.chat.api.chat.BaseComponent}s to use into the translation
   * @see #translate
   * @see #setWith(java.util.List)
   */
  public TranslatableComponent(String translate, Object... with) {
    setTranslate(translate);
    if (with != null && with.length != 0) {
      List<es.angelillo15.mast.api.chat.api.chat.BaseComponent> temp =
          new ArrayList<es.angelillo15.mast.api.chat.api.chat.BaseComponent>();
      for (Object w : with) {
        if (w instanceof es.angelillo15.mast.api.chat.api.chat.BaseComponent) {
          temp.add((es.angelillo15.mast.api.chat.api.chat.BaseComponent) w);
        } else {
          temp.add(new es.angelillo15.mast.api.chat.api.chat.TextComponent(String.valueOf(w)));
        }
      }
      setWith(temp);
    }
  }

  /**
   * Creates a duplicate of this TranslatableComponent.
   *
   * @return the duplicate of this TranslatableComponent.
   */
  @Override
  public TranslatableComponent duplicate() {
    return new TranslatableComponent(this);
  }

  /**
   * Sets the translation substitutions to be used in this component. Removes any previously set
   * substitutions
   *
   * @param components the components to substitute
   */
  public void setWith(List<es.angelillo15.mast.api.chat.api.chat.BaseComponent> components) {
    for (es.angelillo15.mast.api.chat.api.chat.BaseComponent component : components) {
      component.parent = this;
    }
    with = components;
  }

  /**
   * Adds a text substitution to the component. The text will inherit this component's formatting
   *
   * @param text the text to substitute
   */
  public void addWith(String text) {
    addWith(new TextComponent(text));
  }

  /**
   * Adds a component substitution to the component. The text will inherit this component's
   * formatting
   *
   * @param component the component to substitute
   */
  public void addWith(es.angelillo15.mast.api.chat.api.chat.BaseComponent component) {
    if (with == null) {
      with = new ArrayList<es.angelillo15.mast.api.chat.api.chat.BaseComponent>();
    }
    component.parent = this;
    with.add(component);
  }

  @Override
  protected void toPlainText(StringBuilder builder) {
    convert(builder, false);
    super.toPlainText(builder);
  }

  @Override
  protected void toLegacyText(StringBuilder builder) {
    convert(builder, true);
    super.toLegacyText(builder);
  }

  private void convert(StringBuilder builder, boolean applyFormat) {
    String trans = TranslationRegistry.INSTANCE.translate(translate);

    Matcher matcher = format.matcher(trans);
    int position = 0;
    int i = 0;
    while (matcher.find(position)) {
      int pos = matcher.start();
      if (pos != position) {
        if (applyFormat) {
          addFormat(builder);
        }
        builder.append(trans.substring(position, pos));
      }
      position = matcher.end();

      String formatCode = matcher.group(2);
      switch (formatCode.charAt(0)) {
        case 's':
        case 'd':
          String withIndex = matcher.group(1);

          BaseComponent withComponent =
              with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++);
          if (applyFormat) {
            withComponent.toLegacyText(builder);
          } else {
            withComponent.toPlainText(builder);
          }
          break;
        case '%':
          if (applyFormat) {
            addFormat(builder);
          }
          builder.append('%');
          break;
      }
    }
    if (trans.length() != position) {
      if (applyFormat) {
        addFormat(builder);
      }
      builder.append(trans.substring(position, trans.length()));
    }
  }
}
