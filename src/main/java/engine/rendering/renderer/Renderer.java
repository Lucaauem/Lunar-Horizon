package engine.rendering.renderer;

import engine.core.GameWindow;
import engine.rendering.renderer.buffers.IndexBuffer;
import engine.rendering.renderer.buffers.VertexArray;
import engine.rendering.renderer.shader.Shader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
  private static final String SHADER_PATH = "src/main/assets/shaders/Basic.glsl";
  public static final Matrix4f PROJECTION_MATRIX = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
	public static final int PLAYER_RENDER_RADIUS = 15;

  private static final Shader shader = new Shader(SHADER_PATH);

	private Renderer() { }

  public static void draw(VertexArray va, IndexBuffer ib){
    Renderer.shader.bind();
    va.bind();
    ib.bind();
    glDrawElements(GL_TRIANGLES, ib.getCount(), GL_UNSIGNED_INT, 0);
  }

  public static Shader getShader() {
    return Renderer.shader;
  }

	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
}
