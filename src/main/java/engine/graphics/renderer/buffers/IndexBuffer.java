package engine.graphics.renderer.buffers;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class IndexBuffer {
	private int rendererId;
	private int count;

	public IndexBuffer(int[] data, int count) {
		this.count = count;
		this.rendererId = glGenBuffers();

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.rendererId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.rendererId);
	}

	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getCount() {
		return count;
	}
}
