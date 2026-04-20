package game.player;

import engine.objects.MoveDirection;
import engine.objects.PlayerControllable;
import engine.objects.components.MovementComponent;
import engine.objects.components.RenderComponent;
import engine.objects.components.interaction.InteractionComponent;
import engine.objects.entities.Entity;
import engine.scenes.SceneManager;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class PlayerEntity extends Entity implements PlayerControllable {
  private static final String PLAYER_TEXTURE_DOWN = "player/player_down.png";
  private static final String PLAYER_TEXTURE_UP = "player/player_up.png";
  private static final String PLAYER_TEXTURE_LEFT = "player/player_left.png";
  private static final String PLAYER_TEXTURE_RIGHT = "player/player_right.png";

  private MoveDirection lookDirection = MoveDirection.DOWN;

  public PlayerEntity() {
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

      if (!entityPosition.equals(targetPosition)) {
        continue;
      }

      if(entity.hasComponent(InteractionComponent.class)) {
        entity.getComponent(InteractionComponent.class).onInteract();
        return;
      }
    }
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
}
