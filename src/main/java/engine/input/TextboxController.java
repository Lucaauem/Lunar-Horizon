package engine.input;

import engine.ui.components.UITextbox;

public class TextboxController extends Controller{
	private final UITextbox textbox;

	public TextboxController(UITextbox textbox) {
		this.textbox = textbox;
	}

	@Override
	protected void onUp(float dt) { }

	@Override
	protected void onDown(float dt) { }

	@Override
	protected void onLeft(float dt) { }

	@Override
	protected void onRight(float dt) { }

	@Override
	protected void onStart(float dt) { }

	@Override
	protected void onAction(float dt) {
		this.textbox.next();
		needReset = true;
	}
}
