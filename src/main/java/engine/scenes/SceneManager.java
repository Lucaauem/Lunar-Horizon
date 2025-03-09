package engine.scenes;

import engine.Game;
import engine.objects.Tile;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class SceneManager {
	private static final SceneManager instance = new SceneManager();

	private Scene currentScene;

	private SceneManager() {}

	public static SceneManager getInstance() {
		return instance;
	}

	public void switchScene(String sceneName) {
		this.currentScene = new Scene(sceneName);

		Vector2i playerSpawn = this.currentScene.getSpawn();
		Game.player.setPosition(new Vector2f(playerSpawn.x * Tile.TILE_SIZE, playerSpawn.y * Tile.TILE_SIZE));
	}

	public Scene getCurrentScene() {
		return this.currentScene;
	}
}
