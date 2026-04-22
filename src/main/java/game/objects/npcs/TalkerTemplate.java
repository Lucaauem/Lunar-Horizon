package game.objects.npcs;

import engine.objects.components.interaction.DialogueInteraction;
import engine.objects.components.interaction.InteractionComponent;
import engine.objects.entities.Entity;
import engine.objects.entities.EntityFactory;
import engine.objects.entities.EntityTemplate;
import org.json.JSONObject;

public class TalkerTemplate implements EntityTemplate {
  private static final String TEXT_SOURCE = "npc/talker";

  @Override
  public Entity build(EntityFactory entity, JSONObject parameters) {
    entity.add(new InteractionComponent(new DialogueInteraction(TEXT_SOURCE, parameters.getString("text"))));

    return entity.build();
  }
}
