package game.objects.npcs;

import engine.objects.components.interaction.DialogueInteraction;
import engine.objects.components.interaction.InteractionComponent;
import engine.objects.entities.Entity;
import engine.objects.entities.EntityTemplate;
import org.joml.Vector2f;

public class TalkerTemplate implements EntityTemplate {
  private static final String TEXT_SOURCE = "npc/talker";
  private static final String TEXTURE_PATH = "entities/";

  @Override
  public Entity build(Vector2f position, String texture, String parameter) {
    Entity entity = new Entity(TEXTURE_PATH + texture + ".png", position, true);
    entity.addComponent(new InteractionComponent(new DialogueInteraction(TEXT_SOURCE, parameter)));
    return entity;
  }
}
