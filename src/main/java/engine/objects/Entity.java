package engine.objects;

import engine.scenes.SceneManager;
import org.joml.Vector2f;

public abstract class Entity extends GameObject {
	private static final int MOVE_TIME = 10;

	protected MoveDirection direction = MoveDirection.DOWN;
	protected int remainingMoveSteps = 0;

	public Entity(String texturePath, Vector2f position, boolean isSolid) {
		super(texturePath, position, isSolid);
	}

	public void update() {
		if(this.remainingMoveSteps > 0) {
			this.makeMoveStep();
		}
	}

	public void move(MoveDirection direction) {
		if(!this.canMove(direction)) {
			return;
		}

		this.direction = direction;
		this.remainingMoveSteps = MOVE_TIME;
	}

	private boolean canMove(MoveDirection direction) {
		Vector2f moveVector =  this.getMoveVector(direction);
		int targetX = (int) (this.position.x + moveVector.x * DEFAULT_TILE_SIZE);
		int targetY = (int) (this.position.y + moveVector.y * DEFAULT_TILE_SIZE);

		if(!SceneManager.getInstance().getCurrentScene().isInScene(targetX, targetY)) {
			return true;
		}

		// Check if solid tile exists
		if(SceneManager.getInstance().getCurrentScene().getTile(targetX, targetY).isSolid()) {
			return false;
		}

		// Check if solid object exists
		for(Entity entity : SceneManager.getInstance().getCurrentScene().getEntities()) {
			Hitbox nextHitbox = new Hitbox(targetX, targetY, targetX + DEFAULT_TILE_SIZE, targetY + DEFAULT_TILE_SIZE);
			if(!this.equals(entity) && entity.isSolid() && nextHitbox.isColliding(entity.hitbox)) {
				return false;
			}
		}

		return true;
	}

	private Vector2f getMoveVector(MoveDirection direction) {
		Vector2f moveVector = new Vector2f();

		switch (direction) {
			case UP    -> moveVector.y = 1;
			case DOWN  -> moveVector.y = -1;
			case LEFT  -> moveVector.x = -1;
			case RIGHT -> moveVector.x = 1;
		}

		return moveVector;
	}

	private void makeMoveStep() {
		float stepSize = 1.0f / MOVE_TIME;
		Vector2f moveVector = this.getMoveVector(direction);

		this.position.add(moveVector.mul(GameObject.DEFAULT_TILE_SIZE * stepSize));

		if(this.remainingMoveSteps == 1) {
			this.onStepEnd();
		}

		this.remainingMoveSteps--;
	}

	protected void onStepEnd() {
		// Snap to tile
		this.position.x = Math.round(this.position.x);
		this.position.y = Math.round(this.position.y);
	}
}
