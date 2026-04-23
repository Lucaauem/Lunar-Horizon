package engine.scenes;

import engine.objects.entities.Entity;
import engine.objects.Tile;
import engine.objects.entities.EntityBuilder;
import engine.rendering.Tilemap;
import engine.objects.trigger.Trigger;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.json.JSONArray;
import org.json.JSONObject;
import util.FileHandler;
import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";

	private final Tilemap tilemap = new Tilemap();
	private final Tile[][] tiles;
	private final Entity[] entities;
	private final Vector2i spawn = new Vector2i();
	private final String name;
  private final Vector2i sceneSize;

	public Scene(String name) {
		this.name = name;
    this.sceneSize = new Vector2i(0, 0);

		JSONObject sceneData = FileHandler.readJSON(SCENE_BASE_PATH + this.name + ".json");

		this.loadConfig(sceneData.getJSONObject("config"));
		this.tiles = this.loadTiles(sceneData.getJSONObject("tiles"));
		this.loadTrigger(sceneData.getJSONArray("trigger"));
		this.entities = this.loadEntities(sceneData.getJSONArray("entities"));
	}

	// region CREATE SCENE

	private Tile[][] loadTiles(JSONObject tileData) {
		JSONArray tileIndices = tileData.getJSONArray("indices");
		JSONArray solidTiles = tileData.getJSONArray("solid");

    this.sceneSize.x = tileIndices.getJSONArray(0).length();
    this.sceneSize.y = tileIndices.length();

		Tile[][] tiles = new Tile[tileIndices.length()][tileIndices.getJSONArray(0).length()];

    for (int j=0; j<tileIndices.length(); j++) {
      JSONArray row = tileIndices.getJSONArray(j);
      JSONArray solidsRow = solidTiles.getJSONArray(j);
      for (int i=0; i<row.length(); i++) {
        tiles[j][i] = (this.generateTile(row.getInt(i), solidsRow.getInt(i) == 1, new Vector2f(i, this.sceneSize.y - j)));
      }
    }

		return tiles;
	}

	// TODO: Add tile modification (mirroring, rotation, ...)
	public Tile generateTile(int index, boolean solid, Vector2f position) {
		return new Tile(this.tilemap, index, position, solid);
	}

	private void loadConfig(JSONObject config) {
		JSONArray spawn = config.getJSONArray("spawn");

		this.spawn.x = spawn.getInt(0);
		this.spawn.y = spawn.getInt(1);
	}

	private void loadTrigger(JSONArray triggerArray) {
		for (int i=0; i<triggerArray.length(); i++) {
			JSONObject triggerData = triggerArray.getJSONObject(i);
			int xIndex = triggerData.getJSONArray("pos").getInt(0);
			int yIndex = triggerData.getJSONArray("pos").getInt(1);
			this.createTrigger(new Vector2i(xIndex, this.sceneSize.y - yIndex - 1), triggerData);
		}
	}

	private void createTrigger(Vector2i triggerPos, JSONObject triggerData) {
    String triggerId = triggerData.getString("type");
    Class<? extends Trigger> triggerClass = SceneManager.getInstance().triggerClasses.getOrDefault(triggerId, null);

		if(triggerClass != null) {
      try {
        Trigger trigger = triggerClass.getDeclaredConstructor().newInstance();
        trigger.setParameters(triggerData.getJSONObject("parameters"));
        this.tiles[triggerPos.y][triggerPos.x].setTrigger(trigger);
      } catch (Exception ignored) {
        System.err.println("Trigger \"" + triggerId + "\" could not be spawned!");
      }
		}
	}

	private Entity[] loadEntities(JSONArray entityArray) {
		Entity[] entities = new Entity[entityArray.length()];

		for(int i=0; i<entityArray.length(); i++) {
			JSONObject entityData = entityArray.getJSONObject(i);
			Vector2f position = new Vector2f(entityData.getJSONArray("pos").getInt(0), entityData.getJSONArray("pos").getInt(1));

      entities[i] = new EntityBuilder().create(
          entityData.getString("type"), entityData.getString("texture"), position.mul(DEFAULT_TILE_SIZE), entityData.getJSONObject("parameters")
      );
		}

		return entities;
	}

	// endregion

	public Vector2i getSpawn() {
		return this.spawn;
	}

	public Tile getTile(float x, float y) {
		int tileIndexX = (Math.round(x) / Tile.TILE_SIZE);
		int tileIndexY = this.sceneSize.y - (Math.round(y) / Tile.TILE_SIZE);

    return this.tiles[tileIndexY][tileIndexX];
	}

	public Entity[] getEntities() {
		return this.entities;
	}

	public String getName() {
		return this.name;
	}

	public boolean isInScene(float xPos, float yPos) {
		int tileIndexX = (Math.round(xPos) / Tile.TILE_SIZE);
		int tileIndexY = (Math.round(yPos) / Tile.TILE_SIZE) - 1;

		return (tileIndexX >= 0 && tileIndexX < this.sceneSize.x) && (tileIndexY >= 0 && tileIndexY < this.sceneSize.y);
	}

	public void render() {
    for (Tile[] row : this.tiles) {
      for (Tile tile : row) {
        if (tile != null) {
          tile.render();
        }
      }
    }

		for(Entity entity : this.entities) {
			if(entity != null) {
				entity.render();
			}
		}
	}
}
