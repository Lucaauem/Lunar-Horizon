package game;

import engine.core.GameImplementation;
import engine.core.GameState;
import engine.input.InputManager;
import engine.input.PlayerController;
import engine.objects.entities.EntityBuilder;
import game.objects.Player;
import engine.rendering.Camera;
import engine.scenes.SceneManager;
import engine.ui.UIManager;
import game.objects.npcs.TalkerTemplate;
import game.ui.screens.OverworldUI;
import game.mechanics.items.Potion;

public class GameApplication implements GameImplementation {
  public static Camera camera = new Camera();
  public static final Player player = new Player(); // TODO: Change player access
  private static GameState state = GameState.OVERWORLD;

  @Override
  public void init() {
    EntityBuilder.getInstance().addTemplate("TALKER", new TalkerTemplate());

    InputManager.getInstance().setController(new PlayerController());
    SceneManager.getInstance().switchScene("town/main");
    camera.fix(player);

    UIManager.getInstance().setUI(new OverworldUI());

    player.addToInventory(new Potion());
    player.addToInventory(new Potion());
  }

  @Override
  public void update(float dt) {
    InputManager.getInstance().update(dt);

    if (GameApplication.state == GameState.OVERWORLD) {
      player.update();
      SceneManager.getInstance().updateScene();
    }
  }

  @Override
  public void render() {
    switch (state) {
      case OVERWORLD -> {
        camera.update();
        SceneManager.getInstance().getCurrentScene().render();
        player.render();
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
        InputManager.getInstance().setController(new PlayerController());
      }
      case BATTLE -> {
        UIManager.getInstance().getUI().toggle();
        InputManager.getInstance().setController(null);
      }
    }
  }
}
