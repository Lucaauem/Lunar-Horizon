package game.ui.screens;

import engine.input.Controller;
import engine.input.InputManager;
import engine.ui.UIElement;
import engine.ui.UILayer;
import engine.ui.UIManager;
import engine.ui.components.UIPanel;
import engine.ui.components.UITextbox;
import engine.ui.components.list.UIList;
import engine.ui.components.list.UIListElement;
import engine.ui.UIScreen;
import game.GameApplication;
import game.mechanics.items.Item;

import java.util.Map;

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

    menuList.addContent(new UIListElement("INVENTORY", this::openInventory));

    this.menu = menuList;
    this.layers.add(new UILayer(0, new UIElement[]{menuPanel}));
  }

  private void openInventory() {
    UIPanel menuPanel = new UIPanel(0.65f, 0.05f, 0.95f, 0.95f);
    UIList menuList = new UIList();
    menuList.setParent(menuPanel);

    this.loadInventory(menuList, menuPanel);

    InputManager.getInstance().pushController(Controller.forUI(menuList));

    UIManager.getInstance().addElement(menuPanel);
  }

  private void loadInventory(UIList inventory, UIPanel panel) {
    for (Item item : GameApplication.player.getInventory()) {
      inventory.addContent(new UIListElement(item.getName(), () -> {
        item.use();
        inventory.clearContent();
        this.loadInventory(inventory, panel);

        UITextbox textbox = new UITextbox();
        textbox.setTexts("textbox/battle", "PLAYER_USED_ITEM", Map.of("ITEM", item.getName()));
        UIManager.getInstance().addElement(textbox);
        textbox.open();
      }));
    }

    inventory.addContent(new UIListElement("CLOSE", () -> {
      UIManager.getInstance().removeElement(panel);
      InputManager.getInstance().popController();
    }));
  }
}
