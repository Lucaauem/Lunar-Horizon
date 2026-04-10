package engine.objects.entities;

import engine.objects.entities.npcs.Talker;
import org.joml.Vector2f;

public class EntityBuilder {
	public Entity buildEntity(String type, String texture, String parameter, Vector2f position) {
		return switch (type) {
			case "TALKER" -> new Talker(position, parameter, texture);
			default -> null;
		};
	}
}
