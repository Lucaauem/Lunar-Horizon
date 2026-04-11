package engine.core;

import engine.input.KeyListener;
import engine.rendering.renderer.Renderer;
import static org.lwjgl.glfw.GLFW.*;

public class Engine {
	public static final int FPS_CAP = 60;

	private final long window;
  private final GameImplementation game;

	public Engine(long window, GameImplementation game) {
		this.window = window;
    this.game = game;
		Renderer.getShader().bind();
	}

	public void start() {
    Time.init();
    this.game.init();

		while (!glfwWindowShouldClose(window)) {
			if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
				System.exit(0);
			}

      if (!Time.update()) {
        continue;
      }

      // System.out.println((1.0f / Time.deltaTime()) + "FPS");
			this.game.update(Time.deltaTime());

      Renderer.clear();
			this.game.render();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
}
