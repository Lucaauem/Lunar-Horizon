package engine.battle;

import engine.Game;
import engine.GameState;
import engine.controls.BattleController;
import engine.graphics.Model;
import engine.graphics.Texture;
import engine.graphics.renderer.Renderer;
import engine.ui.BattleMenu;
import engine.ui.UiButton;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public class BattleEngine {
	private static final Texture BACKGROUND_TEXTURE = new Texture("ui/battle/background/debug.png");
	private static final Texture STATUS_BOX_MODEL_TEXTURE = new Texture("ui/battle/status_box.png");
	private static final Texture GROUND_TEXTURE = new Texture("ui/battle/ground/debug.png");
	private static final Texture MENU_TEXTURE = new Texture("ui/battle/menu.png");
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
	private static final Model STATUX_BOX_MODEL = new Model(new float[] {
			0.025f, 0.805f, 0, 1,
			0.175f, 0.805f, 1, 1,
			0.175f, 0.975f, 1, 0,
			0.025f, 0.975f, 0, 0
	});

	private Enemy enemy;
	private boolean isPlayerTurn = true; // TODO: Maybe calculate based on speed attribute?
	private BattleMenu menu = new BattleMenu();

	public BattleEngine() {
		// Init game state
		Game.changeState(GameState.BATTLE);
		Game.setController(new BattleController(this));

		// Load monster data
		// TODO: Randomly choose monster based on certain parameters
		this.enemy = new Enemy("test");

		// TODO: Show on player turn
		this.menu.setButtons(new UiButton[]{
			new UiButton("Attack", new Vector2i(20, 35), () -> System.out.println(1)),
			new UiButton("Magic", new Vector2i(20, 15), () -> System.out.println(1)),
			new UiButton("Items", new Vector2i(120, 35), () -> System.out.println(1)),
			new UiButton("Status", new Vector2i(120, 15), () -> System.out.println(1)),
			new UiButton("Block", new Vector2i(220, 35), () -> System.out.println(1)),
			new UiButton("Flee", new Vector2i(220, 15), () -> System.out.println(1))
		});
	}

	public void nextTurn() {
		if(this.isPlayerTurn) {
			// TODO: Wait for user input
		} else {
			// TODO: Generate move
			this.enemy.attack();
		}

		this.isPlayerTurn = !this.isPlayerTurn;
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
		STATUS_BOX_MODEL_TEXTURE.bind();
		Renderer.getInstance().draw(STATUX_BOX_MODEL.getVertexArray(), STATUX_BOX_MODEL.getIndexBuffer(), Game.shader);
		this.menu.render();
	}

	public void moveCursor(int ammount) {
		this.menu.moveCursor(ammount);
	}

	public void clickButton() {
		this.menu.clickButton();
	}
}
