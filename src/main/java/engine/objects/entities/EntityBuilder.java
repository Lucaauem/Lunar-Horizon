package engine.objects.entities;

import org.joml.Vector2f;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class EntityBuilder {
  private static final String TEXTURE_PATH = "entities/";
  private static final String TEXTURE_EXTENSION = ".png";
  private static final HashMap<String, EntityTemplate> templates = new HashMap<>();

  public Entity create(String template, String texture, Vector2f position, JSONObject parameters) {
    if (!EntityBuilder.templates.containsKey(template)) {
      throw new InvalidParameterException("Entity template does not exist: " + template);
    }

    EntityFactory factory = new EntityFactory();
    factory.set(position);
    factory.set(TEXTURE_PATH + texture + TEXTURE_EXTENSION);

    return EntityBuilder.templates.get(template).build(factory, parameters);
  }

  public static void addTemplate(String name, EntityTemplate template) {
    EntityBuilder.templates.put(name, template);
  }
}