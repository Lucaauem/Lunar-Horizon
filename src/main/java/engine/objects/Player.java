package engine.objects;

import engine.scenes.Scene;
import engine.scenes.SceneManager;
import org.joml.Vector2f;

public class Player extends Entity {
	private static final String PLAYER_TEXTURE_PATH = "sample.png";

	private final Vector2f lastValidPosition = new Vector2f();

	public Player() {
		super(PLAYER_TEXTURE_PATH, new Vector2f(0, 0));
	}

	public Vector2f getLastValidPosition() {
		return lastValidPosition;
	}

	@Override
	protected void onStepEnd() {
		super.onStepEnd();

		Scene scene = SceneManager.getInstance().getCurrentScene();
		if(!scene.isInScene(this.position.x, this.position.y)) { return; }

		Tile currentTile = scene.getTile(this.position.x, this.position.y);

		if(currentTile.hasTrigger()) {
			currentTile.activateTrigger();
		}

		this.lastValidPosition.set(this.position.x, this.position.y);
	}
}
