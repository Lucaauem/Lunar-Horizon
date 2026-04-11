package engine.objects.entities;

import org.joml.Vector2f;
import java.security.InvalidParameterException;
import java.util.HashMap;

public class EntityBuilder {
  private static EntityBuilder instance;

  private final HashMap<String, EntityTemplate> templates = new HashMap<>();

  private EntityBuilder() {}

  public static EntityBuilder getInstance() {
    if (instance == null) {
      instance = new EntityBuilder();
    }
    return instance;
  }

  public Entity create(String template, String texture, Vector2f position, String parameter) {
    if (!this.templates.containsKey(template)) {
      throw new InvalidParameterException("Entity template does not exist: " + template);
    }
    return this.templates.get(template).build(position, texture, parameter);
  }

  public void addTemplate(String name, EntityTemplate template) {
    this.templates.put(name, template);
  }
}