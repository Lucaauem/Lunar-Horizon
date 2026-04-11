package engine.objects.entities;

import engine.objects.components.MovementComponent;
import engine.objects.components.RenderComponent;
import engine.objects.components.physics.ColliderComponent;
import engine.objects.core.GameObject;
import org.joml.Vector2f;

public class Entity extends GameObject {
  public Entity(String texturePath, Vector2f position, boolean isSolid) {
    super(position);
    this.addComponent(new RenderComponent(texturePath));
    this.addComponent(new MovementComponent());
    this.addComponent(new ColliderComponent(isSolid));
  }
}
