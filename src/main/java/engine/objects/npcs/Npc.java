package engine.objects.npcs;

import engine.objects.Entity;
import org.joml.Vector2f;

public abstract class Npc extends Entity {
	public Npc(String texturePath, Vector2f position) {
		super(texturePath, position);
	}

	public abstract void onInteract();
}
