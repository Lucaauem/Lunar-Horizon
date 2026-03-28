package engine.objects;

import engine.objects.npcs.Talker;
import org.joml.Vector2f;

public class EntityBuilder {
	public Entity buildEntity(String type, String texture, String parameter, Vector2f position) {
		return switch (type) {
			case "TALKER" -> new Talker(position, parameter, texture);
			default -> null;
		};
	}
}
