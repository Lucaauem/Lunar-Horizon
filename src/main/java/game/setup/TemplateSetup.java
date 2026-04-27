package game.setup;

import engine.objects.entities.EntityBuilder;
import game.objects.npcs.TalkerTemplate;

public class TemplateSetup implements GameSetup{
  @Override
  public void setup() {
    EntityBuilder.addTemplate("TALKER", new TalkerTemplate());
  }
}
