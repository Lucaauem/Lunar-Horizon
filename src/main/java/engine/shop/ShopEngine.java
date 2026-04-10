package engine.shop;

import engine.ui.UIManager;
import engine.ui.screen.ShopUI;
import engine.ui.screen.UIScreen;
import org.json.JSONArray;
import org.json.JSONObject;
import util.FileHandler;

public class ShopEngine {
	private static final String SHOP_DATA_PATH = "src/main/assets/data/shops.json";

	public ShopEngine(String config) {
		super();
    ShopItem[] items = this.loadItems(config);
    UIScreen shopUI = new ShopUI(items);
    UIManager.getInstance().setUI(shopUI);
    UIManager.getInstance().getUI().toggle();
	}

	private ShopItem[] loadItems(String config) {
		JSONObject shopConfigs = FileHandler.readJSON(SHOP_DATA_PATH);
		JSONArray shopItems = shopConfigs.getJSONArray(config);
    ShopItem[] items = new ShopItem[shopItems.length()];

		for(int i=0; i<shopItems.length(); i++) {
			JSONArray shopItemData = shopItems.getJSONArray(i);
      items[i] = new ShopItem(shopItemData.getString(0), shopItemData.getInt(1));
		}

    return items;
	}
}
