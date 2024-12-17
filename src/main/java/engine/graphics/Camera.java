package engine.graphics;

import engine.GameWindow;
import engine.objects.GameObject;
import org.joml.*;

public class Camera {
	private final Matrix4f matrix;
	private final Vector2f offset;
	private GameObject fixObj;

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

		if(this.fixObj != null){
			matrix.translate(GameWindow.WINDOW_WIDTH / 2.0f, GameWindow.WINDOW_HEIGHT / 2.0f, 0);
			matrix.translate(-fullOffset.x, -fullOffset.y, 1);
		} else {
			matrix.translate(-fullOffset.x, -fullOffset.y, 0);
		}

		this.matrix.set(matrix);
	}
}
