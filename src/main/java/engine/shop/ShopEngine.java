package engine.shop;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import engine.scenes.MenuScene;
import engine.ui.menu.BattleList;
import engine.ui.menu.ListElement;
import org.joml.Matrix4f;
import org.json.JSONArray;
import org.json.JSONObject;
import util.FileHandler;
import java.util.ArrayList;
import java.util.Map;

public class ShopEngine extends MenuScene {
	private static final String TEXT_SOURCE = "textbox/shop";
	private static final String SHOP_DATA_PATH = "src/main/assets/data/shops.json";
	private static final Texture BACKGROUND_TEXTURE = new Texture("shop/background.png");
	private static final Model BACKGROUND_MODEL = new Model(new float[] {
			0.0f, 0.0f, 0, 1,
			1.0f, 0.0f, 1, 1,
			1.0f, 1.0f, 1, 0,
			0.0f, 1.0f, 0, 0
	});

	private final BattleList shopItems = new BattleList();

	public ShopEngine(String config) {
		super();
		this.loadItems(config);
		this.activeMenu = this.shopItems;
		this.shopItems.setVisibility(true);
	}

	private void loadItems(String config) {
		JSONObject shopConfigs = FileHandler.readJSON(SHOP_DATA_PATH);
		JSONArray shopItems = shopConfigs.getJSONArray(config);

		ArrayList<ListElement> listItems = new ArrayList<>();
		for(int i = 0; i < shopItems.length(); i++) {
			JSONArray shopItemData = shopItems.getJSONArray(i);
			ShopItem shopItem = new ShopItem(shopItemData.getString(0), shopItemData.getInt(1));

			listItems.add(new ListElement(shopItemData.getString(0), () -> {
				boolean bought = shopItem.buy();

				if(bought) {
					this.textbox.setTexts(ShopEngine.TEXT_SOURCE, "ITEM_BOUGHT", Map.of("ITEM", shopItem.getName()));
				}else {
					this.textbox.setTexts(ShopEngine.TEXT_SOURCE, "ITEM_NOT_BOUGHT");
				}
				this.textbox.open();
			}));
		}

		this.shopItems.setItems(listItems.toArray(new ListElement[0]), () -> {
			this.shopItems.setVisibility(false);
			this.textbox.setTexts(ShopEngine.TEXT_SOURCE, "SHOP_EXIT");
			this.textbox.open(this::end);
		});
	}

	@Override
	public void render() {
		Matrix4f proj = new Matrix4f().ortho(0.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		Game.shader.setUniformMat4f("u_MVP", proj);

		BACKGROUND_TEXTURE.bind();
		Renderer.getInstance().draw(BACKGROUND_MODEL.getVertexArray(), BACKGROUND_MODEL.getIndexBuffer(), Game.shader);

		if(this.shopItems.isVisible()) {
			this.shopItems.render();
		}

		if(this.textbox.isOpen()) {
			this.textbox.render();
		}
	}
}
