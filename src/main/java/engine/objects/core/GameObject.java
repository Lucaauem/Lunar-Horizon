package engine.objects.core;

import org.joml.Vector2f;
import java.util.ArrayList;

/*
TODO
public class GameObject {
	public boolean isColliding(GameObject other) {
		return this.hitbox.isColliding(other.hitbox);
	}
}
 */

public class GameObject {
  public static final int DEFAULT_TILE_SIZE = 16;

  private final Transform transform;
  ArrayList<Component> components;

  public GameObject(Vector2f position) {
    this.transform = new Transform(position);
    this.components = new ArrayList<>();
  }

  public Transform getTransform() {
    return this.transform;
  }

  public void update() {
    for (Component component : this.components) {
      component.update();
    }
  }

  public void addComponent(Component c) {
    components.add(c);
    c.setGameObject(this);
  }

  public <T extends Component> T getComponent(Class<T> type) {
    for (Component c : components) {
      if (type.isInstance(c)) {
        return type.cast(c);
      }
    }
    return null;
  }
}
