package engine.graphics;

import engine.GameWindow;
import engine.controls.KeyListener;
import engine.objects.GameObject;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class Camera {
	private final Matrix4f matrix;
	private final Vector2f offset;
	private GameObject fixObj;
	private int camOffsetX = 0;
	private int camOffsetY = 0;
	private float zoom = 1.5f;

	public Camera() {
		this.matrix = new Matrix4f().identity();
		this.offset = new Vector2f();
	}

	public void setOffset(float ox, float oy) {
		this.offset.set(ox, oy);
	}

	public void fix(GameObject fixObj) {
		this.fixObj = fixObj;
	}

	public Matrix4f getMatrix() {
		return this.matrix;
	}

	public void update(){
		Matrix4f matrix = new Matrix4f().identity();
		Vector2f fullOffset = new Vector2f(
				GameObject.DEFAULT_TILE_SIZE + this.offset.x + this.fixObj.getPosition().x,
				GameObject.DEFAULT_TILE_SIZE + this.offset.y + this.fixObj.getPosition().y
		);

		matrix.translate(GameWindow.RESOLUTION.x / 2.0f, GameWindow.RESOLUTION.y / 2.0f, 0);
		matrix.scale(this.zoom, this.zoom, 1.0f);
		matrix.translate(-fullOffset.x, -fullOffset.y, 0);

		this.matrix.set(matrix);
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public void move() {
		int osFactor = 10;

		if(KeyListener.isKeyPressed(GLFW_KEY_UP)){
			camOffsetY += osFactor;
			this.setOffset(camOffsetX, camOffsetY);
		} else if(KeyListener.isKeyPressed(GLFW_KEY_DOWN)){
			camOffsetY -= osFactor;
			this.setOffset(camOffsetX, camOffsetY);
		} else if(KeyListener.isKeyPressed(GLFW_KEY_LEFT)){
			camOffsetX -= osFactor;
			this.setOffset(camOffsetX, camOffsetY);
		} else if(KeyListener.isKeyPressed(GLFW_KEY_RIGHT)){
			camOffsetX += osFactor;
			this.setOffset(camOffsetX, camOffsetY);
		}
	}
}
