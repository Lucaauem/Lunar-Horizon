package de.luca.lunarhorizon.engine.mechanics.items;

public abstract class Item {
	private final String name;

	protected Item(String itemName) {
		this.name = itemName;
	}

	public abstract void use();

	public String getName() {
		return this.name;
	}
}
