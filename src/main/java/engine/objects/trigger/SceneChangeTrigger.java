package engine.objects.trigger;

import engine.scenes.SceneManager;
import org.joml.Vector2f;
import org.json.JSONArray;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
    String sceneParameter = (String) this.getParameter("scene");

    Object positionParameter = this.getParameter("spawn");
    Vector2f scenePosition = null;

    if (positionParameter != null) {
      JSONArray position = (JSONArray) positionParameter;
      scenePosition = new Vector2f(position.getInt(0), position.getInt(1));
    }

		SceneManager.getInstance().switchScene(sceneParameter.toLowerCase(), scenePosition);
	}
}
