package engine.objects.components;

import engine.rendering.Model;
import engine.rendering.Texture;
import engine.rendering.renderer.Renderer;
import engine.objects.core.Component;
import game.GameApplication;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class RenderComponent extends Component {
  private final Model model;
  private Texture texture;

  public RenderComponent(String texturePath) {
    this.changeTexture(texturePath);
    this.model = new Model(new float[] {
            0.0f,              0.0f,              0, 1,
            DEFAULT_TILE_SIZE, 0.0f,              1, 1,
            DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, 1, 0,
            0.0f,              DEFAULT_TILE_SIZE, 0, 0
    });
  }

  public void changeTexture(String texturePath) {
    this.texture = new Texture(texturePath);
  }

  @Override
  public void update() {
    this.texture.bind();

    Vector2f position = this.gameObject.getTransform().getPosition();

    Matrix4f proj = Renderer.PROJECTION_MATRIX;
    Matrix4f view = GameApplication.camera.getMatrix();
    Matrix4f model = new Matrix4f().identity();
    model.translate(position.x + DEFAULT_TILE_SIZE, position.y + DEFAULT_TILE_SIZE, 0);

    Matrix4f mvp = new Matrix4f(proj).mul(view).mul(model);
    Renderer.getShader().setUniformMat4f("u_MVP", mvp);
    Renderer.getShader().setUniform1i("u_UseTexture", 1);

    Renderer.draw(this.model.getVertexArray(), this.model.getIndexBuffer());
  }
}
