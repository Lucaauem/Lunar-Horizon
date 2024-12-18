package engine.objects;

import engine.Game;
import engine.GameWindow;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import util.Misc;

public class GameObject {
	public static final int DEFAULT_TILE_SIZE = 72;

	private int id = -1;
	protected Model model;
	protected Texture texture;
	protected final Vector2f position = new Vector2f();
	protected Hitbox hitbox;

	public GameObject(String texturePath, Vector2f position) {
		this.position.set(position);

		this.texture = new Texture(texturePath);
		this.model = new Model(new float[] {
				0.0f,              0.0f,              0, 1, // 0
				DEFAULT_TILE_SIZE, 0.0f,              1, 1, // 1
				DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, 1, 0, // 2
				0.0f,              DEFAULT_TILE_SIZE, 0, 0  // 3
		});
	}

	private void rotate90Degrees(boolean rotate) {
		int[] rotationComponents = {rotate ? 1 : 0, rotate ? 0 : 1};

		this.model = new Model(new float[] {
				0.0f,              0.0f,              rotationComponents[0], 1, // 0
				DEFAULT_TILE_SIZE, 0.0f,              1, rotationComponents[1], // 1
				DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, rotationComponents[1], 0, // 2
				0.0f,              DEFAULT_TILE_SIZE, 0, rotationComponents[0]  // 3
		});
	}

	protected void setFlip(boolean flipX, boolean flipY) {
		float[] textureUvs = this.model.getTextureUvs();

		Vector2f[] uvCoordinates= {
				new Vector2f(textureUvs[0], textureUvs[1]),
				new Vector2f(textureUvs[2], textureUvs[3]),
				new Vector2f(textureUvs[4], textureUvs[5]),
				new Vector2f(textureUvs[6], textureUvs[7])
		};

		if(flipX){
			Misc.arraySwap(uvCoordinates, 0, 1);
			Misc.arraySwap(uvCoordinates, 2, 3);
		}
		if(flipY){
			Misc.arraySwap(uvCoordinates, 0, 3);
			Misc.arraySwap(uvCoordinates, 2, 1);
		}

		float[] vertices = {
				0.0f,              0.0f,              uvCoordinates[0].x, uvCoordinates[0].y, // 0
				DEFAULT_TILE_SIZE, 0.0f,              uvCoordinates[1].x, uvCoordinates[1].y, // 1
				DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, uvCoordinates[2].x, uvCoordinates[2].y, // 2
				0.0f,              DEFAULT_TILE_SIZE, uvCoordinates[3].x, uvCoordinates[3].y  // 3
		};

		this.model = new Model(vertices);
	}

	public void modifyTexture(boolean flipX, boolean flipY, boolean rotate90Degrees) {
		this.rotate90Degrees(rotate90Degrees); // Needs to be called first because it "resets" the uv coords
		this.setFlip(flipX, flipY);
	}

	protected void changeTexture(String texturePath) {
		this.texture = new Texture(texturePath);
		this.texture.bind();
	}

	protected void updateHitbox() {
		this.hitbox = new Hitbox(this.position);
	}

	public Hitbox getHitbox() {
		return this.hitbox;
	}

	public Vector2f getPosition() {
		return new Vector2f(this.position.x, this.position.y);
	}

	public void setPosition(Vector2f position) {
		this.position.set(position);
		this.updateHitbox();
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void render(){
		this.texture.bind();

		// Render model
		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.WINDOW_WIDTH, 0.0f, GameWindow.WINDOW_HEIGHT, -1.0f, 1.0f);
		Matrix4f view = Game.camera.getMatrix();
		Matrix4f model = new Matrix4f().identity();

		Matrix4f mvp = proj.mul(view).mul(model);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
