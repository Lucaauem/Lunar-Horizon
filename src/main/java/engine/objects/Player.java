package engine.objects;

import engine.mechanics.items.Item;
import engine.objects.components.MovementComponent;
import engine.objects.components.RenderComponent;
import engine.objects.entities.Entity;
import engine.scenes.SceneManager;
import org.joml.Vector2f;
import org.joml.Vector2i;
import java.util.ArrayList;

public class Player extends Entity {
	private static final String PLAYER_TEXTURE_DOWN = "player/player_down.png";
	private static final String PLAYER_TEXTURE_UP = "player/player_up.png";
	private static final String PLAYER_TEXTURE_LEFT = "player/player_left.png";
	private static final String PLAYER_TEXTURE_RIGHT = "player/player_right.png";

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
    Vector2f position = this.getTransform().getPosition();
		Vector2i targetPosition = new Vector2i((int) position.x, (int) position.y);

		switch (this.lookDirection) {
			case UP    -> targetPosition.y += DEFAULT_TILE_SIZE;
			case DOWN  -> targetPosition.y -= DEFAULT_TILE_SIZE;
			case LEFT  -> targetPosition.x -= DEFAULT_TILE_SIZE;
			case RIGHT -> targetPosition.x += DEFAULT_TILE_SIZE;
		}

		for(Entity entity : SceneManager.getInstance().getCurrentScene().getEntities()) {
      Vector2f floatEntityPosition = entity.getTransform().getPosition();
			Vector2i entityPosition = new Vector2i((int) floatEntityPosition.x, (int) floatEntityPosition.y);
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

	public void move(MoveDirection direction) {
    MovementComponent movementComponent = this.getComponent(MovementComponent.class);
		if(movementComponent.getRemainingMoveSteps() > 0) {
			return;
		}

		this.lookDirection = direction;
		String newTexture = switch(direction) {
			case UP    -> PLAYER_TEXTURE_UP;
			case DOWN  -> PLAYER_TEXTURE_DOWN;
			case LEFT  -> PLAYER_TEXTURE_LEFT;
			case RIGHT -> PLAYER_TEXTURE_RIGHT;
		};
    this.getComponent(RenderComponent.class).changeTexture(newTexture);

    movementComponent.move(direction);
	}

	// region GETTER AND SETTER

	public int getLevel() { return this.level; }

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
