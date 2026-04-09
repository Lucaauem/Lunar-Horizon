package engine.battle;

import engine.Game;
import engine.GameState;
import engine.controls.GridController;
import engine.controls.InputManager;
import engine.controls.PlayerController;
import engine.mechanics.items.Item;
import engine.ui_new.UIManager;
import engine.ui_new.components.UITextbox;
import engine.ui_new.screen.BattleUI;
import engine.ui_new.screen.OverworldUI;
import java.util.Map;

public class BattleEngine {
	private static final String TEXT_SOURCE = "textbox/battle";

  private final BattleUI ui;
	private final Enemy enemy;
	private boolean isPlayerTurn = false;

  // TODO: Define config for generation
	public BattleEngine() {
		super();

    this.ui = new BattleUI(this);
		this.enemy = new Enemy("test");
	}

  public void startBattle() {
    UIManager.getInstance().setUI(this.ui);
    Game.changeState(GameState.BATTLE);

    UITextbox textbox = this.ui.getTextbox();
    textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_APPEARED");
    textbox.setOnClose(this::nextTurn);
    textbox.open();
  }

  private void endBattle() {
    Game.changeState(GameState.OVERWORLD);
  }

	public void nextTurn() {
    this.ui.getActionMenu().setVisibility(false);
    InputManager.getInstance().setController(null);
		this.isPlayerTurn = !this.isPlayerTurn;

		// Check ending condition
		if (Game.player.isDead()) {
			// TODO
		} else if (this.enemy.isDead()) {
			this.winBattle();
			return;
		}

		if (this.isPlayerTurn) {
      this.ui.getActionMenu().setVisibility(true);
      InputManager.getInstance().setController(new GridController(this.ui.getActionMenu()));
		} else {
			// TODO: Generate move
      UITextbox textbox = this.ui.getTextbox();
			textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_ATTACKED");
      textbox.setOnClose(() -> { this.enemy.attack(); this.ui.updatePlayerStats(); this.nextTurn(); });
			textbox.open();
		}
	}

  // TODO: EXP
	private void winBattle() {
    this.ui.getActionMenu().setVisibility(false);
    UITextbox textbox = this.ui.getTextbox();
		textbox.setTexts(BattleEngine.TEXT_SOURCE, "MONSTER_DEFEATED");
    textbox.setOnClose(this::endBattle);
		textbox.open();
	}

  public void attackEnemy() {
    this.ui.getActionMenu().setVisibility(false);
		this.enemy.changeHealth(-1 * Game.player.getAttack());

    UITextbox textbox = this.ui.getTextbox();
		textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_ATTACKED", Map.of("DMG", "" + Game.player.getAttack()));
    textbox.setOnClose(this::nextTurn);
		textbox.open();
	}

  // TODO
  public void useItem(Item item) {
		//item.use();

    UITextbox textbox = this.ui.getTextbox();
		textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_USED_ITEM", Map.of("ITEM", item.getName()));
    textbox.setOnClose(this::nextTurn);
		textbox.open();
	}

  // TODO: Cancel flee with some random value
  public void flee() {
    this.ui.getActionMenu().setVisibility(false);
    UITextbox textbox = this.ui.getTextbox();
    textbox.setTexts(BattleEngine.TEXT_SOURCE, "PLAYER_FLED");
    textbox.setOnClose(this::endBattle);
    textbox.open();
	}
}
