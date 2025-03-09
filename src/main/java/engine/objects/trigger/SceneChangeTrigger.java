package engine.objects.trigger;

import engine.scenes.SceneManager;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
		if(TargetScene.valueOf(this.parameter) == TargetScene.LAST_VALID) {
			SceneManager.getInstance().returnToLastScene();
			return;
		}

		SceneManager.getInstance().switchScene(this.parameter.toLowerCase());
	}
}
