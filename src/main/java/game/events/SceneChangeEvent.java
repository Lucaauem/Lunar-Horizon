package game.events;

import engine.events.Event;
import engine.scenes.Scene;

public class SceneChangeEvent extends Event {
  public final Scene newScene;

  public SceneChangeEvent(Scene newScene) {
    this.newScene = newScene;
  }

  public Scene getNewScene() {
    return this.newScene;
  }
}
