package de.luca.lunarhorizon;

import de.luca.lunarhorizon.engine.Game;
import de.luca.lunarhorizon.engine.GameWindow;

public class Main {
	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
		gameWindow.create();

		Game game = new Game(gameWindow.getWindow());
		game.start();

		gameWindow.destroy();
	}

}
