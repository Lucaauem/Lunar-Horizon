package engine.input;

public class InputManager {
  private static InputManager instance;
  private Controller activeController;
  private Controller previousController;

  private InputManager() {}

  public static InputManager getInstance() {
    if (instance == null) {
      instance = new InputManager();
    }
    return instance;
  }

  public void setController(Controller controller) {
    this.previousController = this.activeController;
    this.activeController = controller;
  }

  public Controller getController() {
    return this.activeController;
  }

  public void setToPreviousController() {
    this.setController(this.previousController);
  }

  public void update(float dt) {
    if (activeController != null) {
      activeController.checkInputs(dt);
    }
  }
}
