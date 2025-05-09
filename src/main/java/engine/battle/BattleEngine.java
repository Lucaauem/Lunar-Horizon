package engine.battle;

import engine.Game;
import engine.GameState;
import engine.controls.BattleController;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import engine.ui.BattleMenu;
import engine.ui.Text;
import engine.ui.UiButton;
import engine.ui.UiElement;
import engine.ui.menu.UiTextbox;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public class BattleEngine {
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
	private final UiTextbox textbox = new UiTextbox(new String[0], new Vector2i(10, 10));
	private boolean isPlayerTurn = false; // TODO: Maybe calculate based on speed attribute?

	public BattleEngine() {
		// Init game state
		Game.changeState(GameState.BATTLE);
		Game.setController(new BattleController(this));
		this.textbox.setTexts(new String[] {"A monster appears!"});

		// Load monster data
		// TODO: Randomly choose monster based on certain parameters
		this.enemy = new Enemy("test");

		// TODO: Show on player turn
		this.menu.setButtons(new UiButton[]{
			new UiButton("Attack", new Vector2i(20, 35), this::attackEnemy),
			new UiButton("Magic", new Vector2i(20, 15), () -> System.out.println(1)),
			new UiButton("Items", new Vector2i(120, 35), () -> System.out.println(1)),
			new UiButton("Status", new Vector2i(120, 15), () -> System.out.println(1)),
			new UiButton("Block", new Vector2i(220, 35), () -> System.out.println(1)),
			new UiButton("Flee", new Vector2i(220, 15), () -> System.out.println(1))
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
			this.textbox.setTexts(new String[]{"The monster attacks!"});
			this.textbox.open(() -> { this.enemy.attack(); this.nextTurn(); });
		}
	}

	private void attackEnemy() {
		this.enemy.changeHealth(-1 * Game.player.getAttack());
		this.textbox.setTexts(new String[]{"You attack!", "The monster took  " + Game.player.getAttack() + " damage!"});
		this.textbox.open(this::nextTurn);
	}

	private void winBattle() {
		// TODO: EXP
		this.textbox.setTexts(new String[]{"You defeated the monster!"});
		this.textbox.open(this::endBattle);
	}

	private void endBattle() {
		// TODO
		System.exit(0);
	}

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

		// Render player stats
		new Text("" + Game.player.getHealth(), new Vector2i(25, 158)).render();
		new Text("" + Game.player.getMagic(), new Vector2i(25, 145)).render();
		HEALTH_ICON.render();
		MANA_ICON.render();
	}

	public void moveCursor(int ammount) {
		this.menu.moveCursor(ammount);
	}

	public void clickButton() {
		this.menu.clickButton();
	}
}
