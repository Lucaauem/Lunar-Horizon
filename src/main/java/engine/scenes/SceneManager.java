package engine.scenes;

import engine.events.EventManager;
import engine.objects.Tile;
import engine.objects.entities.Entity;
import engine.objects.trigger.SceneChangeTrigger;
import engine.objects.trigger.Trigger;
import game.GameApplication;
import org.joml.Vector2f;
import org.joml.Vector2i;
import java.util.HashMap;

public class SceneManager {
	private static final SceneManager instance = new SceneManager();

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
    this.switchScene(sceneName, null);
  }

	public void switchScene(String sceneName, Vector2f newScenePosition) {
		this.currentScene = new Scene(sceneName);

    Vector2f newPosition;
    if (newScenePosition == null) {
		  Vector2i playerSpawn = this.currentScene.getSpawn();
      newPosition = new Vector2f(playerSpawn.x * Tile.TILE_SIZE, playerSpawn.y * Tile.TILE_SIZE);
    } else {
      newPosition = new Vector2f(newScenePosition.x * Tile.TILE_SIZE, newScenePosition.y * Tile.TILE_SIZE);
    }

    GameApplication.player.getEntity().getTransform().setPosition(newPosition);
    EventManager.getInstance().publish("SCENE_CHANGE");
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
