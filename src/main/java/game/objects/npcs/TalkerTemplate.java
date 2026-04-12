package game.objects.npcs;

import engine.objects.components.interaction.DialogueInteraction;
import engine.objects.components.interaction.InteractionComponent;
import engine.objects.entities.Entity;
import engine.objects.entities.EntityFactory;
import engine.objects.entities.EntityTemplate;

public class TalkerTemplate implements EntityTemplate {
  private static final String TEXT_SOURCE = "npc/talker";

  @Override
  public Entity build(EntityFactory entity, String parameter) {
    entity.add(new InteractionComponent(new DialogueInteraction(TEXT_SOURCE, parameter)));

    return entity.build();
  }
}
