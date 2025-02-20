package engine.controls;

import engine.Game;

public class PlayerController extends Controller {
	@Override
	protected void onUp(float dt) {
		Game.player.move(0, dt);
	}

	@Override
	protected void onDown(float dt) {
		Game.player.move(0, -dt);
	}

	@Override
	protected void onLeft(float dt) {
		Game.player.move(-dt, 0);
	}

	@Override
	protected void onRight(float dt) {
		Game.player.move(dt, 0);
	}

	@Override
	protected void onStart(float dt) {
		Game.toggleMenu();
		needReset = true;
	}

	@Override
	protected void onAction(float dt) {

	}
}
