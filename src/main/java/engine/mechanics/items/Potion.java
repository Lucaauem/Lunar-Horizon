package engine.mechanics.items;

import engine.Game;

public class Potion extends Item {
	private static final String POTION_NAME = "POTION";
	private static final int HEALING_POWER = 10;

	public Potion() {
		super(POTION_NAME);
	}

	@Override
	public void use() {
		Game.player.heal(HEALING_POWER);
		Game.player.removeFromInventory(this);
	}
}
