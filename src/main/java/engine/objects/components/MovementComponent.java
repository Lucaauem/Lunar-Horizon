package engine.objects.components;

import engine.objects.Hitbox;
import engine.objects.MoveDirection;
import engine.objects.PlayerControllable;
import engine.objects.Tile;
import engine.objects.components.physics.ColliderComponent;
import engine.objects.core.Component;
import engine.objects.entities.Entity;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
import org.joml.Vector2f;
import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class MovementComponent extends Component {
  private static final int MOVE_TIME = 10;

  private MoveDirection direction = MoveDirection.DOWN;
  private Vector2f lastValidPosition = new Vector2f();
  private int remainingMoveSteps = 0;

  public int getRemainingMoveSteps() {
    return this.remainingMoveSteps;
  }

  public void move(MoveDirection direction) {
    if (!this.canMove(direction)) {
      return;
    }

    this.remainingMoveSteps = MOVE_TIME;
    this.direction = direction;
  }

  @Override
  public void update() {
    if (this.remainingMoveSteps > 0) {
      makeMoveStep();
    }
  }

  private void makeMoveStep() {
    float stepSize = 1.0f / MOVE_TIME;

    Vector2f moveVector = getMoveVector(this.direction);
    this.gameObject.getTransform().translate(moveVector.mul(DEFAULT_TILE_SIZE * stepSize));

    if (this.remainingMoveSteps == 1) {
      onStepEnd();
    }

    this.remainingMoveSteps--;
  }

  private void onStepEnd() {
    Vector2f position = this.gameObject.getTransform().getPosition();
    this.gameObject.getTransform().setPosition(new Vector2f(position).round());

    if (this.gameObject instanceof PlayerControllable) {
      Scene scene = SceneManager.getInstance().getCurrentScene();
      if(!scene.isInScene(position.x, position.y)) { return; }

      Tile currentTile = scene.getTile(position.x, position.y);

      if(currentTile.hasTrigger()) {
        currentTile.activateTrigger();
      }

      this.lastValidPosition = position;
    }
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

  public Vector2f getLastValidPosition() {
    return new Vector2f(this.lastValidPosition);
  }

  private boolean canMove(MoveDirection direction) {
    Vector2f moveVector =  this.getMoveVector(direction);
    Vector2f position = this.gameObject.getTransform().getPosition();
    int targetX = (int) (position.x + moveVector.x * DEFAULT_TILE_SIZE);
    int targetY = (int) (position.y + moveVector.y * DEFAULT_TILE_SIZE);

    if(!SceneManager.getInstance().getCurrentScene().isInScene(targetX, targetY)) {
      return true;
    }

    // Check if solid tile exists
    if(SceneManager.getInstance().getCurrentScene().getTile(targetX, targetY).isSolid()) {
      return false;
    }

    // Check if solid object exists
    Hitbox nextHitbox = new Hitbox(targetX, targetY, targetX + DEFAULT_TILE_SIZE, targetY + DEFAULT_TILE_SIZE);
    for(Entity entity : SceneManager.getInstance().getCurrentScene().getEntities()) {
      ColliderComponent otherCollider = entity.getComponent(ColliderComponent.class);

      if ((otherCollider == null) || (otherCollider.getHitbox() == null)) {
        continue;
      }

      if (!this.gameObject.equals(entity) && otherCollider.isSolid() && nextHitbox.isColliding(otherCollider.getHitbox())) {
        return false;
      }
    }

    return true;
  }
}
