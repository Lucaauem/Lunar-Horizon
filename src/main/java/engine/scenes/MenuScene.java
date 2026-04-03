package engine.scenes;

import engine.Game;
import engine.GameState;
import engine.controls.BattleController;
import engine.controls.InputManager;
import engine.ui.BattleMenu;
import engine.ui.menu.UiTextbox;

public abstract class MenuScene {
	protected final BattleMenu menu = new BattleMenu();
	protected final UiTextbox textbox = new UiTextbox();
	protected BattleMenu activeMenu = menu;

	public MenuScene() {
		// Init game state
		Game.changeState(GameState.BATTLE);
		InputManager.getInstance().setController(new BattleController(this));
	}

	public void moveCursor(int amount) {
		this.activeMenu.moveCursor(amount);
	}

	public void clickButton() {
		this.activeMenu.clickButton();
	}

	protected void end() {
		Game.changeState(GameState.OVERWORLD);
	}

	public abstract void render();
}
