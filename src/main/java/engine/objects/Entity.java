package engine.objects;

import org.joml.Vector2f;

public class Entity extends GameObject {
	private static final int MOVE_TIME = 10;

	protected MoveDirection direction = MoveDirection.DOWN;
	private int remainingMoveSteps = 0;

	public Entity(String texturePath, Vector2f position) {
		super(texturePath, position);
	}

	public void update() {
		if(this.remainingMoveSteps > 0) {
			this.makeMoveStep();
			System.out.println(this.position.x + ", " + this.position.y);
		}
	}

	public void move(MoveDirection direction) {
		if(this.remainingMoveSteps > 0) {
			return;
		}
		this.direction = direction;
		this.remainingMoveSteps = MOVE_TIME;
	}

	private void makeMoveStep() {
		float stepSize = 1.0f / MOVE_TIME;
		Vector2f moveVector = new Vector2f();

		switch (this.direction) {
			case UP    -> moveVector.y = 1;
			case DOWN  -> moveVector.y = -1;
			case LEFT  -> moveVector.x = -1;
			case RIGHT -> moveVector.x = 1;
		}

		this.position.add(moveVector.mul(GameObject.DEFAULT_TILE_SIZE * stepSize));

		// Snap to tile
		if(this.remainingMoveSteps == 1) {
			this.position.x = Math.round(this.position.x);
			this.position.y = Math.round(this.position.y);
		}

		this.remainingMoveSteps--;
	}
}
