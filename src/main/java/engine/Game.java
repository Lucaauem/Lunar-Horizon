package engine;

import engine.controls.Controller;
import engine.controls.KeyListener;
import engine.graphics.Camera;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.shader.Shader;
import engine.objects.Player;
import util.Time;
import static org.lwjgl.glfw.GLFW.*;

public class Game {
	private final long window;
	public static Shader shader = new Shader("src/main/assets/shaders/Basic.glsl");
	private final Renderer renderer;
	public static Camera camera = new Camera();
	private static final Overworld overworld = Overworld.getInstance();
	public static final Player player = new Player();

	public Game(long window) {
		this.window = window;
		Game.shader.bind();

		this.renderer = Renderer.getInstance();
	}

	private void init() {
		camera.fix(player);
	}

	public void start() {
		this.init();

		float lastTime = Time.getTime();
		while (!glfwWindowShouldClose(window)) {
			if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
				System.exit(0);
			}

			float currentTime = Time.getTime();
			float dt = currentTime - lastTime;
			lastTime = currentTime;

			this.update(dt);

			this.render();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	private void update(float dt) {
		System.out.println((1.0f / dt) + "FPS");
		Controller.getInstance().checkInputs(dt);
	}

	private void render() {
		GameWindow.setScale();
		this.renderer.clear();
		camera.update();
		overworld.render();
		player.render();
	}
}
