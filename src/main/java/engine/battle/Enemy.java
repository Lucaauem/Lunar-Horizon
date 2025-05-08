package engine.battle;

import engine.Game;
import engine.graphics.Model;
import engine.graphics.Texture;
import org.json.JSONObject;
import util.FileHandler;

public class Enemy {
	private static final String MONSTER_DATA_PATH = "src/main/assets/data/enemies/";
	private static final String MONSTER_TEXTURE_PATH = "enemies/";

	private final Texture texture;
	private final Model model = new Model(new float[] {
			0.6f, 0.4f,  0, 1,
			0.8f, 0.4f,  1, 1,
			0.8f, 0.74f, 1, 0,
			0.6f, 0.74f, 0, 0
	});
	private int health;
	private int damage;

	public Enemy(String id) {
		JSONObject data = FileHandler.readJSON(MONSTER_DATA_PATH + id + ".json");

		this.texture = new Texture(MONSTER_TEXTURE_PATH + data.getString("texture") + ".png");
		this.health = data.getInt("health");
		this.damage = data.getInt("damage");
	}

	public void attack() {
		Game.player.reduceHealth(this.damage);
	}

	public Texture getTexture() {
		return this.texture;
	}

	public Model getModel() {
		return this.model;
	}
}
