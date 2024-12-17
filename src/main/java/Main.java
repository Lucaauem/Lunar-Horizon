import engine.Game;
import engine.GameWindow;

public class Main {
	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
		gameWindow.create();

		Game game = new Game(gameWindow.getWindow());
		game.startGameLoop();

		gameWindow.destroy();
	}

}
