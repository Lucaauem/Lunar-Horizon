package engine.graphics.renderer.buffers;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class VertexBuffer {
	private final int rendererId;

	public VertexBuffer(FloatBuffer data) {
		this.rendererId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererId);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererId);
	}

	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
