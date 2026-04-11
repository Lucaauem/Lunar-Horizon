package engine.objects.core;

import org.joml.Vector2f;

public class Transform {
  private Vector2f position;

  public Transform(Vector2f position) {
    this.position = position != null ? position : new Vector2f(0, 0);
  }

  public void translate(Vector2f translation) {
    this.position.add(translation);
  }

  public void setPosition(Vector2f position) {
    this.position = new Vector2f(position);
  }

  public Vector2f getPosition() {
    return new Vector2f(this.position);
  }
}
