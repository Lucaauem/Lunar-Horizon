package engine.objects.core;

public abstract class Component {
  protected GameObject gameObject;

  public void setGameObject(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public void update() {}
}