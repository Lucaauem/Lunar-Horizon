package engine.ui_new.text;

import engine.graphics.Model;
import engine.ui_new.Offset;
import engine.ui_new.UIElement;
import org.joml.Vector2f;
import org.joml.Vector4f;

class UITextCharacter extends UIElement {
  private final Model model;

  protected UITextCharacter(Vector4f uv, Offset offset, UIElement parent) {
    this.offset = offset;
    this.setParent(parent);

    this.model = new Model(new float[] {
       0.0f, 0.0f, uv.x, uv.y,
       1.0f, 0.0f, uv.z, uv.y,
       1.0f, 1.0f, uv.z, uv.w,
       0.0f, 1.0f, uv.x, uv.w
    });
  }

  public void renderSelf() {
    Vector2f size = new Vector2f(UIText.CHARACTER_WIDTH, UIText.CHARACTER_HEIGHT);
    Vector2f parentPosition = this.getParent().getScreenPosition();
    Vector2f position = parentPosition.add(new Vector2f(offset.horizontal, offset.vertical));

    this.drawTexture(position, size, UIText.FONT_TEXTURE, this.model);
  }
}
