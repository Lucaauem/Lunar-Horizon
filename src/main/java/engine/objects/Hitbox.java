package engine.objects;

import org.joml.Vector2f;

public class Hitbox {
	private final float x1;
	private final float y1;
	private final float x2;
	private final float y2;

	public Hitbox(Vector2f position) {
		this.x1 = position.x;
		this.y1 = position.y;
		this.x2 = position.x + GameObject.DEFAULT_TILE_SIZE;
		this.y2 = position.y + GameObject.DEFAULT_TILE_SIZE;
	}

	public Hitbox(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public boolean inBounds(Hitbox other) {
		return this.x1 < other.x2 && this.x2 > other.x1 && this.y1 < other.y2 && this.y2 > other.y1;
	}
}
