package engine.scenes;

public class SceneManager {
	private static final SceneManager instance = new SceneManager();

	private Scene currentScene;

	private SceneManager() {}

	public static SceneManager getInstance() {
		return instance;
	}

	public void switchScene(String sceneName) {
		this.currentScene = new Scene(sceneName);
	}

	public Scene getCurrentScene() {
		return this.currentScene;
	}
}
