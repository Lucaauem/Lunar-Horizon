package engine.objects.trigger;

import org.json.JSONObject;
import java.util.HashMap;

public abstract class Trigger {
  private final HashMap<String, Object> parameters;

  public Trigger() {
    this.parameters = new HashMap<>();
  }

	public abstract void trigger();

	public void setParameters(JSONObject parameters) {
    for (String key : parameters.keySet()) {
      this.parameters.put(key, parameters.get(key));
    }
	}

  protected Object getParameter(String key) {
    return this.parameters.get(key);
  }
}
