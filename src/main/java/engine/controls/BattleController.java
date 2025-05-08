package engine.controls;

import engine.battle.BattleEngine;

public class BattleController extends Controller {
	private final BattleEngine battle;

	public BattleController(BattleEngine battle) {
		this.battle = battle;
	}

	@Override
	protected void onUp(float dt) {
		this.battle.moveCursor(-1);
		needReset = true;
	}

	@Override
	protected void onDown(float dt) {
		this.battle.moveCursor(1);
		needReset = true;
	}

	@Override
	protected void onLeft(float dt) {
		this.battle.moveCursor(-2);
		needReset = true;
	}

	@Override
	protected void onRight(float dt) {
		this.battle.moveCursor(2);
		needReset = true;
	}

	@Override
	protected void onStart(float dt) {

	}

	@Override
	protected void onAction(float dt) {
		this.battle.clickButton();
		needReset = true;
	}
}
