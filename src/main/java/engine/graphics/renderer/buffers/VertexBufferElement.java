package engine.graphics.renderer.buffers;

import static org.lwjgl.opengl.GL11.*;

public class VertexBufferElement {
	public int count;
	public int type;
	public boolean normalized;

	public VertexBufferElement(int type, int count, boolean normalized) {
		this.count = count;
		this.type = type;
		this.normalized = normalized;
	}

	public static int getSizeOfType(int type){
		switch(type){
			case GL_FLOAT, GL_UNSIGNED_INT: return 4;
			case GL_UNSIGNED_BYTE: return 1;
		}

		assert (false);
		return 0;
	}
}
