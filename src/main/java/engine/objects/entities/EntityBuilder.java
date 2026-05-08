package engine.objects.entities;

import engine.objects.components.interaction.InteractionComponent;
import org.joml.Vector2f;
import org.json.JSONArray;

public class EntityBuilder {
  private static final String TEXTURE_PATH = "entities/";
  private static final String TEXTURE_EXTENSION = ".png";

  public Entity create(String texture, Vector2f position, JSONArray interactions) {

    EntityFactory factory = new EntityFactory();
    factory.set(position);
    factory.set(TEXTURE_PATH + texture + TEXTURE_EXTENSION);
    factory.add(new InteractionComponent(interactions));

    return factory.build();
  }
}