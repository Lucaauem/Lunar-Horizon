package engine.scenes;

import engine.objects.Entity;
import engine.objects.EntityBuilder;
import engine.objects.Tile;
import engine.graphics.Tilemap;
import engine.objects.trigger.SceneChangeTrigger;
import engine.objects.trigger.Trigger;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.json.JSONArray;
import org.json.JSONObject;
import util.FileHandler;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";

	private final Tilemap tilemap = new Tilemap();
	private final Tile[] tiles;
	private final Entity[] entities;
	private final Vector2i spawn = new Vector2i();
	private final String name;

	private int sceneSize = 0;

	public Scene(String name) {
		this.name = name + "/";

		JSONObject sceneData = FileHandler.readJSON(SCENE_BASE_PATH + this.name + "scene.json");

		this.loadConfig(sceneData.getJSONObject("config"));
		this.tiles = this.loadTiles(sceneData.getJSONObject("tiles"));
		this.loadTrigger(sceneData.getJSONArray("trigger"));
		this.entities = this.loadEntities(sceneData.getJSONArray("entities"));
	}

	// region CREATE SCENE

	private Tile[] loadTiles(JSONObject tileData) {
		JSONArray tileIndices = tileData.getJSONArray("indices");
		JSONArray solidTiles = tileData.getJSONArray("solid");
		Tile[] tiles = new Tile[this.sceneSize * this.sceneSize];

		Vector2f tilePosition = new Vector2f(0, this.sceneSize);
		for(int i=0; i<tileIndices.length(); i++) {
			tiles[i] = (this.generateTile(tileIndices.getInt(i), solidTiles.getInt(i) == 1, new Vector2f(tilePosition)));

			if(++tilePosition.x >= this.sceneSize) {
				tilePosition.x = 0;
				tilePosition.y--;
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

		this.sceneSize = config.getInt("size");

		this.spawn.x = spawn.getInt(0);
		this.spawn.y = spawn.getInt(1);
	}

	private void loadTrigger(JSONArray triggerArray) {
		for (int i=0; i<triggerArray.length(); i++) {
			JSONObject triggerData = triggerArray.getJSONObject(i);
			int xIndex = triggerData.getJSONArray("pos").getInt(0);
			int yIndex = triggerData.getJSONArray("pos").getInt(1);
			this.createTrigger(new Vector2i(xIndex, yIndex), triggerData);
		}
	}

	private void createTrigger(Vector2i triggerPos, JSONObject triggerData) {
		Trigger trigger = switch (triggerData.getString("type")) {
			case "SCENE_CHANGE" -> new SceneChangeTrigger();
			default -> null;
		};

		if(trigger != null) {
			trigger.setParameter(triggerData.getString("parameter"));
			this.tiles[(this.sceneSize - triggerPos.y) * this.sceneSize + triggerPos.x].setTrigger(trigger);
		}
	}

	private Entity[] loadEntities(JSONArray entityArray) {
		Entity[] entities = new Entity[entityArray.length()];

		for(int i=0; i<entityArray.length(); i++) {
			JSONObject entityData = entityArray.getJSONObject(i);
			Vector2f position = new Vector2f(entityData.getJSONArray("pos").getInt(0), entityData.getJSONArray("pos").getInt(1));

			entities[i] = new EntityBuilder().buildEntity(
					entityData.getString("type"), entityData.getString("texture"),
					entityData.getString("parameter"), position
			);
		}

		return entities;
	}

	// endregion

	public Vector2i getSpawn() {
		return this.spawn;
	}

	public Tile getTile(float x, float y) {
		int tileIndexX = (int) (x / Tile.TILE_SIZE);
		int tileIndexY = this.sceneSize - (int) (y / Tile.TILE_SIZE);

		return this.tiles[tileIndexY * this.sceneSize + tileIndexX];
	}

	public Entity[] getEntities() {
		return this.entities;
	}

	public String getName() {
		return this.name;
	}

	public boolean isInScene(float xPos, float yPos) {
		int tileIndexX = (int) (xPos / Tile.TILE_SIZE);
		int tileIndexY = (int) (yPos / Tile.TILE_SIZE) - 1;

		return (tileIndexX >= 0 && tileIndexX < this.sceneSize) && (tileIndexY >= 0 && tileIndexY < this.sceneSize);
	}

	public void render() {
		for(Tile tile : this.tiles) {
			if(tile != null) {
				tile.render();
			}
		}

		for(Entity entity : this.entities) {
			if(entity != null) {
				entity.render();
			}
		}
	}
}
