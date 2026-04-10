package engine.ui;

import org.joml.Vector2f;

public class Anchor {
  public final Vector2f min = new Vector2f();
  public final Vector2f max = new Vector2f();

  public Anchor(float x0, float y0, float x1, float y1) {
    this.min.set(new Vector2f(x0, y0));
    this.max.set(new Vector2f(x1, y1));
  }
}
