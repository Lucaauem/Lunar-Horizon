package engine.ui.menu;

import java.util.ArrayList;
import java.util.List;

public class PageContent {
	public static ArrayList<MenuItem> getPageItems(MenuPages page, UiMenu menu) {
		return switch (page) {
			case MAIN_MENU_0 -> new ArrayList<>(List.of(
					new MenuItem("PLAYER", () -> menu.changeMenuPage(MenuPages.PLAYER_MENU)),
					new MenuItem("SYSTEM", () -> menu.changeMenuPage(MenuPages.SYSTEM))
			));
			case PLAYER_MENU -> new ArrayList<>(List.of(
					new MenuItem("INV.", () -> { /* TODO */}),
					new MenuItem("PARTY", () -> { /* TODO */ })
			));
			case SYSTEM -> new ArrayList<>(List.of(
					new MenuItem("EXIT", () -> System.exit(0))
			));
		};
	}
}
