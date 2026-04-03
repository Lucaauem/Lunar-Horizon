package engine.ui_new.components.list;

import engine.GameWindow;
import engine.ui_new.Anchor;
import engine.ui_new.UIElement;
import engine.ui_new.interaction.Clickable;
import engine.ui_new.text.UIText;

public class UIListElement extends UIElement implements Clickable {
  private final UIText label;
  private final Runnable action;

  public UIListElement(String label, Runnable action) {
    this.label = new UIText(label, this);
    this.anchor = new Anchor(0.0f, 1.0f - (float) UIText.CHARACTER_HEIGHT / GameWindow.RESOLUTION.y, 1.0f, 1.0f);
    this.action = action;
  }

  @Override
  public void click() {
    this.action.run();
  }

  @Override
  public void render() {
    label.render();
  }
}
