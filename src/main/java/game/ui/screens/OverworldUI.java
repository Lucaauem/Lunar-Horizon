package game.ui.screens;

import engine.ui.UIElement;
import engine.ui.UILayer;
import engine.ui.components.UIPanel;
import engine.ui.components.list.UIList;
import engine.ui.components.list.UIListElement;
import engine.ui.UIScreen;

public class OverworldUI extends UIScreen {
  private UIList menu;

  public OverworldUI() {
    super();
    this.init();
  }

  public UIList getMenu() {
    return this.menu;
  }

  @Override
  public void init() {
    UIPanel menuPanel = new UIPanel(0.65f, 0.05f, 0.95f, 0.95f);

    UIList menuList = new UIList();
    menuList.setParent(menuPanel);
    for (int i=0; i<14; i++) {
      int finalI = i;
      menuList.addContent(new UIListElement("item " + i, () -> System.out.println(finalI)));
    }

    this.menu = menuList;
    this.layers.add(new UILayer(0, new UIElement[]{menuPanel}));
  }
}
