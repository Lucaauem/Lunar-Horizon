package engine.objects;

import org.joml.Vector2f;

public class Player extends Entity{
	private static final String PLAYER_TEXTURE_PATH = "sample.png";
	private static final float MOVEMENT_SPEED = 200.0f;

	public Player() {
		super(PLAYER_TEXTURE_PATH, new Vector2f(200, 1000));
	}

	@Override
	public void move(float dx, float dy) {
		super.move(dx * MOVEMENT_SPEED, dy * MOVEMENT_SPEED);
	}
}
