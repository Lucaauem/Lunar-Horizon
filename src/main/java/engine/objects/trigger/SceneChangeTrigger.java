package engine.objects.trigger;

import engine.scenes.SceneManager;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
    String sceneParameter = (String) this.getParameter("scene");

		if(sceneParameter.equals("PREVIOUS_SCENE")) {
			SceneManager.getInstance().returnToLastScene();
			return;
		}
		SceneManager.getInstance().switchScene(sceneParameter.toLowerCase());
	}
}
