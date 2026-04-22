package engine.objects.entities;

import org.json.JSONObject;

public interface EntityTemplate {
  Entity build(EntityFactory factory, JSONObject parameters);
}
