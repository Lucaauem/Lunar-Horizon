package engine.input;

import java.util.Stack;

public class InputManager {
  private final Stack<Controller> controllerStack;
  private static InputManager instance;
  private Controller activeController;
  private boolean enabled = true;

  private InputManager() {
    this.controllerStack = new Stack<>();
  }

  public static InputManager getInstance() {
    if (instance == null) {
      instance = new InputManager();
    }
    return instance;
  }

  public void enableControlls() {
    this.enabled = true;
  }

  public void disableControlls() {
    this.enabled = false;
  }

  public void pushController(Controller controller) {
    Controller.setGlobalResetFlag();
    this.controllerStack.push(controller);
    this.activeController = this.controllerStack.peek();
  }

  public void popController() {
    Controller.setGlobalResetFlag();
    this.controllerStack.pop();
    this.activeController = this.controllerStack.peek();
  }

  public void update(float dt) {
    if (this.enabled && (activeController != null)) {
      activeController.checkInputs(dt);
    }
  }
}
