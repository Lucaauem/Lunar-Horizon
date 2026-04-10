package engine.ui.components;

import engine.ui.UIElement;

public class UIWrapper extends UIElement {
  public UIWrapper(float x0, float y0, float x1, float y1) {
    super(x0, y0, x1, y1);
  }

  @Override
  protected void renderSelf() {
    for (UIElement child : this.getChildren()) {
      child.render();
    }
  }
}
