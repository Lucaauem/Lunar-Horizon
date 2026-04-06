package engine.ui_new.components;

import engine.ui_new.UIElement;
import org.joml.Vector3f;

public class UIPanel extends UIElement {
  private static final Vector3f BACKGROUND_COLOR = new Vector3f(0.7f, 0.7f, 0.7f);

  private Vector3f color = null;

  public UIPanel(float x0, float y0, float x1, float y1) {
    super(x0, y0, x1, y1);
  }

  public void setColor(Vector3f color) {
    this.color = color;
  }

  @Override
  public void render() {
    this.drawRectangle(this.getScreenPosition(), this.getScreenSize(), this.color == null ? UIPanel.BACKGROUND_COLOR : this.color);

    for (UIElement child: this.getChildren()) {
      child.render();
    }
  }
}
