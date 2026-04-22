package game;

import engine.core.GameImplementation;
import engine.core.GameState;
import engine.input.InputManager;
import engine.objects.entities.EntityBuilder;
import engine.rendering.Camera;
import engine.scenes.SceneManager;
import engine.ui.UIManager;
import game.objects.npcs.TalkerTemplate;
import game.player.Player;
import game.setup.InputSetup;
import game.setup.TriggerSetup;
import game.ui.screens.OverworldUI;
import game.mechanics.items.Potion;

public class GameApplication implements GameImplementation {
  public static Camera camera = new Camera();
  public static final Player player = new Player();
  private static GameState state = GameState.OVERWORLD;

  @Override
  public void init() {
    new InputSetup(GameApplication.player.getEntity()).setup();
    new TriggerSetup().setup();

    EntityBuilder.addTemplate("TALKER", new TalkerTemplate());

    SceneManager.getInstance().switchScene("town/guardhouse");
    camera.fix(player.getEntity());

    UIManager.getInstance().setUI(new OverworldUI());

    player.addToInventory(new Potion());
    player.addToInventory(new Potion());
  }

  @Override
  public void update(float dt) {
    InputManager.getInstance().update(dt);

    if (GameApplication.state == GameState.OVERWORLD) {
      player.getEntity().update();
      SceneManager.getInstance().updateScene();
    }
  }

  @Override
  public void render() {
    switch (state) {
      case OVERWORLD -> {
        camera.update();
        SceneManager.getInstance().getCurrentScene().render();
        player.getEntity().render();
        UIManager.getInstance().render();
      }
      case BATTLE -> UIManager.getInstance().render();
    }
  }

  public static void changeState(GameState newState) {
    GameApplication.state = newState;

    switch (GameApplication.state) {
      case OVERWORLD -> {
        UIManager.getInstance().setUI(new OverworldUI());
        InputManager.getInstance().enableControlls();
      }
      case BATTLE -> UIManager.getInstance().getUI().toggle();
    }
  }
}
