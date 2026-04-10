package engine.objects.entities.npcs;

import engine.objects.entities.Entity;
import engine.objects.Interactable;
import org.joml.Vector2f;

public abstract class Npc extends Entity implements Interactable {
	public Npc(String texturePath, Vector2f position, boolean isSolid) {
		super(texturePath, new Vector2f(position).mul(DEFAULT_TILE_SIZE), isSolid);
	}
}
