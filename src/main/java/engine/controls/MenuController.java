package engine.controls;

import engine.Game;
import engine.ui.menu.UiMenu;

public class MenuController extends Controller {
	private final UiMenu menu;

	public MenuController(UiMenu menu) {
		this.menu = menu;
	}

	@Override
	protected void onUp(float dt) {
		menu.moveCursor(-1);
		needReset = true;
	}

	@Override
	protected void onDown(float dt) {
		menu.moveCursor(1);
		needReset = true;
	}

	@Override
	protected void onLeft(float dt) {
		menu.goPageBack();
		needReset = true;
	}

	@Override
	protected void onRight(float dt) {

	}

	@Override
	protected void onStart(float dt) {
		Game.toggleMenu();
		needReset = true;
	}

	@Override
	protected void onAction(float dt) {
		menu.toggleEvent();
		needReset = true;
	}
}
