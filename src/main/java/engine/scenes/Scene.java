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
	public Tile generateTile(byte index, byte modifier, byte status, Vector2f position) {
		if(index == 0) { return null; }

		Tile tile = new Tile(this.tilemap, index - 1, position);
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
