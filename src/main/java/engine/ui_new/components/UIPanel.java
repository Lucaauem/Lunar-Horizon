package engine.ui_new.components;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.renderer.Renderer;
import engine.ui_new.UIElement;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class UIPanel extends UIElement {
  private static final Vector4f BACKGROUND_COLOR = new Vector4f(0.7f, 0.7f, 0.7f, 1.0f);
  private static final Model MODEL = new Model(new float[] {
    0.0f, 0.0f, 0, 0,
    1.0f, 0.0f, 0, 0,
    1.0f, 1.0f, 0, 0,
    0.0f, 1.0f, 0, 0
  });

  public UIPanel(float x0, float y0, float x1, float y1) {
    super(x0, y0, x1, y1);
  }

  @Override
  public void render() {
    Vector2f size = this.getScreenSize();
    Vector2f position = this.getScreenPosition();

    Matrix4f model = new Matrix4f().identity();
    model.translate(position.x, position.y, 0);
    model.scale(size.x, size.y, 1);

    Matrix4f mvp = new Matrix4f(Renderer.PROJECTION_MATRIX).mul(model);
    Game.shader.setUniformMat4f("u_MVP", mvp);

    Game.shader.setUniform4f("u_Color", UIPanel.BACKGROUND_COLOR);
    Game.shader.setUniform1i("u_UseTexture", 0);
    Renderer.getInstance().draw(MODEL.getVertexArray(), MODEL.getIndexBuffer());

    for (UIElement child: this.getChildren()) {
      child.render();
    }
  }
}
