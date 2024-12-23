package engine.ui;

import engine.GameWindow;
import engine.graphics.Texture;
import engine.objects.GameObject;
import org.joml.Vector2i;

public class UiMenu extends UiElement {
	private static final Texture TEXTURE = new Texture("menu.png");
	private static final Vector2i POSITION = new Vector2i(
			GameWindow.RESOLUTION.x - 11 * GameObject.DEFAULT_TILE_SIZE,
			(GameWindow.RESOLUTION.y - TEXTURE.getHeight()) / 2
	);

	public UiMenu() {
		super(TEXTURE, POSITION);
	}
}
