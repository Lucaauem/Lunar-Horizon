package engine.objects;

import engine.objects.npcs.Talker;
import org.joml.Vector2f;
import org.json.JSONObject;
import util.FileHandler;

public class EntityBuilder {
	private static final String PARAMETER_PATH = "src/main/assets/data/builder_parameters/Entity.json";
	private static final JSONObject PARAMETERS = FileHandler.readJSON(PARAMETER_PATH);

	private Vector2f position;

	public Entity buildEntity(int type, int parameter, Vector2f position) {
		this.position = position;

		return switch (type) {
			case 0 -> createTalker(parameter);
			default -> null;
		};
	}

	private Talker createTalker(int parameter) {
		return new Talker(this.position, PARAMETERS.getString("" + parameter));
	}
}
