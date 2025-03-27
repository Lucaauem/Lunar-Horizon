package engine.objects;

import engine.graphics.Texture;
import engine.mechanics.items.Item;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
import org.joml.Vector2f;
import java.util.ArrayList;

public class Player extends Entity {
	private static final String PLAYER_TEXTURE_DOWN = "player/player_down.png";
	private static final String PLAYER_TEXTURE_UP = "player/player_up.png";
	private static final String PLAYER_TEXTURE_LEFT = "player/player_left.png";
	private static final String PLAYER_TEXTURE_RIGHT = "player/player_right.png";

	private final Vector2f lastValidPosition = new Vector2f();
	private int level = 1;
	private int maxHealth = 10;
	private int health = maxHealth;
	private int attack = 2;
	private int maxMagic = 15;
	private int magic = maxMagic;
	private int experience = 0;
	private final ArrayList<Item> inventory = new ArrayList<>();

	public Player() {
		super(PLAYER_TEXTURE_DOWN, new Vector2f(0, 0));
	}

	public Vector2f getLastValidPosition() { return lastValidPosition; }

	public int getLevel() { return this.level;}

	public int getHealth() { return this.health; }

	public int getMaxHealth() { return this.maxHealth; }

	public int getAttack() { return this.attack; }

	public int getMagic() { return this.magic; }

	public int getMaxMagic() { return this.magic; }

	public int getExperience() { return this.experience; }

	public Item[] getInventory() { return this.inventory.toArray(new Item[0]); }

	public void addToInventory(Item item) {
		this.inventory.add(item);
	}

	public void removeFromInventory(Item item) {
		this.inventory.remove(item);
	}

	public void heal(int amount) {
		this.health = Math.min(maxHealth, this.health + amount);
	}

	@Override
	public void move(MoveDirection direction) {
		switch(direction) {
			case UP    -> this.texture = new Texture(PLAYER_TEXTURE_UP);
			case DOWN  -> this.texture = new Texture(PLAYER_TEXTURE_DOWN);
			case LEFT  -> this.texture = new Texture(PLAYER_TEXTURE_LEFT);
			case RIGHT -> this.texture = new Texture(PLAYER_TEXTURE_RIGHT);
		}

		super.move(direction);
	}

	@Override
	protected void onStepEnd() {
		super.onStepEnd();

		Scene scene = SceneManager.getInstance().getCurrentScene();
		if(!scene.isInScene(this.position.x, this.position.y)) { return; }

		Tile currentTile = scene.getTile(this.position.x, this.position.y);

		if(currentTile.hasTrigger()) {
			currentTile.activateTrigger();
		}

		this.lastValidPosition.set(this.position.x, this.position.y);
	}
}
