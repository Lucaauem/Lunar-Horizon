import engine.controls.KeyListener;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
	public static void main(String[] args) {
		Window window = new Window();
		window.create();

		while (!glfwWindowShouldClose(window.getWindow())) {
			if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
				System.exit(0);
			}

			glfwSwapBuffers(window.getWindow());
			glfwPollEvents();
		}

		window.destroy();
	}
}
