package game.battle;

import engine.events.EventManager;
import engine.scenes.SceneManager;
import java.util.Random;

public class BattleGenerator {
  private static final int MIN_STEPS_TO_BATTLE = 10;
  private static final int MAX_STEPS_TO_BATTLE = 50;

  private boolean active;
  private int stepsToBattle = -1;

  public BattleGenerator() {
    EventManager.getInstance().subscribe("PLAYER_MOVE", this::step);
    this.active = SceneManager.getInstance().getCurrentScene().canSpawnEnemies();
    EventManager.getInstance().subscribe("SCENE_CHANGE", () -> this.active = SceneManager.getInstance().getCurrentScene().canSpawnEnemies());
  }

  public void step() {
    if (!this.active) {
      return;
    }

    this.stepsToBattle--;

    if (stepsToBattle == 0) {
      new BattleEngine().startBattle();
    }

    if (stepsToBattle <= 0) {
      this.resetSteps();
    }
  }

  public void resetSteps() {
    this.stepsToBattle = new Random().nextInt(MAX_STEPS_TO_BATTLE - MIN_STEPS_TO_BATTLE + 1);
  }
}
