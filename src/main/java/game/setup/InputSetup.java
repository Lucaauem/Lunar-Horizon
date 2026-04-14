package game.setup;

import engine.input.Controller;
import engine.input.InputManager;
import engine.objects.MoveDirection;
import engine.ui.UIManager;
import game.objects.Player;
import game.ui.screens.OverworldUI;

public class InputSetup implements GameSetup {
  private final Player player;

  public InputSetup(Player player) {
    this.player = player;
  }

  @Override
  public void setup() {
    Controller playerController = new Controller()
      .bindUp(() -> this.player.move(MoveDirection.UP), false)
      .bindDown(() -> this.player.move(MoveDirection.DOWN), false)
      .bindLeft(() -> this.player.move(MoveDirection.LEFT), false)
      .bindRight(() -> this.player.move(MoveDirection.RIGHT), false)
      .bindStart(() -> {
        OverworldUI ui = new OverworldUI();
        Controller menuController = Controller.forUI(ui.getMenu());
        menuController.bindStart(() -> {
          ui.toggle();
          InputManager.getInstance().popController();
        }, true);

        InputManager.getInstance().pushController(menuController);
        UIManager.getInstance().setUI(ui);
        UIManager.getInstance().getUI().toggle();
      }, false)
      .bindAction(this.player::interact, false);

    InputManager.getInstance().pushController(playerController);
  }
}
