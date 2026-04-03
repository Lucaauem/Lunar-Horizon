package engine.ui_new;

import engine.Game;
import engine.GameWindow;
import engine.graphics.Model;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
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
    this(0.0f, 0.0f, 1.0f, 1.0f);
  }

  public void setParent(UIElement parent) {
    if (this.parent != null) {
      this.parent.children.remove(this);
    }

    this.parent = parent;
    parent.addChild(this);
  }

  public UIElement getParent() {
    return this.parent;
  }

  public void setAnchor(float x0, float y0, float x1, float y1) {
    this.anchor = new Anchor(x0, y0, x1, y1);
  }

  protected UIElement[] getChildren() {
    return this.children.toArray(new UIElement[0]);
  }

  private void addChild(UIElement child) {
    this.children.add(child);
  }

  public Vector2f getScreenSize() {
    Vector2f parentSize = this.parent == null ? new Vector2f(GameWindow.RESOLUTION) : this.parent.getScreenSize();

    float width = (this.anchor.max.x - this.anchor.min.x) * parentSize.x;
    float height = (this.anchor.max.y - this.anchor.min.y) * parentSize.y;

    return new Vector2f(width, height);
  }

  public Vector2f getScreenPosition() {
    Vector2f pos = new Vector2f();
    Vector2f parentSize;

    if (parent != null) {
      pos.add(this.parent.getScreenPosition());
      parentSize = this.parent.getScreenSize();
    } else {
      parentSize = new Vector2f(GameWindow.RESOLUTION);
    }

    Vector2f relativePos = new Vector2f(this.anchor.min);
    pos.add(new Vector2f(parentSize).mul(relativePos));
    pos.add(this.offset.asVector());

    return pos;
  }

  private Vector4f getScreenBounds() {
    Vector2f pos = this.getScreenPosition();
    Vector2f size = this.getScreenSize();
    return new Vector4f(pos.x, pos.y, pos.x + size.x, pos.y + size.y);
  }

  public boolean isInsideAnchor() {
    if (this.parent == null) {
      return true;
    }

    Vector4f own = this.getScreenBounds();
    Vector4f parent = this.parent.getScreenBounds();

    return own.x >= parent.x && own.y >= parent.y && own.z <= parent.z && own.w <= parent.w;
  }

  public void setOffset(float horizontal, float vertical) {
    this.offset.horizontal = horizontal;
    this.offset.vertical = vertical;
  }

  public abstract void render();

  protected void drawRectangle(Vector2f position, Vector2f size, Vector3f color) {
    this.drawRectangle(position, size, new Vector4f(color.x, color.y, color.z, 1));
  }

  protected void drawRectangle(Vector2f position, Vector2f size, Vector4f color) {
    Model model = new Model(new float[] {
      0.0f, 0.0f, 0, 0,
      1.0f, 0.0f, 0, 0,
      1.0f, 1.0f, 0, 0,
      0.0f, 1.0f, 0, 0
    });

    Matrix4f modelMatrix = new Matrix4f().identity();
    modelMatrix.translate(position.x , position.y, 0);
    modelMatrix.scale(size.x, size.y, 1);

    Matrix4f mvp = new Matrix4f(Renderer.PROJECTION_MATRIX).mul(modelMatrix);
    Game.shader.setUniformMat4f("u_MVP", mvp);

    Game.shader.setUniform4f("u_Color", color);
    Game.shader.setUniform1i("u_UseTexture", 0);
    Renderer.getInstance().draw(model.getVertexArray(), model.getIndexBuffer());
  }

  // region DEBUG

  public void DEBUG_drawBounds(Vector3f color) {
    this.drawRectangle(this.getScreenPosition(), this.getScreenSize(), new Vector4f(color.x, color.y, color.z, 0.25f));
  }

  // endregion
}
