package engine.objects.trigger;

import engine.scenes.SceneManager;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
		SceneManager.getInstance().switchScene(this.parameter);
	}
}
