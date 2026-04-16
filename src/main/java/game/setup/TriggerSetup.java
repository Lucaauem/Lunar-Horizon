package game.setup;

import engine.scenes.SceneManager;
import game.objects.trigger.ShopTrigger;

public class TriggerSetup implements GameSetup {
  @Override
  public void setup() {
    SceneManager sceneManager = SceneManager.getInstance();

    sceneManager.addTrigger("OPEN_SHOP", ShopTrigger.class);
  }
}
