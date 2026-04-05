package engine.ui_new.screen;

import engine.Game;
import engine.controls.MenuController;
import engine.ui_new.UIElement;
import engine.ui_new.UILayer;
import engine.ui_new.components.UIPanel;
import engine.ui_new.components.list.UIList;
import engine.ui_new.components.list.UIListElement;

public class OverworldUI extends UIScreen {
  public OverworldUI() {
    super();
    this.init();
  }

  @Override
  public void init() {
    UIPanel menuPanel = new UIPanel(0.65f, 0.05f, 0.95f, 0.95f);

    UIList meunuList = new UIList();
    meunuList.setParent(menuPanel);
    for (int i=0; i<14; i++) {
      int finalI = i;
      meunuList.addContent(new UIListElement("item " + i, () -> System.out.println(finalI)));
    }

    this.layers.add(new UILayer(0, new UIElement[]{menuPanel}));

    Game.controllers.put("OVERWORLD_MENU", new MenuController(meunuList));
  }
}
