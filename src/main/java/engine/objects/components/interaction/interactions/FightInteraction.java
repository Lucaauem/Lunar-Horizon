package engine.objects.components.interaction.interactions;

import game.battle.BattleEngine;

public class FightInteraction extends Interaction {
  private final String fightId;

  public FightInteraction(String fightId) {
    this.fightId = fightId;
  }

  @Override
  public void onInteract() {
    BattleEngine battle = new BattleEngine(this.fightId);
    battle.setOnFightEnd(this::onInteractionEnd);
    battle.startBattle();
  }
}
