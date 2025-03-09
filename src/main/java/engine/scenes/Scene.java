package engine.scenes;

import engine.objects.Tile;
import engine.graphics.Tilemap;
import engine.objects.trigger.SceneChangeTrigger;
import engine.objects.trigger.TargetScene;
import engine.objects.trigger.Trigger;
import org.joml.Vector2f;
import org.joml.Vector2i;
import util.FileHandler;
import java.util.ArrayList;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";
	private static final String SCENE_EXTENSION = ".scene.bin";
	private static final String TRIGGER_EXTENSION = ".trigger.bin";
	private static final String CONFIG_EXTENSION = ".conf.bin";
	private static final int SCENE_SIZE = 8;

	private final Tilemap tilemap = new Tilemap();
	private final Tile[] tiles;
	private final Vector2i spawn = new Vector2i();

	public Scene(String name) {
		byte[] rawTileData = FileHandler.readBinary(SCENE_BASE_PATH + name + SCENE_EXTENSION);
		ArrayList<Tile> tiles = new ArrayList<>();
		Vector2f tilePosition = new Vector2f(0, SCENE_SIZE);

		// Tiles consist of 3 bytes.
		// The first byte is the index in the tilemap (texture).
		// The second byte modifies the visuals (mirroring, palette, ...).
		// The third byte contains stauts information (e.g. collision, special behaviour, ...).
		for(int i=0; i<rawTileData.length; i+=3) {
			tiles.add(this.generateTile(rawTileData[i], rawTileData[i + 1], rawTileData[i + 2], new Vector2f(tilePosition.x, tilePosition.y)));

			if(++tilePosition.x >= SCENE_SIZE) {
				tilePosition.x = 0;
				tilePosition.y--;
			}
		}

		this.tiles = tiles.toArray(new Tile[0]);

		byte[] rawConfigData = FileHandler.readBinary(SCENE_BASE_PATH + name + CONFIG_EXTENSION);
		for(int i=0; i<rawConfigData.length; i+=3) {
			this.loadConfig(rawConfigData[i], rawConfigData[i + 1], rawConfigData[i + 2]);
		}

		byte[] rawTriggerData = FileHandler.readBinary(SCENE_BASE_PATH + name + TRIGGER_EXTENSION);

		// Tiles consist of 4 bytes.
		// The first byte is the x-index of the tile.
		// The second byte is the y-index of the tile.
		// The third byte is the type of trigger.
		// The fourth byte is parameter data for the trigger.
		for(int i=0; i<rawTriggerData.length; i+=4) {
			this.createTrigger(rawTriggerData[i], rawTriggerData[i + 1], rawTriggerData[i + 2], rawTriggerData[i + 3]);
		}
	}

	private void loadConfig(byte type, byte param1, byte param2) {
		if(type == 0) {
			this.spawn.x = param1;
			this.spawn.y = param2;
		}
	}

	private void createTrigger(int xIndex, int yIndex, int type, int parameter) {
		Trigger trigger = null;

		if(type == 1) {
			trigger = new SceneChangeTrigger();
			trigger.setParameter(TargetScene.values()[parameter].name().toLowerCase());
		}

		if(trigger != null) {
			this.tiles[yIndex * SCENE_SIZE + xIndex].setTrigger(trigger);
		}
	}

	public Vector2i getSpawn() {
		return this.spawn;
	}

	public Tile getTile(float x, float y) {
		int tileIndexX = (int) (x / Tile.TILE_SIZE);
		int tileIndexY = SCENE_SIZE - (int) (y / Tile.TILE_SIZE);

		return this.tiles[tileIndexY * SCENE_SIZE + tileIndexX];
	}

	public boolean isInScene(float xPos, float yPos) {
		int tileIndexX = (int) (xPos / Tile.TILE_SIZE);
		int tileIndexY = (int) (yPos / Tile.TILE_SIZE) - 1;

		return (tileIndexX >= 0 && tileIndexX < SCENE_SIZE) && (tileIndexY >= 0 && tileIndexY < SCENE_SIZE);
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
	// +--------

	// Status byte:
	// 76543210
	// ||||||||
	// |||||||+- Can be walked on/through
	// ||||||+--
	// |||||+---
	// ||||+----
	// |||+-----
	// |+-------
	// +--------
	public Tile generateTile(byte index, byte modifier, byte status, Vector2f position) {
		Tile tile = new Tile(this.tilemap, index, position, (status & 0b00000001) != 0);
		tile.rotate90Deg(modifier & 0b00000011);
		return tile;
	}

	public void render() {
		for(Tile tile : this.tiles) {
			if(tile != null) {
				tile.render();
			}
		}
	}
}
