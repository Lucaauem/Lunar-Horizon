package engine.ui_new;

import engine.ui_new.screen.UIScreen;

public class UIManager {
  private static UIManager instance;

  private UIScreen activeUI;

  private UIManager() {}

  public static UIManager getInstance() {
    if(UIManager.instance == null) {
      UIManager.instance = new UIManager();
    }
    return UIManager.instance;
  }

  public void setUI(UIScreen ui) {
    this.activeUI = ui;
  }

  public UIScreen getUI() {
    return this.activeUI;
  }

  public void render() {
    this.activeUI.render();
  }
}
