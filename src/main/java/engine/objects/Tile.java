package engine.objects;

import engine.Game;
import engine.GameWindow;
import engine.graphics.Model;
import engine.graphics.Tilemap;
import engine.graphics.renderer.Renderer;
import engine.objects.trigger.Trigger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Tile {
	public static final int TILE_SIZE = 16;

	private final Tilemap tilemap;
	private final Vector2f position;
	private final boolean isSolid;
	private Trigger trigger = null;
	private Vector2f[] uv;
	private Model model;

	public Tile(Tilemap tilemap, int textureIndex, Vector2f position, boolean solid) {
		this.position = position;
		this.tilemap = tilemap;
		this.isSolid = solid;

		Vector4f uv = tilemap.getUv(textureIndex);
		this.uv = new Vector2f[]{
				new Vector2f(uv.x, uv.w),
				new Vector2f(uv.z, uv.w),
				new Vector2f(uv.z, uv.y),
				new Vector2f(uv.x, uv.y),
		};

		this.updateModel();
	}

	private void updateModel() {
		this.model = new Model(new float[] {
				0.0f,      0.0f,      this.uv[0].x, this.uv[0].y,
				TILE_SIZE, 0.0f,      this.uv[1].x, this.uv[1].y,
				TILE_SIZE, TILE_SIZE, this.uv[2].x, this.uv[2].y,
				0.0f,      TILE_SIZE, this.uv[3].x, this.uv[3].y,
		});
	}

	public void activateTrigger() {
		this.trigger.trigger();
	}

	public boolean hasTrigger() {
		return this.trigger != null;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	// is rotated clockwise
	public void rotate90Deg(int times) {
		int normalizedTimes = ((times % 4) + 4) % 4;

		for(int i=0; i<normalizedTimes; i++) {
			this.uv = new Vector2f[]{
					new Vector2f(this.uv[1].x, this.uv[1].y),
					new Vector2f(this.uv[2].x, this.uv[2].y),
					new Vector2f(this.uv[3].x, this.uv[3].y),
					new Vector2f(this.uv[0].x, this.uv[0].y),
			};
		}
		this.updateModel();
	}

	public boolean isSolid() {
		return this.isSolid;
	}

	public void render() {
		this.tilemap.getTexture().bind();

		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
		Matrix4f view = Game.camera.getMatrix();
		Matrix4f model = new Matrix4f().identity();
		model.translate((this.position.x * TILE_SIZE) + TILE_SIZE, (this.position.y * TILE_SIZE) + TILE_SIZE, 0);

		Matrix4f mvp = proj.mul(view).mul(model);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}
}
