package engine.objects;

import org.joml.Vector2f;

public abstract class Interactable extends Entity {
	public Interactable(String texturePath, Vector2f position) {
		super(texturePath, position);
	}

	public abstract void onInteract();
}
