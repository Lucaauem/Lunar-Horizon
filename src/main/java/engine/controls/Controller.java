package engine.controls;

import java.util.Map;
import static org.lwjgl.glfw.GLFW.*;

public abstract class Controller {
	protected static boolean needReset = false;

  private Runnable customUp;
  private Runnable customDown;
  private Runnable customLeft;
  private Runnable customRight;
  private Runnable customStart;
  private Runnable customAction;

  private Runnable resolve(Runnable custom, Runnable fallback) {
    return custom != null ? custom : fallback;
  }

	public void checkInputs(float dt) {
    Map<Integer, Runnable> keyActions = Map.of(
      GLFW_KEY_W, resolve(this.customUp, () -> this.onUp(dt)),
      GLFW_KEY_A, resolve(this.customLeft, () -> this.onLeft(dt)),
      GLFW_KEY_S, resolve(this.customDown, () -> this.onDown(dt)),
      GLFW_KEY_D, resolve(this.customRight, () -> this.onRight(dt)),
      GLFW_KEY_E, resolve(this.customStart, () -> this.onStart(dt)),
      GLFW_KEY_SPACE, resolve(this.customAction, () -> this.onAction(dt))
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

  public Controller withCustomUp(Runnable action) {
    this.customUp = action;
    return this;
  }

  public Controller withCustomDown(Runnable action) {
    this.customDown = action;
    return this;
  }

  public Controller withCustomLeft(Runnable action) {
    this.customLeft = action;
    return this;
  }

  public Controller withCustomRight(Runnable action) {
    this.customRight = action;
    return this;
  }

  public Controller withCustomStart(Runnable action) {
    this.customStart = action;
    return this;
  }

  public Controller withCustomAction(Runnable action) {
    this.customAction = action;
    return this;
  }
}
