package engine.controls;

import engine.scenes.MenuScene;

public class BattleController extends Controller {
	private final MenuScene menuScene;

	public BattleController(MenuScene battle) {
		this.menuScene = battle;
	}

	@Override
	protected void onUp(float dt) {
		this.menuScene.moveCursor(-1);
		needReset = true;
	}

	@Override
	protected void onDown(float dt) {
		this.menuScene.moveCursor(1);
		needReset = true;
	}

	@Override
	protected void onLeft(float dt) {
		this.menuScene.moveCursor(-2);
		needReset = true;
	}

	@Override
	protected void onRight(float dt) {
		this.menuScene.moveCursor(2);
		needReset = true;
	}

	@Override
	protected void onStart(float dt) {

	}

	@Override
	protected void onAction(float dt) {
		this.menuScene.clickButton();
		needReset = true;
	}
}
