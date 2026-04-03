package engine.ui_new.text;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.renderer.Renderer;
import engine.ui_new.Offset;
import engine.ui_new.UIElement;
import org.joml.Matrix4f;
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

  public void render() {
    Vector2f size = new Vector2f(UIText.CHARACTER_WIDTH, UIText.CHARACTER_HEIGHT);
    Vector2f parentPosition = this.getParent().getScreenPosition();
    Vector2f position = parentPosition.add(new Vector2f(offset.horizontal, offset.vertical));

    Matrix4f model = new Matrix4f().identity();
    model.translate(position.x, position.y, 0);
    model.scale(size.x, size.y, 1);

    Matrix4f mvp = new Matrix4f(Renderer.PROJECTION_MATRIX).mul(model);
    Game.shader.setUniformMat4f("u_MVP", mvp);
    Game.shader.setUniform1i("u_UseTexture", 1);

    Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer());
  }
}
