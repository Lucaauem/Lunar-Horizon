package de.luca.lunarhorizon.engine.controls;

import java.util.Map;
import static org.lwjgl.glfw.GLFW.*;

public abstract class Controller {
	protected static boolean needReset = false;

	public void checkInputs(float dt) {
		Map<Integer, Runnable> keyActions = Map.of(
				GLFW_KEY_W, ()     -> this.onUp(dt),
				GLFW_KEY_A, ()     -> this.onLeft(dt),
				GLFW_KEY_S, ()     -> this.onDown(dt),
				GLFW_KEY_D, ()     -> this.onRight(dt),
				GLFW_KEY_E, () 	   -> this.onStart(dt),
				GLFW_KEY_SPACE, () -> this.onAction(dt)
		);

		boolean keyPressed = false;
		for(var entry : keyActions.entrySet()) {
			if (KeyListener.isKeyPressed(entry.getKey())) {
				keyPressed = true;
				if(!needReset) {
					entry.getValue().run();
				}
				break;
			}
		}

		if(!keyPressed) {
			needReset = false;
		}
	}

	protected abstract void onUp(float dt);
	protected abstract void onDown(float dt);
	protected abstract void onLeft(float dt);
	protected abstract void onRight(float dt);
	protected abstract void onStart(float dt);
	protected abstract void onAction(float dt);
}
