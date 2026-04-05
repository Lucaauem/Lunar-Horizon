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
    if (this.activeUI != null) {
      this.activeUI.toggle();
    }

    this.activeUI = ui;
    this.activeUI.toggle();
  }

  public UIScreen getUI() {
    return this.activeUI;
  }

  public void render() {
    this.activeUI.render();
  }
}
