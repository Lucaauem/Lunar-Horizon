import engine.core.Game;
import engine.core.GameWindow;

public class Main {
	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
		gameWindow.create();
    GameWindow.setScale();

		Game game = new Game(gameWindow.getWindow());
		game.start();

		gameWindow.destroy();
	}
}
