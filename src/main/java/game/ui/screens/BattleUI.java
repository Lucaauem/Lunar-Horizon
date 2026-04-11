package game.ui.screens;

import engine.ui.UIScreen;
import game.GameApplication;
import game.battle.BattleEngine;
import engine.ui.UIElement;
import engine.ui.UILayer;
import engine.ui.components.*;
import engine.ui.components.list.UIListElement;
import engine.ui.text.UIText;
import org.joml.Vector3f;

public class BattleUI extends UIScreen {
  private static final Vector3f COLOR_GROUND = new Vector3f(0, 0.5f, 0);
  private static final Vector3f COLOR_GROUND_2 = new Vector3f(0.4f, 0.4f, 0.4f);
  private static final Vector3f COLOR_SKY = new Vector3f(0.5f, 0.8f, 0.9f);
  private static final String PLAYER_HEALTH_ICON = "ui/battle/health_icon.png";
  private static final String PLAYER_MANA_ICON = "ui/battle/mana_icon.png";
  private static final float STATUS_ICON_GAP = 5;

  private UITextbox textbox;
  private UIMenuGrid actionMenu;
  private UIText playerHealth;
  private UIText playerMana;
  private final BattleEngine battle;

  public BattleUI(BattleEngine battle) {
    super();
    this.battle = battle;
    this.init();
  }

  public UITextbox getTextbox() {
    return this.textbox;
  }

  public UIMenuGrid getActionMenu() {
    return this.actionMenu;
  }

  public void updatePlayerStats() {
    this.playerHealth.setTextRaw("" + GameApplication.player.getHealth());
    this.playerMana.setTextRaw("" + GameApplication.player.getMagic());
  }

  @Override
  public void init() {
    this.textbox = new UITextbox();

    // Background
    UIPanel background = new UIPanel(0, 0, 1, 1);
    background.setColor(new Vector3f(0, 0, 0));
    UIPanel ground = new UIPanel(0, 0.35f, 1, 0.475f);
    ground.setColor(COLOR_GROUND);
    UIPanel menuBackground = new UIPanel(0, 0, 1, 0.35f);
    menuBackground.setColor(COLOR_GROUND_2);
    UIPanel sky = new UIPanel(0, 0.475f, 1, 1);
    sky.setColor(COLOR_SKY);

    // Menu
    this.actionMenu = new UIMenuGrid(2, 3);
    this.actionMenu.setElements(new UIListElement[]{
            new UIListElement("ATTACK", this.battle::attackEnemy),
            new UIListElement("MAGIC",  () -> { /* TODO */ }),
            new UIListElement("ITEMS",  () -> { /* TODO */ }),
            new UIListElement("STATUS", () -> { /* TODO */ }),
            new UIListElement("BLOCK",  () -> { /* TODO */ }),
            new UIListElement("FLEE",   this.battle::flee)
    });
    this.actionMenu.setParent(menuBackground);
    this.actionMenu.setAnchor(0.10f, 0.1f, 0.9f, 0.9f);
    this.actionMenu.setVisibility(false);

    // Player Stats
    UIWrapper playerStats = new UIWrapper(0.025f, 0.85f, 0.20f, 0.975f);
    this.playerHealth = new UIText("", playerStats);
    this.playerHealth.setAnchor(0.0f, 0.5f, 1.0f, 1.0f);
    this.playerMana = new UIText("", playerStats);
    this.playerMana.setAnchor(0.0f, 0.0f, 1.0f, 0.5f);
    UIImage healthIcon = new UIImage(0, 0.5f, 1.0f, 1.0f, PLAYER_HEALTH_ICON);
    healthIcon.setParent(playerStats);
    UIImage manaIcon = new UIImage(0, 0, 1.0f, 0.5f, PLAYER_MANA_ICON);
    manaIcon.setParent(playerStats);

    this.playerHealth.setOffset(healthIcon.getSize().x + STATUS_ICON_GAP, 0);
    this.playerMana.setOffset(healthIcon.getSize().x + STATUS_ICON_GAP, 0);
    this.updatePlayerStats();

    this.layers.add(new UILayer(-1, new UIElement[] { background }));
    this.layers.add(new UILayer(0, new UIElement[] { ground, menuBackground, sky }));
    this.layers.add(new UILayer(2, new UIElement[]{ this.actionMenu }));
    this.layers.add(new UILayer(2, new UIElement[]{ playerStats }));
    this.layers.add(new UILayer(3, new UIElement[]{ this.textbox }));
  }
}
