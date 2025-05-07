package engine.battle;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;

public class BattleEngine {
	private static final Texture BACKGROUND_TEXTURE = new Texture("ui/battle/background/debug_background.png");
	private static final Texture STATUS_BOX_MODEL_TEXTURE = new Texture("ui/battle/status_box.png");
	private static final Texture GROUND_TEXTURE = new Texture("ui/battle/ground/debug.png");
	private static final Texture MENU_TEXTURE = new Texture("ui/battle/menu.png");
	private static final Model MENU_MODEL = new Model(new float[] {
			0.0f, 0.0f, 0, 1,
			1.0f, 0.0f, 1, 1,
			1.0f, 0.25f, 1, 0,
			0.0f, 0.25f, 0, 0
	});
	private static final Model BACKGROUND_MODEL = new Model(new float[] {
			0.0f, 0.25f, 0, 1,
			1.0f, 0.25f, 1, 1,
			1.0f, 1.0f,  1, 0,
			0.0f, 1.0f,  0, 0
	});
	private static final Model GROUND_MODEL = new Model(new float[] {
			0.0f, 0.25f, 0, 1,
			1.0f, 0.25f, 1, 1,
			1.0f, 0.375f, 1, 0,
			0.0f, 0.375f, 0, 0
	});
	private static final Model STATUX_BOX_MODEL = new Model(new float[] {
			0.025f, 0.805f, 0, 1,
			0.175f, 0.805f, 1, 1,
			0.175f, 0.975f, 1, 0,
			0.025f, 0.975f, 0, 0
	});

	private Enemy[] enemies;

	public BattleEngine() {

	}

	public void render() {
		Matrix4f proj = new Matrix4f().ortho(0.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		Game.shader.setUniformMat4f("u_MVP", proj);

		// Render background
		BACKGROUND_TEXTURE.bind();
		Renderer.getInstance().draw(BACKGROUND_MODEL.getVertexArray(), BACKGROUND_MODEL.getIndexBuffer(), Game.shader);
		GROUND_TEXTURE.bind();
		Renderer.getInstance().draw(GROUND_MODEL.getVertexArray(), GROUND_MODEL.getIndexBuffer(), Game.shader);
		MENU_TEXTURE.bind();
		Renderer.getInstance().draw(MENU_MODEL.getVertexArray(), MENU_MODEL.getIndexBuffer(), Game.shader);

		// Render ui elements
		STATUS_BOX_MODEL_TEXTURE.bind();
		Renderer.getInstance().draw(STATUX_BOX_MODEL.getVertexArray(), STATUX_BOX_MODEL.getIndexBuffer(), Game.shader);
	}
}
