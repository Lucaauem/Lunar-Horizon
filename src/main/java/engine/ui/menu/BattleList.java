package engine.ui.menu;

import engine.ui.BattleMenu;
import engine.ui.UiButton;
import org.joml.Vector2i;

public class BattleList extends BattleMenu {
	private static final int TEXT_PADDING = 15;

	private final Vector2i position;

	public BattleList(Vector2i position) {
		this.position = position;
	}

	public void setItems(ListElement[] items) {
		UiButton[] buttons = new UiButton[items.length];

		for(int i=0; i<items.length; i++) {
			Vector2i buttonPosition = new Vector2i(this.position.x, this.position.y - TEXT_PADDING * i);

			UiButton button = new UiButton(items[i].getLabel(), buttonPosition, items[i].getAction());
			buttons[i] = button;
		}

		this.setButtons(buttons);
	}
}
