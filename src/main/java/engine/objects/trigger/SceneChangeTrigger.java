package engine.objects.trigger;

import engine.scenes.SceneManager;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
		if(this.parameter.equals("PREVIOUS_SCENE")) {
			SceneManager.getInstance().returnToLastScene();
			return;
		}
		SceneManager.getInstance().switchScene(this.parameter.toLowerCase());
	}
}
