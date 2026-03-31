package engine.ui_new;

import engine.GameWindow;
import org.joml.Vector2f;
import java.util.ArrayList;

public abstract class UIElement {
  protected Anchor anchor;
  protected Offset offset;
  private UIElement parent = null;
  private final ArrayList<UIElement> children;

  public UIElement(float x0, float y0, float x1, float y1) {
    this.anchor = new Anchor(x0, y0, x1, y1);
    this.offset = new Offset();
    this.children = new ArrayList<>();
  }

  public UIElement() {
    this(0, 0, 0, 0);
  }

  public void setParent(UIElement parent) {
    this.parent = parent;
    parent.addChild(this);
  }

  protected UIElement[] getChildren() {
    return this.children.toArray(new UIElement[0]);
  }

  private void addChild(UIElement child) {
    this.children.add(child);
  }

  public Vector2f getScreenSize() {
    float width = (this.anchor.max.x - this.anchor.min.x) * GameWindow.RESOLUTION.x + this.offset.left + this.offset.right;
    float height = (this.anchor.max.y - this.anchor.min.y) * GameWindow.RESOLUTION.y + this.offset.top + this.offset.bottom;
    return new Vector2f(width, height);
  }

  public Vector2f getScreenPosition() {
    float x,y;

    if (parent != null) {
      Vector2f parentPos = parent.getScreenPosition();
      Vector2f parentSize = parent.getScreenSize();
      x = parentPos.x + parentSize.x * this.anchor.min.x + this.offset.left - this.offset.right;
      y = parentPos.y + parentSize.y * this.anchor.min.y + this.offset.bottom - this.offset.top;
    } else {
      x = this.anchor.min.x * GameWindow.RESOLUTION.x + this.offset.left - this.offset.right;
      y = this.anchor.min.y * GameWindow.RESOLUTION.y + this.offset.bottom - this.offset.top;
    }

    return new Vector2f(x, y);
  }

  public void setOffset(float left, float right, float top, float bottom) {
    this.offset.left = left;
    this.offset.right = right;
    this.offset.top = top;
    this.offset.bottom = bottom;
  }

  public abstract void render();
}
