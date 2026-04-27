package engine.rendering;

import engine.core.GameWindow;
import engine.input.KeyListener;
import engine.objects.core.GameObject;
import engine.rendering.renderer.Renderer;
import engine.scenes.SceneManager;
import org.joml.*;
import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class Camera {
	private final Matrix4f matrix;
	private final Vector2f offset;
	private GameObject fixObj;
	private int camOffsetX = 0;
	private int camOffsetY = 0;
	private float zoom = 1f;

	public Camera() {
		this.matrix = new Matrix4f().identity();
		this.offset = new Vector2f();

    if (Renderer.camera == null) {
      Renderer.camera = this;
    }
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
    Vector2f objectPosition = this.fixObj.getTransform().getPosition();

    float targetX = 2 * GameObject.DEFAULT_TILE_SIZE + this.offset.x + objectPosition.x;
    float targetY = 2 * GameObject.DEFAULT_TILE_SIZE + this.offset.y + objectPosition.y;
    float halfW = (GameWindow.RESOLUTION.x / zoom) / 2f;
    float halfH = (GameWindow.RESOLUTION.y / zoom) / 2f;
    float sceneWidth = (SceneManager.getInstance().getCurrentScene().getSize().x + 1) * GameObject.DEFAULT_TILE_SIZE;
    float sceneHeight = (SceneManager.getInstance().getCurrentScene().getSize().y + 2) * GameObject.DEFAULT_TILE_SIZE;
    float clampedX = Math.max(halfW + GameObject.DEFAULT_TILE_SIZE, Math.min(targetX, sceneWidth - halfW));
    float clampedY = Math.max(halfH + 2 * GameObject.DEFAULT_TILE_SIZE, Math.min(targetY, sceneHeight - halfH));

    Matrix4f matrix = new Matrix4f().identity();
    matrix.translate(GameWindow.RESOLUTION.x / 2.0f, GameWindow.RESOLUTION.y / 2.0f, 0);
    matrix.scale(this.zoom, this.zoom, 1.0f);
    matrix.translate(-clampedX, -clampedY, 0);

    this.matrix.set(matrix);
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

  public float getZoom() {
    return this.zoom;
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
