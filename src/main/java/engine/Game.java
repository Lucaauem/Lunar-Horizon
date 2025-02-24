package engine;

import engine.controls.Controller;
import engine.controls.KeyListener;
import engine.controls.MenuController;
import engine.controls.PlayerController;
import engine.graphics.Camera;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.shader.Shader;
import engine.objects.Player;
import engine.scenes.Scene;
import engine.ui.UiManager;
import engine.ui.menu.MenuItem;
import engine.ui.menu.UiMenu;
import util.Time;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Game {
	private static final int FPS_CAP = 60;
	private static final ArrayList<MenuItem> MENU_ITEMS = new ArrayList<>(List.of(
			new MenuItem("ITEM 1", () -> System.out.println(1)),
			new MenuItem("ITEM 2", () -> System.out.println(2))
	));

	private final long window;
	public static Shader shader = new Shader("src/main/assets/shaders/Basic.glsl");
	private final Renderer renderer;
	public static Camera camera = new Camera();
	public static final Player player = new Player();
	private static Controller controller = new PlayerController();
	private static Scene scene;

	public Game(long window) {
		this.window = window;
		Game.shader.bind();

		scene = new Scene("overworld");
		this.renderer = Renderer.getInstance();
	}

	private void init() {
		camera.fix(player);
		UiManager.getInstance().addElement("overworld_menu", new UiMenu(MENU_ITEMS));
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

			if(dt < 1.0f / (float) FPS_CAP) {
				continue;
			}

			lastTime = currentTime;

			this.update(dt);

			this.render();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	private void update(float dt) {
		//System.out.println((1.0f / dt) + "FPS");
		controller.checkInputs(dt);
		player.update();
	}

	private void render() {
		GameWindow.setScale();
		this.renderer.clear();

		camera.update();
		scene.render();
		player.render();

		UiManager.getInstance().render();
	}

	public static void toggleMenu() {
		UiManager.getInstance().getElement("overworld_menu").toggle();

		if(controller instanceof PlayerController) {
			controller = new MenuController((UiMenu) UiManager.getInstance().getElement("overworld_menu"));
			return;
		}
		controller = new PlayerController();
	}
}
