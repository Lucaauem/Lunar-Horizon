package de.luca.lunarhorizon.engine.graphics;

import de.luca.lunarhorizon.engine.graphics.renderer.buffers.IndexBuffer;
import de.luca.lunarhorizon.engine.graphics.renderer.buffers.VertexArray;
import de.luca.lunarhorizon.engine.graphics.renderer.buffers.VertexBuffer;
import de.luca.lunarhorizon.engine.graphics.renderer.buffers.VertexBufferLayout;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

public class Model {
	private static final int[] DEFAULT_INDICES = {
			0, 1, 2,
			2, 3, 0
	};

	private final VertexArray vertexArray;
	private final IndexBuffer indexBuffer;
	private final float[] textureUvs;

	public Model(float[] vertices) {
		this.textureUvs = new float[] {vertices[2], vertices[3], vertices[6], vertices[7], vertices[10], vertices[11], vertices[14], vertices[15]};

		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.length);
		vertexData.put(vertices);
		vertexData.flip();

		VertexArray va = new VertexArray();
		VertexBuffer vb = new VertexBuffer(vertexData);

		VertexBufferLayout layout = new VertexBufferLayout();
		layout.pushf(2);
		layout.pushf(2);
		va.addBuffer(vb, layout);

		this.indexBuffer = new IndexBuffer(DEFAULT_INDICES, 6);
		this.vertexArray = va;
	}

	public float[] getTextureUvs() {
		return this.textureUvs;
	}

	public VertexArray getVertexArray() {
		return this.vertexArray;
	}

	public IndexBuffer getIndexBuffer() {
		return this.indexBuffer;
	}
}
