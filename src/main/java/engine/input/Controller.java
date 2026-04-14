package engine.input;

import engine.ui.interaction.UIControllable;
import java.util.Map;
import static org.lwjgl.glfw.GLFW.*;

public class Controller {
  private static boolean globalResetFlag = false; // TODO: Check for better solution

  private KeyAction customUp = new KeyAction(() -> {}, false);
  private KeyAction customDown = new KeyAction(() -> {}, false);
  private KeyAction customLeft = new KeyAction(() -> {}, false);
  private KeyAction customRight = new KeyAction(() -> {}, false);
  private KeyAction customStart = new KeyAction(() -> {}, false);
  private KeyAction customAction = new KeyAction(() -> {}, false);

	public void checkInputs(float dt) {
    Map<Integer, KeyAction> keyActions = Map.of(
      GLFW_KEY_W, customUp,
      GLFW_KEY_A, customLeft,
      GLFW_KEY_S, customDown,
      GLFW_KEY_D, customRight,
      GLFW_KEY_E, customStart,
      GLFW_KEY_SPACE, customAction
    );

    boolean anyKeyPressed = false;
		for(var entry : keyActions.entrySet()) {
      boolean keyPressed = KeyListener.isKeyPressed(entry.getKey());
			if (keyPressed && !Controller.globalResetFlag) {
        entry.getValue().run();
			}
      entry.getValue().setResetFlag(!keyPressed);
      anyKeyPressed = anyKeyPressed || keyPressed;
		}

    Controller.globalResetFlag = Controller.globalResetFlag && anyKeyPressed;
	}

  public static void setGlobalResetFlag() {
    Controller.globalResetFlag = true;
  }

  public Controller bindUp(Runnable action, boolean needReset) {
    this.customUp = new KeyAction(action, needReset);
    return this;
  }

  public Controller bindDown(Runnable action, boolean needReset) {
    this.customDown = new KeyAction(action, needReset);
    return this;
  }

  public Controller bindLeft(Runnable action, boolean needReset) {
    this.customLeft = new KeyAction(action, needReset);
    return this;
  }

  public Controller bindRight(Runnable action, boolean needReset) {
    this.customRight = new KeyAction(action, needReset);
    return this;
  }

  public Controller bindStart(Runnable action, boolean needReset) {
    this.customStart = new KeyAction(action, needReset);
    return this;
  }

  public Controller bindAction(Runnable action, boolean needReset) {
    this.customAction = new KeyAction(action, needReset);
    return this;
  }

  public static Controller forUI(UIControllable ui) {
    return new Controller()
      .bindUp(ui::onUp, true)
      .bindDown(ui::onDown, true)
      .bindLeft(ui::onLeft, true)
      .bindRight(ui::onRight, true)
      .bindStart(ui::onStart, true)
      .bindAction(ui::onAction, true);
  }
}
