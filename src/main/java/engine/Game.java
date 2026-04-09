package engine;

import engine.battle.BattleEngine;
import engine.controls.*;
import engine.graphics.Camera;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.shader.Shader;
import engine.mechanics.items.Potion;
import engine.objects.Player;
import engine.scenes.SceneManager;
import engine.ui_new.UIManager;
import engine.ui_new.screen.OverworldUI;
import util.Time;
import static org.lwjgl.glfw.GLFW.*;

public class Game {
	private static final int FPS_CAP = 60;

	private final long window;
	public static Shader shader = new Shader("src/main/assets/shaders/Basic.glsl");
	private final Renderer renderer;
	public static Camera camera = new Camera();
	public static final Player player = new Player();
	private static GameState state = GameState.OVERWORLD;

	public Game(long window) {
		this.window = window;
		Game.shader.bind();

		this.renderer = Renderer.getInstance();

		player.addToInventory(new Potion());
		player.addToInventory(new Potion());
	}

	private void init() {
    InputManager.getInstance().setController(new PlayerController());
		SceneManager.getInstance().switchScene("town/main");
		camera.fix(player);

    UIManager.getInstance().setUI(new OverworldUI());

    new BattleEngine().startBattle();
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
		InputManager.getInstance().update(dt);

		if (Game.state == GameState.OVERWORLD) {
			player.update();
		}

	}

	private void render() {
		GameWindow.setScale();
		this.renderer.clear();

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
