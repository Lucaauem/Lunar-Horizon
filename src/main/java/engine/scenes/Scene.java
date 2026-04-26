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
import java.awt.*;
import java.util.ArrayList;

import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";
  private static final String SUBAREA_DIR = "/sub/";

	private final Tilemap tilemap = new Tilemap();
	private final Vector2i spawn = new Vector2i(0, 0);
	private final String name;
  private final Vector2i sceneSize;
	private final ArrayList<Entity> entities;
  private boolean hasEnemies = false;
	private Tile[][] tiles = new Tile[0][0];

	public Scene(String name) {
		this.name = name;
    this.sceneSize = new Vector2i(0, 0);
    this.entities = new ArrayList<>();

		JSONObject sceneData = FileHandler.readJSON(SCENE_BASE_PATH + this.name + ".json");
    JSONObject sceneConfig = sceneData.getJSONObject("config");
    boolean usesAreas = sceneConfig.has("useAreas") && sceneConfig.getBoolean("useAreas");

    this.loadConfig(sceneConfig);

    ArrayList<JSONObject> entityData = new ArrayList<>();
    ArrayList<JSONObject> triggerData = new ArrayList<>();
    JSONObject[][] areas;

    if (usesAreas) {
      areas = this.loadAreas(sceneData.getJSONObject("tiles").getJSONArray("indices"));
    } else {
      areas = new JSONObject[][] {{ sceneData }};
      JSONArray areaTiles = sceneData.getJSONObject("tiles").getJSONArray("indices");
      this.sceneSize.set(areaTiles.getJSONArray(0).length(), areaTiles.length());
    }

    this.createScene(entityData, triggerData, areas);
    this.loadEntities(entityData.toArray(new JSONObject[0]));
    this.loadTrigger(triggerData.toArray(new JSONObject[0]));
	}

  // region SCENE CREATION

  private JSONObject[][] loadAreas(JSONArray areaIds) {
    JSONObject[][] areas = new JSONObject[areaIds.length()][areaIds.getJSONArray(0).length()];

    for (int i=0; i<areaIds.length(); i++) {
      JSONArray row = areaIds.getJSONArray(i);
      int rowWidth = 0;
      int rowHeight = 0;
      for (int j=0; j<row.length(); j++) {
        String areaFilePath = SCENE_BASE_PATH + (this.name.split("/")[0]) + SUBAREA_DIR + row.getString(j) + ".json";
        areas[i][j] = FileHandler.readJSON(areaFilePath);

        JSONArray areaTiles = areas[i][j].getJSONObject("tiles").getJSONArray("indices");
        rowHeight = areaTiles.length();
        rowWidth += areaTiles.getJSONArray(0).length();
      }
      this.sceneSize.add(rowWidth, rowHeight);
    }

    return areas;
  }

  private void createScene(ArrayList<JSONObject> entities, ArrayList<JSONObject> trigger, JSONObject[][] areas) {
    ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    Vector2i offset = new Vector2i(0, 0);

    for (JSONObject[] jsonObjects : areas) {
      offset.x = 0;
      int tmpOffsetY = 0;
      for (JSONObject area : jsonObjects) {
        JSONArray areaEntities = area.getJSONArray("entities");
        JSONArray areaTrigger = area.getJSONArray("trigger");

        Tile[][] areaTiles = this.loadTiles(area.getJSONObject("tiles"), offset);
        this.addAreaTilesToScene(tiles, areaTiles, offset);

        addSceneObjects(entities, offset, areaEntities, areaTiles.length);
        addSceneObjects(trigger, offset, areaTrigger, areaTiles.length);

        offset.x += areaTiles[0].length;
        tmpOffsetY = Math.max(tmpOffsetY, areaTiles.length);
      }
      offset.y += tmpOffsetY;
    }

    Tile[][] result = new Tile[tiles.size()][];
    for (int i = 0; i < tiles.size(); i++) {
      result[i] = tiles.get(i).toArray(new Tile[0]);
    }
    this.tiles =  result;
  }

  private void addSceneObjects(ArrayList<JSONObject> objects, Vector2i offset, JSONArray areaObjects, int areaHeight) {
    for(Object object : areaObjects) {
      JSONObject obj = (JSONObject) object;
      JSONArray pos = obj.getJSONArray("pos");

      JSONArray offsetPos = new JSONArray();
      offsetPos.put(pos.getInt(0) + offset.x);
      offsetPos.put(this.sceneSize.y - (areaHeight - pos.getInt(1) + offset.y));

      obj.put("pos", offsetPos);
      objects.add(obj);
    }
  }

  private void addAreaTilesToScene(ArrayList<ArrayList<Tile>> tiles, Tile[][] areaTiles, Vector2i offset) {
    for (int i=0; i<areaTiles.length; i++) {
      if (i + offset.y >= tiles.size()) {
        tiles.add(new ArrayList<>());
      }

      for (int j=0; j<areaTiles[i].length; j++) {
        tiles.get(i + offset.y).add(areaTiles[i][j]);
      }
    }
  }

  private Tile[][] loadTiles(JSONObject tileData, Vector2i offset) {
    JSONArray tileIndices = tileData.getJSONArray("indices");
    JSONArray solidTiles = tileData.getJSONArray("solid");
    Tile[][] tiles = new Tile[tileIndices.length()][tileIndices.getJSONArray(0).length()];

    for (int j=0; j<tileIndices.length(); j++) {
      JSONArray row = tileIndices.getJSONArray(j);
      JSONArray solidsRow = solidTiles.getJSONArray(j);
      for (int i=0; i<row.length(); i++) {
        Vector2f tilePosition = new Vector2f(i + offset.x, this.sceneSize.y - j - offset.y);
        tiles[j][i] = this.generateTile(row.getInt(i), solidsRow.getInt(i) == 1, tilePosition);
      }
    }

    return tiles;
  }

	// TODO: Add tile modification (mirroring, rotation, ...)
	public Tile generateTile(int index, boolean solid, Vector2f position) {
		return new Tile(this.tilemap, index, position, solid);
	}

	private void loadConfig(JSONObject config) {
    if (config.has("spawn")) {
      JSONArray spawn = config.getJSONArray("spawn");
      this.spawn.x = spawn.getInt(0);
      this.spawn.y = spawn.getInt(1);
    }

    this.hasEnemies = config.has("enemies");
	}

	private void loadTrigger(JSONObject[] triggerArray) {
		for (JSONObject triggerData : triggerArray) {
			int xIndex = triggerData.getJSONArray("pos").getInt(0);
			int yIndex = triggerData.getJSONArray("pos").getInt(1);
			this.createTrigger(new Vector2i(xIndex, this.sceneSize.y - yIndex - 1), triggerData);
		}
	}

	private void createTrigger(Vector2i triggerPos, JSONObject triggerData) {
    String triggerId = triggerData.getString("type");
    Class<? extends Trigger> triggerClass = SceneManager.getInstance().triggerClasses.getOrDefault(triggerId, null);

		if(triggerClass == null) {
      return;
		}

    try {
      Trigger trigger = triggerClass.getDeclaredConstructor().newInstance();
      trigger.setParameters(triggerData.getJSONObject("parameters"));
      this.tiles[triggerPos.y][triggerPos.x].setTrigger(trigger);
    } catch (Exception ignored) {
      System.err.println("Trigger \"" + triggerId + "\" could not be spawned!");
    }
	}

	private void loadEntities(JSONObject[] entityArray) {
    for (JSONObject entityData : entityArray) {
      Vector2f position = new Vector2f(entityData.getJSONArray("pos").getInt(0), entityData.getJSONArray("pos").getInt(1));
      position.add(0, 1);

      this.entities.add(new EntityBuilder().create(
              entityData.getString("type"), entityData.getString("texture"), position.mul(DEFAULT_TILE_SIZE), entityData.getJSONObject("parameters")
      ));
    }
	}

  // endregion

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

  // region GETTER AND SETTER

  public Vector2i getSpawn() {
    return this.spawn;
  }

  public Tile getTile(float x, float y) {
    int tileIndexX = (Math.round(x) / Tile.TILE_SIZE);
    int tileIndexY = this.sceneSize.y - (Math.round(y) / Tile.TILE_SIZE);
    return this.tiles[tileIndexY][tileIndexX];
  }

  public Entity[] getEntities() {
    return this.entities.toArray(new Entity[0]);
  }

  public String getName() {
    return this.name;
  }

  public boolean canSpawnEnemies() {
    return this.hasEnemies;
  }

  // endregion
}
