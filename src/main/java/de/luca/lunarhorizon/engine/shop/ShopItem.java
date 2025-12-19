package de.luca.lunarhorizon.engine.shop;

import de.luca.lunarhorizon.engine.Game;
import de.luca.lunarhorizon.engine.mechanics.items.Item;
import java.lang.reflect.Constructor;

public class ShopItem {
	private static final String ITEM_PACKAGE = "engine.mechanics.items.";

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
		if(Game.player.getMoney() < this.costs) {
			return false;
		}

		Game.player.changeMoney(-this.costs);
		Game.player.addToInventory(this.item);
		return true;
	}

	public String getName() {
		return this.item.getName();
	}
}
