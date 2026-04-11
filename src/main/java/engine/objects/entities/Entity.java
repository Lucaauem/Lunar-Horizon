package engine.objects.entities;

import engine.objects.Hitbox;
import engine.objects.MoveDirection;
import engine.objects.components.RenderComponent;
import engine.objects.core.GameObject;
import engine.scenes.SceneManager;
import org.joml.Vector2f;

public class Entity extends GameObject {
  private static final int MOVE_TIME = 10;

  protected MoveDirection direction = MoveDirection.DOWN;
  protected int remainingMoveSteps = 0;

  public Entity(String texturePath, Vector2f position, boolean isSolid) {
    super(position);
    this.addComponent(new RenderComponent(texturePath));
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
    Vector2f position = this.getTransform().getPosition();
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
    for(Entity entity : SceneManager.getInstance().getCurrentScene().getEntities()) {
      Hitbox nextHitbox = new Hitbox(targetX, targetY, targetX + DEFAULT_TILE_SIZE, targetY + DEFAULT_TILE_SIZE);
      /*
      TODO
      if(!this.equals(entity) && entity.isSolid() && nextHitbox.isColliding(entity.hitbox)) {
        return false;
      }
       */
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
    Vector2f position = this.getTransform().getPosition();

    position.add(moveVector.mul(GameObject.DEFAULT_TILE_SIZE * stepSize));

    if(this.remainingMoveSteps == 1) {
      this.onStepEnd();
    }

    this.remainingMoveSteps--;
  }

  protected void onStepEnd() {
    // Snap to tile
    Vector2f position = this.getTransform().getPosition();
    position.x = Math.round(position.x);
    position.y = Math.round(position.y);
  }
}
