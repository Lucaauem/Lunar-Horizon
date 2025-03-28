package engine.battle;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import org.joml.Matrix4f;

public class BattleEngine {
	private static final Texture UI_TEXTURE = new Texture("ui/battle.png");
	private static final Model UI_MODEL = new Model(new float[] {
			0.0f, 0.0f, 0, 1, // 0
			1.0f, 0.0f, 1, 1, // 1
			1.0f, 1.0f, 1, 0, // 2
			0.0f, 1.0f, 0, 0  // 3
	});

	private Enemy[] enemies;

	public BattleEngine() {

	}

	public void render() {
		UI_TEXTURE.bind();

		Matrix4f proj = new Matrix4f().ortho(0.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		Game.shader.setUniformMat4f("u_MVP", proj);

		Renderer.getInstance().draw(UI_MODEL.getVertexArray(), UI_MODEL.getIndexBuffer(), Game.shader);
	}
}
