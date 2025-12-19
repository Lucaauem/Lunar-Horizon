package de.luca.lunarhorizon.engine.objects;

import de.luca.lunarhorizon.engine.graphics.Texture;
import de.luca.lunarhorizon.engine.mechanics.items.Item;
import de.luca.lunarhorizon.engine.scenes.Scene;
import de.luca.lunarhorizon.engine.scenes.SceneManager;
import org.joml.Vector2f;
import org.joml.Vector2i;
import java.util.ArrayList;

public class Player extends Entity {
	private static final String PLAYER_TEXTURE_DOWN = "player/player_down.png";
	private static final String PLAYER_TEXTURE_UP = "player/player_up.png";
	private static final String PLAYER_TEXTURE_LEFT = "player/player_left.png";
	private static final String PLAYER_TEXTURE_RIGHT = "player/player_right.png";

	private final Vector2f lastValidPosition = new Vector2f();
	private MoveDirection lookDirection = MoveDirection.DOWN;
	private int level = 1;
	private int maxHealth = 10;
	private int health = maxHealth;
	private int attack = 2;
	private int maxMagic = 15;
	private int magic = maxMagic;
	private int experience = 0;
	private int money = 100;
	private final ArrayList<Item> inventory = new ArrayList<>();

	public Player() {
		super(PLAYER_TEXTURE_DOWN, new Vector2f(0, 0), false);
	}

	public void interact() {
		Vector2i targetPosition = new Vector2i((int) this.position.x, (int) this.position.y);

		switch (this.lookDirection) {
			case UP    -> targetPosition.y += DEFAULT_TILE_SIZE;
			case DOWN  -> targetPosition.y -= DEFAULT_TILE_SIZE;
			case LEFT  -> targetPosition.x -= DEFAULT_TILE_SIZE;
			case RIGHT -> targetPosition.x += DEFAULT_TILE_SIZE;
		}

		for(Entity entity : SceneManager.getInstance().getCurrentScene().getEntities()) {
			Vector2i entityPosition = new Vector2i((int) entity.position.x, (int) entity.position.y);
			if(entity instanceof Interactable && entityPosition.equals(targetPosition)) {
				((Interactable) entity).onInteract();
				return;
			}
		}
	}

	public void addToInventory(Item item) {
		this.inventory.add(item);
	}

	public void removeFromInventory(Item item) {
		this.inventory.remove(item);
	}

	public boolean isDead() {
		return this.health <= 0;
	}

	public void reduceHealth(int damage) {
		this.health -= damage;
	}

	public void changeMoney(int ammount) { this.money += ammount; }

	public void heal(int amount) {
		this.health = Math.min(maxHealth, this.health + amount);
	}

	@Override
	public void move(MoveDirection direction) {
		if(this.remainingMoveSteps > 0) {
			return;
		}

		this.lookDirection = direction;
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

	// region GETTER AND SETTER

	public Vector2f getLastValidPosition() { return lastValidPosition; }

	public int getLevel() { return this.level;}

	public int getHealth() { return this.health; }

	public int getMaxHealth() { return this.maxHealth; }

	public int getAttack() { return this.attack; }

	public int getMagic() { return this.magic; }

	public int getMaxMagic() { return this.magic; }

	public int getExperience() { return this.experience; }

	public int getMoney() { return this.money; }

	public Item[] getInventory() { return this.inventory.toArray(new Item[0]); }

	// endregion
}
