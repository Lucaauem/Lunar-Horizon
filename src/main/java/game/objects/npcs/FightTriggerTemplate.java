package game.objects.npcs;

import engine.objects.components.interaction.DialogueInteraction;
import engine.objects.components.interaction.InteractionComponent;
import engine.objects.entities.Entity;
import engine.objects.entities.EntityFactory;
import engine.objects.entities.EntityTemplate;
import game.battle.BattleEngine;
import org.json.JSONObject;

public class FightTriggerTemplate implements EntityTemplate {
  private static final String TEXT_SOURCE = "npc/talker";

  @Override
  public Entity build(EntityFactory entity, JSONObject parameters) {
    DialogueInteraction interaction = new DialogueInteraction(TEXT_SOURCE, parameters.getString("text"));
    interaction.setOnTextboxClose(() -> new BattleEngine(parameters.getString("fightId")).startBattle());

    entity.add(new InteractionComponent(interaction));

    return entity.build();
  }
}
