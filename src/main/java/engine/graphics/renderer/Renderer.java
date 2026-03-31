package engine.graphics.renderer;

import engine.Game;
import engine.GameWindow;
import engine.graphics.renderer.buffers.IndexBuffer;
import engine.graphics.renderer.buffers.VertexArray;
import engine.graphics.renderer.shader.Shader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
  public static final Matrix4f PROJECTION_MATRIX = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);

	public static int PLAYER_RENDER_RADIUS = 15;
	private static Renderer instance;

	private Renderer() {}

	public static Renderer getInstance() {
		if (instance == null) {
			instance = new Renderer();
		}
		return instance;
	}

	public void draw(VertexArray va, IndexBuffer ib, Shader shader){
		shader.bind();
		va.bind();
		ib.bind();
		glDrawElements(GL_TRIANGLES, ib.getCount(), GL_UNSIGNED_INT, 0);
	}

  public void draw(VertexArray va, IndexBuffer ib){
    this.draw(va, ib, Game.shader);
  }

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
}
