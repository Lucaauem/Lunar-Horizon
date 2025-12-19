package de.luca.lunarhorizon.engine.objects;

import de.luca.lunarhorizon.engine.Game;
import de.luca.lunarhorizon.engine.GameWindow;
import de.luca.lunarhorizon.engine.graphics.Model;
import de.luca.lunarhorizon.engine.graphics.Texture;
import de.luca.lunarhorizon.engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class GameObject {
	public static final int DEFAULT_TILE_SIZE = 16;

	protected Model model;
	protected Texture texture;
	protected final Vector2f position = new Vector2f();
	protected Hitbox hitbox;
	private final boolean isSolid;

	public GameObject(String texturePath, Vector2f position, boolean isSolid) {
		if(position != null) {
			this.setPosition(position);
		}

		this.texture = new Texture(texturePath);
		this.isSolid = isSolid;
		this.model = new Model(new float[] {
				0.0f,              0.0f,              0, 1, // 0
				DEFAULT_TILE_SIZE, 0.0f,              1, 1, // 1
				DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, 1, 0, // 2
				0.0f,              DEFAULT_TILE_SIZE, 0, 0  // 3
		});
	}

	public Vector2f getPosition() {
		return new Vector2f(this.position.x, this.position.y);
	}

	public boolean isColliding(GameObject other) {
		return this.hitbox.isColliding(other.hitbox);
	}

	public boolean isSolid() {
		return this.isSolid;
	}

	public void setPosition(Vector2f position) {
		this.position.set(position);
		this.hitbox = new Hitbox(position.x, position.y, position.x + DEFAULT_TILE_SIZE, position.y + DEFAULT_TILE_SIZE);
	}

	public void render(){
		this.texture.bind();

		// Render model
		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
		Matrix4f view = Game.camera.getMatrix();
		Matrix4f model = new Matrix4f().identity();
		model.translate(this.position.x + DEFAULT_TILE_SIZE, this.position.y + DEFAULT_TILE_SIZE, 0);

		Matrix4f mvp = proj.mul(view).mul(model);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}
}
