package de.luca.lunarhorizon.engine.controls;

import de.luca.lunarhorizon.engine.ui.menu.UiTextbox;

public class TextboxController extends Controller{
	private final UiTextbox textbox;

	public TextboxController(UiTextbox textbox) {
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
