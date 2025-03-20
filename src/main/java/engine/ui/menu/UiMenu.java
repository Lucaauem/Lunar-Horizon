package engine.ui.menu;

import engine.GameWindow;
import engine.graphics.Texture;
import engine.objects.GameObject;
import engine.ui.Text;
import engine.ui.UiElement;
import org.joml.Vector2i;
import java.util.ArrayList;
import java.util.Stack;

public class UiMenu extends UiElement {
	private static final Texture TEXTURE = new Texture("ui/menu.png");
	private static final Vector2i POSITION = new Vector2i(
			GameWindow.RESOLUTION.x - 7 * GameObject.DEFAULT_TILE_SIZE,
			(GameWindow.RESOLUTION.y - TEXTURE.getHeight()) / 2
	);
	private static final int TEXT_PADDING_LEFT = 10;
	private static final int TEXT_OFFSET_Y = TEXTURE.getHeight() - 20;
	private static final int TEXT_SPACING = Text.CHARACTER_HEIGHT + 7;

	private ArrayList<MenuItem> items;
	private int cursorIndex = 0;
	private MenuPage currentPage = null;
	private final Stack<MenuPage> pageStack = new Stack<>();

	public UiMenu() {
		this(MenuPage.MAIN_MENU_0);
	}

	public UiMenu(MenuPage page) {
		super(TEXTURE, POSITION);
		this.changeMenuPage(page);
	}

	public void moveCursor(int direction) {
		this.cursorIndex += direction;
		this.cursorIndex = Math.max(0, this.cursorIndex);
		this.cursorIndex = Math.min(this.items.size() - 1, this.cursorIndex);
	}

	public void changeMenuPage(MenuPage page) {
		this.changeMenuPage(page, true);
	}

	public void changeMenuPage(MenuPage page, boolean pushToStack) {
		this.items = PageContent.getPageItems(page, this);
		this.cursorIndex = 0;

		if(this.currentPage != null && pushToStack) {
			this.pageStack.push(this.currentPage);
		}
		this.currentPage = page;
	}

	public void refreshPage() {
		this.changeMenuPage(this.currentPage);
	}

	public void goPageBack() {
		if(!this.pageStack.isEmpty()) {
			this.changeMenuPage(this.pageStack.pop(), false);
		}
	}

	@Override
	public void render() {
		super.render();

		for(int i=0; i<this.items.size(); i++) {
			Vector2i itemTextPos = new Vector2i(
					POSITION.x + TEXT_PADDING_LEFT + Text.CHARACTER_WIDTH,
					POSITION.y + TEXT_OFFSET_Y - (i * TEXT_SPACING)
			);

			Text text = new Text(this.items.get(i).getLabel(), itemTextPos);
			text.render();
		}

		Vector2i itemTextPos = new Vector2i(
				POSITION.x + TEXT_PADDING_LEFT,
				POSITION.y + TEXT_OFFSET_Y - (cursorIndex * TEXT_SPACING)
		);

		Text text = new Text("-", itemTextPos);
		text.render();
	}

	public void toggleEvent() {
		this.items.get(this.cursorIndex).action();
	}
}
