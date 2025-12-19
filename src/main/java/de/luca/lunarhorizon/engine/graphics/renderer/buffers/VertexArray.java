package de.luca.lunarhorizon.engine.graphics.renderer.buffers;

import java.util.List;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray {
	private final int rendererId;

	public VertexArray() {
		this.rendererId = glGenVertexArrays();
	}

	public void addBuffer(VertexBuffer vb, VertexBufferLayout layout) {
		this.bind();
		vb.bind();
		List<VertexBufferElement> elements = layout.getElements();

		int offset = 0;

		for(int i=0; i<elements.size(); i++) {
			VertexBufferElement element = elements.get(i);

			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, element.count, element.type, element.normalized, layout.getStride(), offset);

			offset += element.count * VertexBufferElement.getSizeOfType(element.type);
		}
	}

	public void bind() {
		glBindVertexArray(this.rendererId);
	}

	public void unbind() {
		glBindVertexArray(0);
	}
}
