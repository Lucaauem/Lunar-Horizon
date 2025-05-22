package engine.controls;

import engine.Game;
import engine.objects.MoveDirection;

public class PlayerController extends Controller {
	@Override
	protected void onUp(float dt) {
		Game.player.move(MoveDirection.UP);
	}

	@Override
	protected void onDown(float dt) {
		Game.player.move(MoveDirection.DOWN);
	}

	@Override
	protected void onLeft(float dt) {
		Game.player.move(MoveDirection.LEFT);
	}

	@Override
	protected void onRight(float dt) {
		Game.player.move(MoveDirection.RIGHT);
	}

	@Override
	protected void onStart(float dt) {
		Game.toggleMenu();
		needReset = true;
	}

	@Override
	protected void onAction(float dt) {
		Game.player.interact();
		needReset = true;
	}
}
