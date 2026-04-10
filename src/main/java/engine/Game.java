package engine;

import engine.controls.*;
import engine.graphics.Camera;
import engine.graphics.renderer.Renderer;
import engine.mechanics.items.Potion;
import engine.objects.Player;
import engine.scenes.SceneManager;
import engine.ui.UIManager;
import engine.ui.screen.OverworldUI;
import util.Time;
import static org.lwjgl.glfw.GLFW.*;

public class Game {
	public static final int FPS_CAP = 60;

	private final long window;
	public static Camera camera = new Camera();
	public static final Player player = new Player();
	private static GameState state = GameState.OVERWORLD;

	public Game(long window) {
		this.window = window;
		Renderer.getShader().bind();

		player.addToInventory(new Potion());
		player.addToInventory(new Potion());
	}

	private void init() {
    InputManager.getInstance().setController(new PlayerController());
		SceneManager.getInstance().switchScene("town/main");
		camera.fix(player);

    UIManager.getInstance().setUI(new OverworldUI());
	}

	public static void changeState(GameState newState) {
		Game.state = newState;

    switch (Game.state) {
      case OVERWORLD -> {
        UIManager.getInstance().setUI(new OverworldUI());
        InputManager.getInstance().setController(new PlayerController());
      }
      case BATTLE -> {
        UIManager.getInstance().getUI().toggle();
        InputManager.getInstance().setController(null);
      }
    }
	}

	public void start() {
		this.init();
    Time.init();

		while (!glfwWindowShouldClose(window)) {
			if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
				System.exit(0);
			}

      if (!Time.update()) {
        continue;
      }

			this.update();
			this.render();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	private void update() {
		// System.out.println((1.0f / Time.deltaTime()) + "FPS");
		InputManager.getInstance().update(Time.deltaTime());

		if (Game.state == GameState.OVERWORLD) {
			player.update();
		}
	}

	private void render() {
		Renderer.clear();

		switch (state) {
			case OVERWORLD -> {
				camera.update();
				SceneManager.getInstance().getCurrentScene().render();
				player.render();

        UIManager.getInstance().render();
			}
			case BATTLE -> UIManager.getInstance().render();
		}
	}
}
