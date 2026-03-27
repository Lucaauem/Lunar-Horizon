package engine.ui;

import engine.Game;
import engine.GameWindow;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class UiElement {
	private final Texture texture;
	private final Vector2i position;
	private final Model model;
	private boolean visible = false;

	public UiElement(Texture texture, Vector2i position) {
		this.texture = texture;
		this.position = position;

		Vector2i size = new Vector2i(this.texture.getWidth(), this.texture.getHeight());

		this.model = new Model(new float[] {
				0.0f,   0.0f,   0, 1,
				size.x, 0.0f,   1, 1,
				size.x, size.y, 1, 0,
				0.0f,   size.y, 0, 0
		});
	}

	public UiElement(Texture texture, Vector2i size, Vector4f uv, Vector2i position) {
		this.texture = texture;
		this.position = position;

		this.model = new Model(new float[] {
				0.0f,   0.0f,   uv.x, uv.y,
				size.x, 0.0f,   uv.z, uv.y,
				size.x, size.y, uv.z, uv.w,
				0.0f,   size.y, uv.x, uv.w,
		});
	}

	public void toggle() {
		this.visible = !this.visible;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void render(){
		this.texture.bind();

		Matrix4f proj = new Matrix4f().ortho(0.0f, GameWindow.RESOLUTION.x, 0.0f, GameWindow.RESOLUTION.y, -1.0f, 1.0f);
		Matrix4f model = new Matrix4f().identity();
		model.translate(this.position.x, this.position.y, 0);

		Matrix4f mvp = proj.mul(model);
		Game.shader.setUniformMat4f("u_MVP", mvp);

		Renderer.getInstance().draw(this.model.getVertexArray(), this.model.getIndexBuffer(), Game.shader);
	}
}
