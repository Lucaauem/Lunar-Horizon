package engine.ui;

import java.util.ArrayList;

public class UIManager {
  private static UIManager instance;

  private UIScreen activeUI;
  private final ArrayList<UIElement> additionalElements;

  private UIManager() {
    this.additionalElements = new ArrayList<>();
  }

  public static UIManager getInstance() {
    if(UIManager.instance == null) {
      UIManager.instance = new UIManager();
    }
    return UIManager.instance;
  }

  public void addElement(UIElement element) {
    this.additionalElements.add(element);
  }

  public void removeElement(UIElement element) {
    this.additionalElements.remove(element);
  }

  public boolean isAdditionalElement(UIElement element) {
    return this.additionalElements.contains(element);
  }

  public void setUI(UIScreen ui) {
    this.activeUI = ui;
  }

  public UIScreen getUI() {
    return this.activeUI;
  }

  public void render() {
    this.activeUI.render();

    for(UIElement additionalElement: this.additionalElements) {
      additionalElement.render();
    }
  }
}
