package game.player;

import game.mechanics.items.Item;
import java.util.ArrayList;
import java.util.Random;

public class Player {
  private final PlayerEntity entity = new PlayerEntity();
  private int level = 1;
  private int maxHealth = 10;
  private int health = maxHealth;
  private int attack = 2;
  private int maxMagic = 15;
  private int magic = maxMagic;
  private int experience = 0;
  private int expNeededForNextLevel;
  private int money = 100;
  private final ArrayList<Item> inventory = new ArrayList<>();
  private final String[] spells = new String[0]; // TODO

  public Player() {
    this.expNeededForNextLevel = this.calcExpNeededForNextLevel();
  }

  private int calcExpNeededForNextLevel() {
    return (int) (100 * level * 1.5f);
  }

  public void addToInventory(Item item) {
    this.inventory.add(item);
  }

  public void removeFromInventory(Item item) {
    this.inventory.remove(item);
  }

  public void addExp(int exp) {
    this.experience += exp;

    if (this.experience >= this.expNeededForNextLevel) {
      this.levelUp();
      this.experience -= this.expNeededForNextLevel;
      this.expNeededForNextLevel = calcExpNeededForNextLevel();
    }
  }

  private void levelUp() {
    this.level++;

    this.maxHealth += (new Random()).nextInt(1, 5);
    this.maxMagic += (new Random()).nextInt(1, 4);
    this.attack += (new Random()).nextInt(1, 2);

    this.fullRestore();
  }

  public boolean isDead() {
    return this.health <= 0;
  }

  public void reduceHealth(int damage) {
    this.health -= damage;
  }

  public void changeMoney(int ammount) { this.money += ammount; }

  public void heal(int amount) {
    this.health = Math.min(maxHealth, this.health + amount);
  }

  public void fullRestore() {
    this.health = this.maxHealth;
    this.magic = this.maxMagic;
  }


  // region GETTER AND SETTER

  public PlayerEntity getEntity() {
    return this.entity;
  }

  public int getLevel() { return this.level; }

  public int getHealth() { return this.health; }

  public int getMaxHealth() { return this.maxHealth; }

  public int getAttack() { return this.attack; }

  public int getMagic() { return this.magic; }

  public int getMaxMagic() { return this.magic; }

  public String[] getSpells() { return this.spells; }

  public int getExperience() { return this.experience; }

  public int getMoney() { return this.money; }

  public Item[] getInventory() { return this.inventory.toArray(new Item[0]); }

  // endregion
}
