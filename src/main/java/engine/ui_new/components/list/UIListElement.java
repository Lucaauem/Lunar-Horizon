package engine.ui_new.components.list;

import engine.ui_new.UIElement;
import engine.ui_new.interaction.Clickable;
import engine.ui_new.text.UIText;

public class UIListElement extends UIElement implements Clickable {
  private final String label;
  private final Runnable action;

  public UIListElement(String label, Runnable action) {
    super(0,0,0,0);
    this.label = label;
    this.action = action;
  }

  @Override
  public void click() {
    this.action.run();
  }

  @Override
  public void render() {
    UIText text = new UIText(this.label, this);
    text.render();
  }
}
