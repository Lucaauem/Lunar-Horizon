package de.luca.lunarhorizon.engine.scenes;

import de.luca.lunarhorizon.engine.Game;
import de.luca.lunarhorizon.engine.GameState;
import de.luca.lunarhorizon.engine.controls.BattleController;
import de.luca.lunarhorizon.engine.ui.BattleMenu;
import de.luca.lunarhorizon.engine.ui.menu.UiTextbox;

public abstract class MenuScene {
	protected final BattleMenu menu = new BattleMenu();
	protected final UiTextbox textbox = new UiTextbox();
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
