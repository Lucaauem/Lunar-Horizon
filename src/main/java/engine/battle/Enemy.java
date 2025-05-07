package engine.battle;

import engine.graphics.Model;
import engine.graphics.Texture;
import org.json.JSONObject;
import util.FileHandler;

public class Enemy {
	private static final String MONSTER_DATA_PATH = "src/main/assets/data/enemies/";
	private static final String MONSTER_TEXTURE_PATH = "enemies/";

	private final Texture texture;
	private final Model model = new Model(new float[] {
			0.6f, 0.3f,  0, 1,
			0.8f, 0.3f,  1, 1,
			0.8f, 0.64f, 1, 0,
			0.6f, 0.64f, 0, 0
	});
	private int health;
	private int damage;

	public Enemy(String name) {
		JSONObject data = FileHandler.readJSON(MONSTER_DATA_PATH + name + ".json");

		System.out.println(MONSTER_TEXTURE_PATH + data.getString("texture") + ".png");
		this.texture = new Texture(MONSTER_TEXTURE_PATH + data.getString("texture") + ".png");
	}

	public Texture getTexture() {
		return this.texture;
	}

	public Model getModel() {
		return this.model;
	}
}
