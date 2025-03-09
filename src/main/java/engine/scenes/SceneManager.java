package engine.scenes;

import engine.Game;
import engine.objects.Tile;
import org.joml.Vector2f;
import org.joml.Vector2i;
import java.util.Stack;

public class SceneManager {
	private static final SceneManager instance = new SceneManager();

	private final Stack<SceneMemento> lastScenePosition = new Stack<>();
	private Scene currentScene;

	private SceneManager() {}

	public static SceneManager getInstance() {
		return instance;
	}

	public void switchScene(String sceneName) {
		if(this.currentScene != null) {
			Vector2f lastValidPosition = new Vector2f(Game.player.getLastValidPosition().x, Game.player.getLastValidPosition().y);
			SceneMemento sceneMemento = new SceneMemento(this.currentScene.getName(), lastValidPosition);
			this.lastScenePosition.push(sceneMemento);
		}

		this.currentScene = new Scene(sceneName);

		Vector2i playerSpawn = this.currentScene.getSpawn();
		Game.player.setPosition(new Vector2f(playerSpawn.x * Tile.TILE_SIZE, playerSpawn.y * Tile.TILE_SIZE));
	}

	public void returnToLastScene() {
		if(this.lastScenePosition.isEmpty()) { return; }

		SceneMemento memento = this.lastScenePosition.pop();
		this.switchScene(memento.getSceneName());
		Game.player.setPosition(memento.getPosition());
	}

	public Scene getCurrentScene() {
		return this.currentScene;
	}
}
