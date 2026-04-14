package game.ui.screens;

import engine.input.Controller;
import engine.input.InputManager;
import engine.objects.MoveDirection;
import engine.ui.UIScreen;
import game.GameApplication;
import game.shop.ShopItem;
import engine.ui.UIElement;
import engine.ui.UILayer;
import engine.ui.UIManager;
import engine.ui.components.UIPanel;
import engine.ui.components.UITextbox;
import engine.ui.components.list.UIList;
import engine.ui.components.list.UIListElement;
import org.joml.Vector3f;
import java.util.Map;

public class ShopUI extends UIScreen {
  private static final String TEXT_SOURCE = "textbox/shop";
  private static final String MONEY_SYMBOL = "\" ";

  private final ShopItem[] listItems;
  private UIList shopList;

  public ShopUI(ShopItem[] items) {
    super();
    this.listItems = items;
    this.init();
    InputManager.getInstance().pushController(Controller.forUI(this.shopList));
  }

  @Override
  public void init() {
    // Background
    UIPanel background = new UIPanel(0.0f, 0.0f, 1.0f, 1.0f);
    background.setColor(new Vector3f(0, 0, 0));
    this.layers.add(new UILayer(0, new UIElement[]{background}));

    // Shop Menu
    UIPanel menuPanel = new UIPanel(0.65f, 0.05f, 0.95f, 0.95f);

    UIList shopList = new UIList();
    shopList.setParent(menuPanel);
    this.shopList = shopList;
    UITextbox textbox = new UITextbox();

    for (ShopItem item : listItems) {
      String itemDisplayName = item.getCosts() + MONEY_SYMBOL + item.getName();
      shopList.addContent(new UIListElement(itemDisplayName, () -> {
        if (item.buy()) {
          textbox.setTexts(ShopUI.TEXT_SOURCE, "ITEM_BOUGHT", Map.of("ITEM", item.getName()));
        } else {
          textbox.setTexts(ShopUI.TEXT_SOURCE, "ITEM_NOT_BOUGHT");
        }
        textbox.open();
      }));

      shopList.addContent(new UIListElement("EXIT", this::onExit));

      this.layers.add(new UILayer(1, new UIElement[]{menuPanel, textbox}));
    }
  }

  @Override
  public void onExit() {
    UIManager.getInstance().setUI(new OverworldUI());
    GameApplication.player.move(MoveDirection.DOWN);
    InputManager.getInstance().popController();
  }
}
