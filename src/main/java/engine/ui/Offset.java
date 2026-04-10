package engine.ui;

import org.joml.Vector2f;

public class Offset {
  public float horizontal = 0;
  public float vertical = 0;

  public Vector2f asVector() {
    return new Vector2f(horizontal, vertical);
  }
}
