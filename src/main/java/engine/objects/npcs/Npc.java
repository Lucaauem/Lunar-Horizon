package engine.objects.npcs;

import engine.objects.Interactable;
import org.joml.Vector2f;

public abstract class Npc extends Interactable {
	public Npc(String texturePath, Vector2f position, boolean isSolid) {
		super(texturePath, position.mul(DEFAULT_TILE_SIZE), isSolid);
	}
}
