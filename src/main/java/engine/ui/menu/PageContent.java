package engine.ui.menu;

import engine.Game;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PageContent {
	public static ArrayList<MenuItem> getPageItems(MenuPage page, UiMenu menu) {
		return switch (page) {
			case MAIN_MENU_0 -> new ArrayList<>(List.of(
					new MenuItem("PLAYER", () -> menu.changeMenuPage(MenuPage.PLAYER_MENU)),
					new MenuItem("SYSTEM", () -> menu.changeMenuPage(MenuPage.SYSTEM))
			));
			case PLAYER_MENU -> new ArrayList<>(List.of(
					new MenuItem("INV.", () -> { /* TODO */}),
					new MenuItem("STATS", PageContent::showStats)
			));
			case SYSTEM -> new ArrayList<>(List.of(
					new MenuItem("EXIT", () -> System.exit(0))
			));
		};
	}

	// !TODO! Open real menu
	private static void showStats() {
		System.out.println("====== Stats ======");
		System.out.println(MessageFormat.format("Level: {0}", Game.player.getLevel()));
		System.out.println(MessageFormat.format("HP: {0}/{1}", Game.player.getHealth(), Game.player.getMaxHealth()));
		System.out.println(MessageFormat.format("MP: {0}/{1}", Game.player.getMagic(), Game.player.getMaxMagic()));
		System.out.println(MessageFormat.format("Attack: {0}", Game.player.getAttack()));
		System.out.println(MessageFormat.format("EXP: {0}", Game.player.getExperience()));
		System.out.println("===================");
	}
}
