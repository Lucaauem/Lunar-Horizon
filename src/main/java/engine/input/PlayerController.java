package engine.input;

import engine.objects.MoveDirection;
import engine.ui.UIManager;
import engine.ui.screen.OverworldUI;
import game.GameApplication;

public class PlayerController extends Controller {
	@Override
	protected void onUp(float dt) {
		GameApplication.player.move(MoveDirection.UP);
	}

	@Override
	protected void onDown(float dt) {
		GameApplication.player.move(MoveDirection.DOWN);
	}

	@Override
	protected void onLeft(float dt) {
		GameApplication.player.move(MoveDirection.LEFT);
	}

	@Override
	protected void onRight(float dt) {
		GameApplication.player.move(MoveDirection.RIGHT);
	}

	@Override
	protected void onStart(float dt) {
    OverworldUI ui = new OverworldUI();
    InputManager.getInstance().setController(new MenuController(ui.getMenu()).withCustomStart(() -> {
      ui.toggle();
      InputManager.getInstance().setController(new PlayerController());
      needReset = true;
    }));
    UIManager.getInstance().setUI(ui);
    UIManager.getInstance().getUI().toggle();
		needReset = true;
	}

	@Override
	protected void onAction(float dt) {
		GameApplication.player.interact();
		needReset = true;
	}
}
