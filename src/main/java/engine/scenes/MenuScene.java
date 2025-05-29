package engine.scenes;

import engine.Game;
import engine.GameState;
import engine.controls.BattleController;
import engine.ui.BattleMenu;

public abstract class MenuScene {
	protected final BattleMenu menu = new BattleMenu();
	protected BattleMenu activeMenu = menu;

	public MenuScene() {
		// Init game state
		Game.changeState(GameState.BATTLE);
		Game.setController(new BattleController(this));
	}

	public void moveCursor(int ammount) {
		this.activeMenu.moveCursor(ammount);
	}

	public void clickButton() {
		this.activeMenu.clickButton();
	}

	protected void end() {
		Game.changeState(GameState.OVERWORLD);
	}

	public abstract void render();
}
