package engine.graphics.tiles;

import engine.Game;
import engine.GameWindow;
import engine.graphics.Model;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static engine.objects.GameObject.DEFAULT_TILE_SIZE;

public class Tile {
	private final Tilemap tilemap;
	private final Model model;
	private final Vector2f position;

	public Tile(Tilemap tilemap, int textureIndex, Vector2f position) {
		this.position = position;
		this.tilemap = tilemap;
		Vector4f uv = tilemap.getUv(textureIndex);

		this.model = new Model(new float[] {
				0.0f,              0.0f,              uv.x, uv.w,
				DEFAULT_TILE_SIZE, 0.0f,              uv.z, uv.w,
				DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, uv.z, uv.y,
				0.0f,              DEFAULT_TILE_SIZE, uv.x, uv.y
		});
	}

	public void render() {
		this.tilemap.getTexture().bind();

		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
		Matrix4f view = Game.camera.getMatrix();
		Matrix4f model = new Matrix4f().identity();
		model.translate((this.position.x * DEFAULT_TILE_SIZE) + DEFAULT_TILE_SIZE, (this.position.y * DEFAULT_TILE_SIZE) + DEFAULT_TILE_SIZE, 0);

		Matrix4f mvp = proj.mul(view).mul(model);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}
}
