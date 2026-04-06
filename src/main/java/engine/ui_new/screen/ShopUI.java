package engine.ui_new.screen;

import engine.Game;
import engine.controls.InputManager;
import engine.controls.MenuController;
import engine.controls.PlayerController;
import engine.objects.MoveDirection;
import engine.shop.ShopItem;
import engine.ui_new.UIElement;
import engine.ui_new.UILayer;
import engine.ui_new.UIManager;
import engine.ui_new.components.UIPanel;
import engine.ui_new.components.UITextbox;
import engine.ui_new.components.list.UIList;
import engine.ui_new.components.list.UIListElement;
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
    InputManager.getInstance().setController(new MenuController(this.shopList));
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
    textbox.setOnClose(() -> InputManager.getInstance().setToPreviousController());

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
    Game.player.move(MoveDirection.DOWN);
    InputManager.getInstance().setController(new PlayerController());
  }
}
