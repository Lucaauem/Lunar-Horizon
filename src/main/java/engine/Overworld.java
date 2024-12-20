package engine;

import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import engine.objects.GameObject;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Overworld{
	private static Overworld instance;
	private static final Vector2f dimensions = new Vector2f(32, 32);

	private final Texture texture = new Texture("debug/debug_map.png");
	private final Model model;

	private Overworld() {
		float width = GameObject.DEFAULT_TILE_SIZE * dimensions.x;
		float height = GameObject.DEFAULT_TILE_SIZE * dimensions.y;
		this.model = new Model(new float[] {
				0.0f,  0.0f,   0, 1,
				width, 0.0f,   1, 1,
				width, height, 1, 0,
				0.0f,  height, 0, 0
		});
	}

	public static Overworld getInstance() {
		if(instance == null) {
			instance = new Overworld();
		}
		return instance;
	}

	public void render(){
		this.texture.bind();

		// Render model
		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
		Matrix4f view = Game.camera.getMatrix();

		Matrix4f mvp = proj.mul(view);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}
}
