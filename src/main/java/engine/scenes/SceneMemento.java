package engine.scenes;

import org.joml.Vector2f;

public class SceneMemento {
	private final String sceneName;
	private final Vector2f position;

	public SceneMemento(String sceneName, Vector2f playPosition) {
		this.sceneName = sceneName;
		this.position = playPosition;
	}

	public String getSceneName() {
		return this.sceneName;
	}

	public Vector2f getPosition() {
		return this.position;
	}
}
