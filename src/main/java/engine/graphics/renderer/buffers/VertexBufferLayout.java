package engine.graphics.renderer.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class VertexBufferLayout {
	private final List<VertexBufferElement> elements;
	private int stride;

	public VertexBufferLayout() {
		this.elements = new ArrayList<>();
	}

	public void pushf(int count){
		this.elements.add(new VertexBufferElement(GL_FLOAT, count, false));
		this.stride += Float.BYTES * count;
	}

	public void pushi(int count){
		this.elements.add(new VertexBufferElement(GL_UNSIGNED_INT, count, false));
		this.stride += Integer.BYTES * count;
	}

	public void pushc(int count){
		this.elements.add(new VertexBufferElement(GL_UNSIGNED_BYTE, count, false));
		this.stride += Byte.BYTES * count;
	}

	public List<VertexBufferElement> getElements() {
		return this.elements;
	}

	public int getStride(){
		return this.stride;
	}
}
