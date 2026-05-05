package game.battle;

import engine.events.EventManager;
import engine.scenes.SceneManager;
import java.util.Random;

public class BattleGenerator {
  private static final int MIN_STEPS_TO_BATTLE = 10;
  private static final int MAX_STEPS_TO_BATTLE = 50;

  private static BattleGenerator instance;

  private boolean active;
  private int stepsToBattle = -1;

  private BattleGenerator() {
    EventManager.getInstance().subscribe("PLAYER_MOVE", this::step);
    EventManager.getInstance().subscribe("SCENE_CHANGE", () -> this.active = SceneManager.getInstance().getCurrentScene().canSpawnEnemies());
    this.update();
  }

  public void update() {
    this.active = SceneManager.getInstance().getCurrentScene().canSpawnEnemies();
  }

  public static BattleGenerator getInstance() {
    if (instance == null) {
      instance = new BattleGenerator();
    }
    return instance;
  }

  public void step() {
    if (!this.active) {
      return;
    }

    this.stepsToBattle--;

    if (stepsToBattle == 0) {
      new BattleEngine("test").startBattle();
    }

    if (stepsToBattle <= 0) {
      this.resetSteps();
    }
  }

  public void resetSteps() {
    this.stepsToBattle = new Random().nextInt(MAX_STEPS_TO_BATTLE - MIN_STEPS_TO_BATTLE + 1) + MIN_STEPS_TO_BATTLE;
  }
}
