package engine.battle;

import engine.Game;
import engine.GameState;
import engine.controls.BattleController;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import engine.mechanics.items.Item;
import engine.ui.BattleMenu;
import engine.ui.text.Text;
import engine.ui.UiButton;
import engine.ui.UiElement;
import engine.ui.menu.BattleList;
import engine.ui.menu.ListElement;
import engine.ui.menu.UiTextbox;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import java.util.ArrayList;
import java.util.Map;

public class BattleEngine {
	private static final String TEXT_SOURCE = "textbox/battle";
	private static final Texture BACKGROUND_TEXTURE = new Texture("ui/battle/background/debug.png");
	private static final Texture STATUS_BOX_MODEL_TEXTURE = new Texture("ui/battle/status_box.png");
	private static final Texture GROUND_TEXTURE = new Texture("ui/battle/ground/debug.png");
	private static final Texture MENU_TEXTURE = new Texture("ui/battle/menu.png");
	private static final UiElement HEALTH_ICON = new UiElement(new Texture("ui/battle/health_icon.png"), new Vector2i(15, 158));
	private static final UiElement MANA_ICON = new UiElement(new Texture("ui/battle/mana_icon.png"), new Vector2i(15, 145));
	private static final UiElement STATUX_BOX_MODEL_ELEMENT = new UiElement(STATUS_BOX_MODEL_TEXTURE, new Vector2i(8, 140));
	private static final Model MENU_MODEL = new Model(new float[] {
			0.0f, 0.0f, 0, 1,
			1.0f, 0.0f, 1, 1,
			1.0f, 0.35f, 1, 0,
			0.0f, 0.35f, 0, 0
	});
	private static final Model BACKGROUND_MODEL = new Model(new float[] {
			0.0f, 0.35f, 0, 1,
			1.0f, 0.35f, 1, 1,
			1.0f, 1.0f,  1, 0,
			0.0f, 1.0f,  0, 0
	});
	private static final Model GROUND_MODEL = new Model(new float[] {
			0.0f, 0.35f,  0, 1,
			1.0f, 0.35f,  1, 1,
			1.0f, 0.475f, 1, 0,
			0.0f, 0.475f, 0, 0
	});

	private final Enemy enemy;
	private final BattleMenu menu = new BattleMenu();
	private final BattleList submenu = new BattleList(new Vector2i(225, 150));
	private final UiTextbox textbox = new UiTextbox(new Vector2i(10, 10));
	private BattleMenu activeMenu = menu;
	private boolean isPlayerTurn = false; // TODO: Maybe calculate based on speed attribute?

	public BattleEngine() {
		// Init game state
		Game.changeState(GameState.BATTLE);
		Game.setController(new BattleController(this));
		this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_APPEARED");

		// Load monster data
		// TODO: Randomly choose monster based on certain parameters
		this.enemy = new Enemy("test");

		// TODO: Show on player turn
		this.menu.setButtons(new UiButton[]{
			new UiButton("Attack", new Vector2i(20, 35), this::attackEnemy),
			new UiButton("Magic", new Vector2i(20, 15), () -> System.out.println(1)),   	// TODO
			new UiButton("Items", new Vector2i(120, 35), this::openInventory),
			new UiButton("Status", new Vector2i(120, 15), () -> System.out.println(1)), 	// TODO
			new UiButton("Block", new Vector2i(220, 35), () -> System.out.println(1)),  	// TODO
			new UiButton("Flee", new Vector2i(220, 15), this::flee)
		});

		this.textbox.open();

		this.nextTurn();
	}

	public void nextTurn() {
		this.isPlayerTurn = !this.isPlayerTurn;

		// Check ending condition
		if(Game.player.isDead()) {
			// TODO
		} else if(this.enemy.isDead()) {
			this.winBattle();
			return;
		}

		if(this.isPlayerTurn) {
			// TODO: Wait for user input
		} else {
			// TODO: Generate move
			this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_ATTACKED");
			this.textbox.open(() -> { this.enemy.attack(); this.nextTurn(); });
		}
	}

	private void attackEnemy() {
		this.enemy.changeHealth(-1 * Game.player.getAttack());
		this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_ATTACKED", Map.of("DMG", "" + Game.player.getAttack()));
		this.textbox.open(this::nextTurn);
	}

	private void flee() {
		// TODO: Cancel flee with some random value
		this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_FLED");
		this.textbox.open(this::endBattle);
	}

	private void winBattle() {
		// TODO: EXP
		this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_DEFEATED");
		this.textbox.open(this::endBattle);
	}

	private void endBattle() {
		// TODO
		System.exit(0);
	}

	private void useItem(Item item) {
		this.submenu.setVisibility(false);
		this.activeMenu = this.menu;
		item.use();
		this.textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_USED_ITEM", Map.of("ITEM", item.getName()));
		this.textbox.open(this::nextTurn);
	}

	// region UI ELEMENTS

	private void openInventory() {
		ArrayList<ListElement> items = new ArrayList<>();
		for(Item playerItem : Game.player.getInventory()) {
			items.add(new ListElement(playerItem.getName(), () -> this.useItem(playerItem)));
		}
		this.submenu.setItems(items.toArray(new ListElement[0]));

		this.submenu.setVisibility(true);
		this.activeMenu = submenu;
	}

	public void moveCursor(int ammount) {
		this.activeMenu.moveCursor(ammount);
	}

	public void clickButton() {
		this.activeMenu.clickButton();
	}

	// endregion

	public void render() {
		Matrix4f proj = new Matrix4f().ortho(0.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		Game.shader.setUniformMat4f("u_MVP", proj);

		// Render background
		BACKGROUND_TEXTURE.bind();
		Renderer.getInstance().draw(BACKGROUND_MODEL.getVertexArray(), BACKGROUND_MODEL.getIndexBuffer(), Game.shader);
		GROUND_TEXTURE.bind();
		Renderer.getInstance().draw(GROUND_MODEL.getVertexArray(), GROUND_MODEL.getIndexBuffer(), Game.shader);
		MENU_TEXTURE.bind();
		Renderer.getInstance().draw(MENU_MODEL.getVertexArray(), MENU_MODEL.getIndexBuffer(), Game.shader);

		// Render entities
		Model enemyModel = this.enemy.getModel();
		this.enemy.getTexture().bind();
		Renderer.getInstance().draw(enemyModel.getVertexArray(), enemyModel.getIndexBuffer(), Game.shader);

		// Render ui elements
		STATUX_BOX_MODEL_ELEMENT.render();
		if(this.textbox.isOpen()) {
			this.textbox.render();
		}else {
			this.menu.render();
		}

		if(this.submenu.isVisible()) {
			this.submenu.render();
		}

		// Render player stats
		new Text("" + Game.player.getHealth(), new Vector2i(25, 158)).render();
		new Text("" + Game.player.getMagic(), new Vector2i(25, 145)).render();
		HEALTH_ICON.render();
		MANA_ICON.render();
	}
}
