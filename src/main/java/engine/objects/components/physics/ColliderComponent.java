package engine.objects.components.physics;

import engine.objects.Hitbox;
import engine.objects.core.Component;
import org.joml.Vector2f;
import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class ColliderComponent extends Component {
  private Hitbox hitbox;
  private final boolean isSolid;

  public ColliderComponent(boolean isSolid) {
    this.isSolid = isSolid;
  }

  public boolean isColliding(ColliderComponent other) {
    return this.hitbox.isColliding(other.hitbox);
  }

  private Hitbox createObjectHitbox() {
    Vector2f position = this.gameObject.getTransform().getPosition();
    return new Hitbox(position.x, position.y, position.x + DEFAULT_TILE_SIZE, position.y + DEFAULT_TILE_SIZE);
  }

  @Override
  public void update() {
    this.hitbox = this.createObjectHitbox();
  }

  // region GETTER AND SETTER

  public Hitbox getHitbox() {
    return this.hitbox;
  }

  public boolean isSolid() {
    return this.isSolid;
  }

  // endregion
}
