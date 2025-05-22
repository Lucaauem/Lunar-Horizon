package engine.objects.npcs;

import engine.ui.menu.UiTextbox;
import org.joml.Vector2f;

public class Talker extends Npc {
	private static final String TEXT_SOURCE = "npc/talker";
	private static final String TEXTURE_PATH = "player/player_down.png";

	private final String text;
	private UiTextbox textbox = null;

	public Talker(Vector2f position, String text) {
		super(TEXTURE_PATH, position);
		this.text = text;
	}

	@Override
	public void onInteract() {
		this.textbox = new UiTextbox();
		this.textbox.setTexts(TEXT_SOURCE, this.text);
		this.textbox.open(() -> this.textbox = null);
	}

	@Override
	public void render() {
		super.render();

		if(this.textbox != null) {
			this.textbox.render();
		}
	}
}
