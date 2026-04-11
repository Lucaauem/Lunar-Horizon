package game.mechanics.items;

import game.GameApplication;

public class Potion extends Item {
	private static final String POTION_NAME = "POTION";
	private static final int HEALING_POWER = 10;

	public Potion() {
		super(POTION_NAME);
	}

	@Override
	public void use() {
		GameApplication.player.heal(HEALING_POWER);
		GameApplication.player.removeFromInventory(this);
	}
}
