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
import engine.ui.text.UIText;
import game.GameApplication;
import game.mechanics.items.Item;
import game.player.Player;

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
    menuList.addContent(new UIListElement("STATUS", this::openStatus));

    this.menu = menuList;
    this.layers.add(new UILayer(0, new UIElement[]{menuPanel}));
  }

  private void openStatus() {
    Player player = GameApplication.player;

    UIPanel statusPanel = new UIPanel(0.05f, 0.05f, 0.95f, 0.95f);

    UIText playerName = new UIText(player.getName(), statusPanel);
    playerName.setAnchor(0.1f, 0.85f, 0.3f, 0.95f);
    UIText playerMoney = new UIText("MNY " + player.getMoney(), statusPanel);
    playerMoney.setAnchor(0.1f, 0.15f, 0.3f, 0.25f);
    UIText playerLevel = new UIText("LVL " + player.getLevel(), statusPanel);
    playerLevel.setAnchor(0.45f, 0.85f, 0.65f, 0.95f);
    UIText playerEXP = new UIText("EXP " + player.getExperience(), statusPanel);
    playerEXP.setAnchor(0.45f, 0.75f, 0.65f, 0.85f);
    UIText playerHP = new UIText("HP " + player.getHealth() + "'" + player.getMaxHealth(), statusPanel);
    playerHP.setAnchor(0.45f, 0.6f, 0.65f, 0.7f);
    UIText playerMP = new UIText("MP " + player.getMagic() + "'" + player.getMaxMagic(), statusPanel);
    playerMP.setAnchor(0.45f, 0.5f, 0.65f, 0.6f);
    UIText playerATK = new UIText("ATK " + player.getAttack(), statusPanel);
    playerATK.setAnchor(0.45f, 0.4f, 0.65f, 0.5f);
    UIText playerDEF = new UIText("DEF " + player.getDefence(), statusPanel);
    playerDEF.setAnchor(0.45f, 0.3f, 0.65f, 0.4f);

    UIList closeBtn = new UIList();
    closeBtn.setParent(statusPanel);
    closeBtn.setAnchor(0.75f, 0.1f, 0.85f, 0.15f);

    closeBtn.addContent(new UIListElement("CLOSE", () -> {
      UIManager.getInstance().removeElement(statusPanel);
      InputManager.getInstance().popController();
    }));

    InputManager.getInstance().pushController(Controller.forUI(closeBtn));
    UIManager.getInstance().addElement(statusPanel);
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
