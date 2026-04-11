package engine.ui.components.list;

import engine.core.GameWindow;
import engine.ui.Anchor;
import engine.ui.UIElement;
import engine.ui.interaction.Clickable;
import engine.ui.text.UIText;

public class UIListElement extends UIElement implements Clickable {
  private final UIText label;
  private final Runnable action;

  public UIListElement(String label, Runnable action) {
    this.label = new UIText(label, this);
    this.anchor = new Anchor(0.0f, 1.0f - (float) UIText.CHARACTER_HEIGHT / GameWindow.RESOLUTION.y, 1.0f, 1.0f);
    this.action = action;
  }

  public UIText getLabel() {
    return this.label;
  }

  @Override
  public void click() {
    this.action.run();
  }

  @Override
  public void renderSelf() {
    label.render();
  }
}
