package engine.controls;

import engine.ui_new.components.list.UIList;

public class MenuController extends Controller {
	private final UIList list;

	public MenuController(UIList list) {
		this.list = list;
	}

	@Override
	protected void onUp(float dt) {
		this.list.moveCursorUp();
		needReset = true;
	}

	@Override
	protected void onDown(float dt) {
		this.list.moveCursorDown();
		needReset = true;
	}

	@Override
	protected void onLeft(float dt) {
		// TODO
		needReset = true;
	}

	@Override
	protected void onRight(float dt) {

	}

	@Override
	protected void onStart(float dt) {

	}

	@Override
	protected void onAction(float dt) {
		this.list.selectElement();
		needReset = true;
	}
}
