package engine.objects;

import org.joml.Vector2f;

public class Entity extends GameObject {
	public Entity(String texturePath, Vector2f position) {
		super(texturePath, position);
	}

	public void move(float dx, float dy) {
		this.position.add(new Vector2f(dx, dy));
	}
}
