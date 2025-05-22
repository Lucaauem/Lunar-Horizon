package engine.scenes;

import engine.objects.Entity;
import engine.objects.EntityBuilder;
import engine.objects.Tile;
import engine.graphics.Tilemap;
import engine.objects.trigger.SceneChangeTrigger;
import engine.objects.trigger.TargetScene;
import engine.objects.trigger.Trigger;
import org.joml.Vector2f;
import org.joml.Vector2i;
import util.FileHandler;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";
	private static final String TILES_EXTENSION = "tiles.bin";
	private static final String TRIGGER_EXTENSION = "trigger.bin";
	private static final String CONFIG_EXTENSION = "conf.bin";
	private static final String ENTITY_EXTENSION = "entities.bin";
	private static final int SCENE_SIZE = 8;

	private final Tilemap tilemap = new Tilemap();
	private final Tile[] tiles;
	private final Entity[] entities;
	private final Vector2i spawn = new Vector2i();
	private final String name;

	public Scene(String name) {
		this.name = name + "/";

		this.tiles = this.loadTiles();
		this.loadConfig();
		this.loadTrigger();
		this.entities = this.loadEntities();
	}

	// region GENERATE TILES

	// Tiles consist of 2 bytes.
	// The first byte is the index in the tilemap (texture).
	// The second byte modifies the tiles (mirroring, palette, collidable, ...).
	private Tile[] loadTiles() {
		byte[] rawTileData = FileHandler.readBinary(SCENE_BASE_PATH + this.name + TILES_EXTENSION);
		Tile[] tiles = new Tile[SCENE_SIZE * SCENE_SIZE];

		Vector2f tilePosition = new Vector2f(0, SCENE_SIZE);
		int tileIndex = 0;
		for(int i=0; i<rawTileData.length; i+=2) {
			tiles[tileIndex++] = (this.generateTile(rawTileData[i], rawTileData[i + 1], new Vector2f(tilePosition)));

			if(++tilePosition.x >= SCENE_SIZE) {
				tilePosition.x = 0;
				tilePosition.y--;
			}
		}

		return tiles;
	}

	// Modifier byte:
	// 76543210
	// ||||||||
	// |||||||+- 1st bit for the number of rotations
	// ||||||+-- 2nd bit for the number of rotations
	// |||||+---
	// ||||+----
	// |||+-----
	// |+-------
	// +-------- Can be walked on/through
	public Tile generateTile(byte index, byte modifier, Vector2f position) {
		Tile tile = new Tile(this.tilemap, index, position, (modifier & 0b10000000) != 0);
		tile.rotate90Deg(modifier & 0b00000011);

		return tile;
	}

	private void loadConfig() {
		byte[] rawConfigData = FileHandler.readBinary(SCENE_BASE_PATH + this.name + CONFIG_EXTENSION);

		for(int i=0; i<rawConfigData.length; i+=3) {
			this.readConfig(rawConfigData[i], rawConfigData[i + 1], rawConfigData[i + 2]);
		}
	}

	private void readConfig(byte type, byte param1, byte param2) {
		if(type == 0) {
			this.spawn.x = param1;
			this.spawn.y = param2;
		}
	}

	// Tiles consist of 4 bytes.
	// The first byte is the x-index of the tile.
	// The second byte is the y-index of the tile.
	// The third byte is the type of trigger.
	// The fourth byte is parameter data for the trigger.
	private void loadTrigger() {
		byte[] rawTriggerData = FileHandler.readBinary(SCENE_BASE_PATH + this.name + TRIGGER_EXTENSION);

		for(int i=0; i<rawTriggerData.length; i+=4) {
			this.createTrigger(rawTriggerData[i], rawTriggerData[i + 1], rawTriggerData[i + 2], rawTriggerData[i + 3]);
		}
	}

	private void createTrigger(int xIndex, int yIndex, int type, int parameter) {
		Trigger trigger = null;

		if(type == 1) {
			trigger = new SceneChangeTrigger();
			trigger.setParameter(TargetScene.values()[parameter].name());
		}

		if(trigger != null) {
			this.tiles[yIndex * SCENE_SIZE + xIndex].setTrigger(trigger);
		}
	}

	// endregion

	private Entity[] loadEntities() {
		byte[] rawEntityData = FileHandler.readBinary(SCENE_BASE_PATH + this.name + ENTITY_EXTENSION);
		Entity[] entities = new Entity[rawEntityData.length / 4];

		int entityIndex = 0;
		for(int i=0; i<rawEntityData.length; i+=4) {
			int type = rawEntityData[i];
			int parameter = rawEntityData[i + 1];
			Vector2f position = new Vector2f(rawEntityData[i + 2], rawEntityData[i + 3]);
			entities[entityIndex++] = new EntityBuilder().buildEntity(type, parameter, position);
		}

		return entities;
	}

	public Vector2i getSpawn() {
		return this.spawn;
	}

	public Tile getTile(float x, float y) {
		int tileIndexX = (int) (x / Tile.TILE_SIZE);
		int tileIndexY = SCENE_SIZE - (int) (y / Tile.TILE_SIZE);

		return this.tiles[tileIndexY * SCENE_SIZE + tileIndexX];
	}

	public String getName() {
		return this.name;
	}

	public boolean isInScene(float xPos, float yPos) {
		int tileIndexX = (int) (xPos / Tile.TILE_SIZE);
		int tileIndexY = (int) (yPos / Tile.TILE_SIZE) - 1;

		return (tileIndexX >= 0 && tileIndexX < SCENE_SIZE) && (tileIndexY >= 0 && tileIndexY < SCENE_SIZE);
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
