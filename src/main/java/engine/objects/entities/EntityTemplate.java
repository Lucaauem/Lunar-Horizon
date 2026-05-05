package engine.objects.entities;

import org.json.JSONObject;

public interface EntityTemplate {
  Entity build(EntityFactory entity, JSONObject parameters);
}
