package engine.objects.components.physics;

import engine.objects.Hitbox;
import engine.objects.core.Component;
import org.joml.Vector2f;
import static engine.objects.core.GameObject.DEFAULT_TILE_SIZE;

public class HitboxComponent extends Component {
  private Hitbox hitbox;

  public HitboxComponent() {
    this.update();
  }

  private Hitbox createObjectHitbox() {
    Vector2f position = this.gameObject.getTransform().getPosition();
    return new Hitbox(position.x, position.y, position.x + DEFAULT_TILE_SIZE, position.y + DEFAULT_TILE_SIZE);
  }

  public Hitbox getHitbox() {
    return this.hitbox;
  }

  @Override
  public void update() {
    this.hitbox = this.createObjectHitbox();
  }
}
