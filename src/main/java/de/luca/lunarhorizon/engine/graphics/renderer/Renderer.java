package de.luca.lunarhorizon.engine.graphics.renderer;

import de.luca.lunarhorizon.engine.graphics.renderer.buffers.IndexBuffer;
import de.luca.lunarhorizon.engine.graphics.renderer.buffers.VertexArray;
import de.luca.lunarhorizon.engine.graphics.renderer.shader.Shader;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
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

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
}
