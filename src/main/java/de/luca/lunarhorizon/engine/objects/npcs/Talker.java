package de.luca.lunarhorizon.engine.objects.npcs;

import de.luca.lunarhorizon.engine.ui.menu.UiTextbox;
import org.joml.Vector2f;

public class Talker extends Npc {
	public static final String TEXT_SOURCE = "npc/talker";
	private static final String TEXTURE_PATH = "entities/";
	private static final boolean IS_SOLID = true;

	private final String text;
	private UiTextbox textbox = null;

	public Talker(Vector2f position, String text, String texture) {
		super(TEXTURE_PATH + texture + ".png", position, IS_SOLID);
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
