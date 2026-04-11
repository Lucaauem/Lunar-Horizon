import engine.core.Engine;
import engine.core.GameImplementation;
import engine.core.GameWindow;
import game.GameApplication;

public class Main {
	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
		gameWindow.create();
    GameWindow.setScale();

    GameImplementation game = new GameApplication();
		Engine engine = new Engine(gameWindow.getWindow(), game);
    engine.start();

		gameWindow.destroy();
	}
}
