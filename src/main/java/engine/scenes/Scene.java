package engine.scenes;

import engine.graphics.tiles.Tile;
import engine.graphics.tiles.Tilemap;
import org.joml.Vector2f;
import util.FileHandler;
import java.util.ArrayList;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";
	private static final String SCENE_EXTENSION = ".scene.bin";
	private static final int SCENE_SIZE = 8;

	private final Tilemap tilemap = new Tilemap();
	private final Tile[] tiles;

	public Scene(String name) {
		byte[] rawData = FileHandler.readBinary(SCENE_BASE_PATH + name + SCENE_EXTENSION);
		ArrayList<Tile> tiles = new ArrayList<>();

		Vector2f tilePosition = new Vector2f(0, SCENE_SIZE);

		// Tiles consist of 3 bytes.
		// The first byte is the index in the tilemap (texture).
		// The second byte modifies the visuals (mirroring, palette, ...).
		// The third byte contains stauts information (e.g. collision, special behaviour, ...).
		for(int i=0; i<rawData.length; i+=3) {
			tiles.add(this.generateTile(rawData[i], rawData[i + 1], rawData[i + 2], new Vector2f(tilePosition.x, tilePosition.y)));

			if(++tilePosition.x >= SCENE_SIZE) {
				tilePosition.x = 0;
				tilePosition.y--;
			}
		}

		this.tiles = tiles.toArray(new Tile[0]);
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
