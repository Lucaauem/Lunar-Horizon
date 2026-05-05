package game.setup;

import engine.objects.entities.EntityBuilder;
import game.objects.npcs.FightTriggerTemplate;
import game.objects.npcs.TalkerTemplate;

public class TemplateSetup implements GameSetup{
  @Override
  public void setup() {
    EntityBuilder.addTemplate("TALKER", new TalkerTemplate());
    EntityBuilder.addTemplate("FIGHT_TRIGGER", new FightTriggerTemplate());
  }
}
