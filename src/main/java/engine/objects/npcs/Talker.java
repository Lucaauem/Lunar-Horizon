package engine.objects.npcs;

import engine.controls.InputManager;
import engine.ui_new.UIManager;
import engine.ui_new.components.UITextbox;
import org.joml.Vector2f;

public class Talker extends Npc {
	public static final String TEXT_SOURCE = "npc/talker";
	private static final String TEXTURE_PATH = "entities/";
	private static final boolean IS_SOLID = true;

	private final String text;

	public Talker(Vector2f position, String text, String texture) {
		super(TEXTURE_PATH + texture + ".png", position, IS_SOLID);
		this.text = text;
	}

	@Override
	public void onInteract() {
    UITextbox textbox = new UITextbox();
    textbox.setTexts(TEXT_SOURCE, this.text);
    textbox.setOnClose(() -> InputManager.getInstance().setToPreviousController());
    UIManager.getInstance().addElement(textbox);
    textbox.open();
	}

	@Override
	public void render() {
		super.render();
	}
}
