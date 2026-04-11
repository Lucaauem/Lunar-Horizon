package game.shop;

import game.GameApplication;
import game.mechanics.items.Item;
import java.lang.reflect.Constructor;

public class ShopItem {
	private static final String ITEM_PACKAGE = "game.mechanics.items.";

	private final Item item;
	private final int costs;

	public ShopItem(String name, int costs) {
		try {
			Class<?> myClass = Class.forName(ITEM_PACKAGE + name);
			Constructor<?> constructor = myClass.getConstructor();

			this.item = (Item) constructor.newInstance();
			this.costs = costs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean buy() {
		if(GameApplication.player.getMoney() < this.costs) {
			return false;
		}

		GameApplication.player.changeMoney(-this.costs);
		GameApplication.player.addToInventory(this.item);
		return true;
	}

	public String getName() {
		return this.item.getName();
	}

  public int getCosts() { return this.costs; }
}
