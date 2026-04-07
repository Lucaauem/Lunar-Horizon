package engine.ui_new.screen;

import engine.ui_new.UIElement;
import engine.ui_new.UILayer;
import engine.ui_new.components.UIPanel;
import engine.ui_new.components.list.UIList;
import engine.ui_new.components.list.UIListElement;

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
