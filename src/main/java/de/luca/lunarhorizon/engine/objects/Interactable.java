package de.luca.lunarhorizon.engine.objects;

import org.joml.Vector2f;

public abstract class Interactable extends Entity {
	public Interactable(String texturePath, Vector2f position, boolean isSolid) {
		super(texturePath, position, isSolid);
	}

	public abstract void onInteract();
}
