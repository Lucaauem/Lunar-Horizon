package engine;

import engine.controls.KeyListener;
import engine.graphics.Camera;
import engine.objects.GameObject;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.shader.Shader;
import org.joml.Vector2f;
import util.Time;
import static org.lwjgl.glfw.GLFW.*;

public class Game {
	private final long window;
	public static Shader shader = new Shader("src/main/assets/shaders/Basic.glsl");
	private final Renderer renderer;
	public static Camera camera = new Camera();

	public Game(long window) {
		this.window = window;
		Game.shader.bind();

		this.renderer = Renderer.getInstance();
	}

	public void startGameLoop() {
		float beginTime = Time.getTime();
		float endTime;
		float dt = -1.0f;

		int camOffsetX = 0;
		int camOffsetY = 0;
		int osFactor = 10;

		GameObject debugObj = new GameObject("sample.png", new Vector2f());
		camera.fix(debugObj);

		while ( !glfwWindowShouldClose(window) ) {
			if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
				System.exit(0);
			} else if(KeyListener.isKeyPressed(GLFW_KEY_UP)){
				camOffsetY += osFactor;
				camera.setOffset(camOffsetX, camOffsetY);
			} else if(KeyListener.isKeyPressed(GLFW_KEY_DOWN)){
				camOffsetY -= osFactor;
				camera.setOffset(camOffsetX, camOffsetY);
			} else if(KeyListener.isKeyPressed(GLFW_KEY_LEFT)){
 				camOffsetX -= osFactor;
				camera.setOffset(camOffsetX, camOffsetY);
			} else if(KeyListener.isKeyPressed(GLFW_KEY_RIGHT)){
				camOffsetX += osFactor;
				camera.setOffset(camOffsetX, camOffsetY);
			}

			if(dt >= 0) {
				this.renderer.clear();
				camera.update();
				debugObj.render();
				//System.out.println((1.0f / dt) + "FPS");
			}

			glfwSwapBuffers(window);
			glfwPollEvents();

			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
	}
}
