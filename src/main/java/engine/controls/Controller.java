package engine.controls;

import engine.Game;
import static org.lwjgl.glfw.GLFW.*;

public class Controller {
	private static Controller instance;

	private Controller () {}

	public static Controller getInstance () {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public void checkInputs(float dt) {
		if(KeyListener.isKeyPressed(GLFW_KEY_W)) {
			Game.player.move(0, dt);
		}else if(KeyListener.isKeyPressed(GLFW_KEY_A)) {
			Game.player.move(-dt, 0);
		}else if(KeyListener.isKeyPressed(GLFW_KEY_S)) {
			Game.player.move(0, -dt);
		}else if(KeyListener.isKeyPressed(GLFW_KEY_D)) {
			Game.player.move(dt, 0);
		}
	}
}
