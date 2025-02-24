package engine.graphics.tiles;

import engine.graphics.Texture;
import org.joml.Vector4f;

public class Tilemap {
	private static final int TILE_SIZE = 8;

	private final Texture tilemap = new Texture("tiles.png");
	private final int dimensionHeight;
	private final int dimensionWidth;

	public Tilemap() {
		this.dimensionHeight = tilemap.getHeight() / TILE_SIZE;
		this.dimensionWidth = tilemap.getWidth() / TILE_SIZE;
	}

	public Texture getTexture() {
		return this.tilemap;
	}

	public Vector4f getUv(int index) {
		int col = index % Tilemap.this.dimensionWidth;
		int row = index / Tilemap.this.dimensionWidth;

		return new Vector4f(
			col * (1f / this.dimensionWidth),row * (1f / this.dimensionHeight),
			(col + 1) * (1f / this.dimensionWidth),(row + 1) * (1f / this.dimensionHeight)
		);
	}
}
