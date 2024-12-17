package engine.controls;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
	private static KeyListener instance;
	private final boolean[] keyPressed = new boolean[350];

	private KeyListener() {}

	public static KeyListener getInstance() {
		if (KeyListener.instance == null) {
			KeyListener.instance = new KeyListener();
		}
		return KeyListener.instance;
	}

	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW_PRESS) {
			KeyListener.getInstance().keyPressed[key] = true;
		} else if (action == GLFW_RELEASE) {
			KeyListener.getInstance().keyPressed[key] = false;
		}
	}

	public static boolean isKeyPressed(int keyCode) {
		return getInstance().keyPressed[keyCode];
	}
}