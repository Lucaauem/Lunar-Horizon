package engine.scenes;

import engine.graphics.tiles.Tile;
import engine.graphics.tiles.Tilemap;
import util.FileHandler;
import java.util.ArrayList;

public class Scene {
	private static final String SCENE_BASE_PATH = "src/main/assets/scenes/";
	private static final String SCENE_EXTENSION = ".scene.bin";

	private final Tilemap tilemap = new Tilemap();
	private final Tile[] tiles;

	public Scene(String name) {
		byte[] rawData = FileHandler.readBinary(SCENE_BASE_PATH + name + SCENE_EXTENSION);
		ArrayList<Tile> tiles = new ArrayList<>();

		for(byte b : rawData) {
			tiles.add(this.generateTile(b));
		}

		this.tiles = tiles.toArray(new Tile[0]);
	}

	public Tile generateTile(byte data) {
		return new Tile(this.tilemap, 0);
	}

	public void render() {
		for(Tile tile : this.tiles) {
			if(tile != null) {
				tile.render();
			}
		}
	}
}
