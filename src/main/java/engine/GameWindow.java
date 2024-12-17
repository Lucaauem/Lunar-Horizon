package engine;

import engine.controls.KeyListener;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {
	public static int WINDOW_HEIGHT = 720;
	public static int WINDOW_WIDTH = 1296;
	private long  window;

	public void create() {
		// Setup an error callback. The default implementation
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()){
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Set OpenGL profile
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		// Create the window
		window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Hello World!", NULL, NULL);

		if (window == NULL){
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			assert vidmode != null;
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		glfwSetKeyCallback(this.window, KeyListener::keyCallback);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); // Enable v-sync

		glfwShowWindow(window);
		GL.createCapabilities();

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
	}

	public void destroy() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		Objects.requireNonNull(glfwSetErrorCallback(null)).free();
	}

	public long getWindow(){
		return this.window;
	}
}
