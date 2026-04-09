package engine.ui_new.components;

import engine.graphics.Texture;
import engine.ui_new.UIElement;
import org.joml.Vector2f;

public class UIImage extends UIElement {
  private final Texture texture;
  private Vector2f scale;

  public UIImage(float x0, float y0, float x1, float y1, String texturePath) {
    super(x0, y0, x1, y1);
    this.texture = new Texture(texturePath);
    this.scale = new Vector2f(1.0f, 1.0f);
  }

  public void setScale(float widthScale, float heightScale) {
    this.scale = new Vector2f(widthScale, heightScale);
  }

  public Vector2f getSize() {
    return new Vector2f(this.texture.getWidth(), this.texture.getHeight()).mul(this.scale);
  }

  @Override
  protected void renderSelf() {
    this.drawTexture(this.getScreenPosition(), this.getSize(), this.texture);
  }
}
