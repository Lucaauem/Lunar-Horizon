package engine.controls;

import engine.Game;

public class MenuController extends Controller {
	@Override
	protected void onUp(float dt) {

	}

	@Override
	protected void onDown(float dt) {

	}

	@Override
	protected void onLeft(float dt) {

	}

	@Override
	protected void onRight(float dt) {

	}

	@Override
	protected void onStart(float dt) {
		Game.toggleMenu();
		needReset = true;
	}
}
