package engine.objects.entities;

import org.joml.Vector2f;

public interface EntityTemplate {
  Entity build(Vector2f position, String texture, String parameter);
}
