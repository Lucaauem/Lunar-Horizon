package engine.controls;

public class InputManager {
  private Controller activeController;
  private static InputManager instance;

  private InputManager() {}

  public static InputManager getInstance() {
    if (instance == null) {
      instance = new InputManager();
    }
    return instance;
  }

  public void setController(Controller controller) {
    this.activeController = controller;
  }

  public Controller getController() {
    return this.activeController;
  }

  public void update(float dt) {
    if (activeController != null) {
      activeController.checkInputs(dt);
    }
  }
}
