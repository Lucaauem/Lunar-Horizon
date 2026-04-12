package engine.objects.entities;

import engine.objects.core.Component;
import org.joml.Vector2f;
import java.util.ArrayList;

public class EntityFactory {
  private final ArrayList<Component> components;
  private Vector2f position = new Vector2f();
  private String texture = null;

  public EntityFactory() {
    this.components = new ArrayList<>();
  }

  public void add(Component component) {
    this.components.add(component);
  }

  public void set(String texturePath) {
    this.texture = texturePath;
  }

  public void set(Vector2f position) {
    this.position = position;
  }

  public Entity build() {
    Entity entity = new Entity(this.texture, new Vector2f(this.position), true);

    for (Component component : this.components) {
      entity.addComponent(component);
    }

    this.components.clear();
    this.texture = null;
    this.position = new Vector2f();

    return entity;
  }
}
