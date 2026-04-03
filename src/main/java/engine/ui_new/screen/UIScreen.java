package engine.ui_new.screen;

import engine.ui_new.UILayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public abstract class UIScreen {
  protected ArrayList<UILayer> layers;

  public UIScreen() {
    this.layers = new ArrayList<>();
    this.init();
  }

  public abstract void init();
  public void onUpdate() {}
  public void onExit() {}
  public void update() {}

  public void render() {
    UILayer[] sortedLayers = this.layers.toArray(new UILayer[0]);
    Arrays.sort(sortedLayers, Comparator.comparingInt(UILayer::getZ));

    for(UILayer layer: sortedLayers) {
      if(layer.isVisible()) {
        layer.render();
      }
    }
  }

  public void toggle() {
    for(UILayer layer : this.layers) {
      layer.toggle();
    }
  }
}
