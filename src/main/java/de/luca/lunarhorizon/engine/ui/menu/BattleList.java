package de.luca.lunarhorizon.engine.ui.menu;

import de.luca.lunarhorizon.engine.ui.BattleMenu;
import de.luca.lunarhorizon.engine.ui.UiButton;
import org.joml.Vector2i;

public class BattleList extends BattleMenu {
	private static final int TEXT_PADDING = 15;
	private static final int MAX_ITEMS = 8;
	private static final String EXIT_LABEL = "EXIT";
	private static final Vector2i DEFAULT_POSITION = new Vector2i(225, 150);

	private final Vector2i position;

	public BattleList() {
		this.position = DEFAULT_POSITION;
	}

	public void setItems(ListElement[] items, Runnable onClose) {
		UiButton[] buttons = new UiButton[items.length + 1]; // Add +1 for exit button

		for(int i=0; i<items.length; i++) {
			UiButton button = new UiButton(items[i].getLabel(), this.calcItemPosition(i), items[i].getAction());
			buttons[i] = button;
		}

		buttons[items.length] = new UiButton(EXIT_LABEL, this.calcItemPosition(MAX_ITEMS), onClose);

		this.setButtons(buttons);
	}

	private Vector2i calcItemPosition(int i) {
		return new Vector2i(this.position.x, this.position.y - TEXT_PADDING * i);
	}
}
