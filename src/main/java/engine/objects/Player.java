package engine.objects;

import org.joml.Vector2f;

public class Player extends Entity{
	private static final String PLAYER_TEXTURE_PATH = "sample.png";

	public Player() {
		super(PLAYER_TEXTURE_PATH, new Vector2f(0, 0));
	}
}
