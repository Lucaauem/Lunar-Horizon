package engine.scenes;

import engine.objects.Tile;
import engine.objects.components.MovementComponent;
import engine.objects.entities.Entity;
import engine.objects.trigger.SceneChangeTrigger;
import engine.objects.trigger.Trigger;
import game.GameApplication;
import org.joml.Vector2f;
import org.joml.Vector2i;
import java.util.HashMap;
import java.util.Stack;

public class SceneManager {
	private static final SceneManager instance = new SceneManager();

	private final Stack<SceneMemento> lastScenePosition = new Stack<>();
  protected final HashMap<String, Class<? extends Trigger>> triggerClasses;
	private Scene currentScene;

	private SceneManager() {
    this.triggerClasses = new HashMap<>();
    this.triggerClasses.put("SCENE_CHANGE", SceneChangeTrigger.class); // Default trigger
  }

	public static SceneManager getInstance() {
		return instance;
	}

  public void addTrigger(String id, Class<? extends Trigger> triggerClass) {
    this.triggerClasses.put(id, triggerClass);
  }

	public void switchScene(String sceneName) {
		if(this.currentScene != null) {
			Vector2f lastValidPosition = GameApplication.player.getEntity().getComponent(MovementComponent.class).getLastValidPosition();
			SceneMemento sceneMemento = new SceneMemento(this.currentScene.getName(), lastValidPosition);
			this.lastScenePosition.push(sceneMemento);
		}

		this.currentScene = new Scene(sceneName);

		Vector2i playerSpawn = this.currentScene.getSpawn();
		GameApplication.player.getEntity().getTransform().setPosition(new Vector2f(playerSpawn.x * Tile.TILE_SIZE, playerSpawn.y * Tile.TILE_SIZE));
	}

	public void returnToLastScene() {
		if(this.lastScenePosition.isEmpty()) { return; }

		SceneMemento memento = this.lastScenePosition.pop();
		this.switchScene(memento.getSceneName());
		GameApplication.player.getEntity().getTransform().setPosition(memento.getPosition());
	}

  public void updateScene() {
    for (Entity entity : this.currentScene.getEntities()) {
      entity.update();
    }
  }

	public Scene getCurrentScene() {
		return this.currentScene;
	}
}
