package engine.ui_new;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class UIManager {
  private static UIManager instance;

  private final HashMap<String, UILayer> layers = new HashMap<>();

  private UIManager() {}

  public static UIManager getInstance() {
    if(UIManager.instance == null) {
      UIManager.instance = new UIManager();
    }
    return UIManager.instance;
  }

  public void addLayer(String id, UILayer layer) {
    if (this.layers.containsKey(id)) {
      throw new IllegalArgumentException("UIElement id already exists!");
    }
    this.layers.put(id, layer);
  }

  public UILayer getLayer(String id) {
    return this.layers.get(id);
  }

  public void render() {
    UILayer[] sortedLayers = this.layers.values().toArray(new UILayer[0]);
    Arrays.sort(sortedLayers, Comparator.comparingInt(UILayer::getZ));

    for(UILayer layer: sortedLayers) {
      if(layer.isVisible()) {
        layer.render();
      }
    }
  }
}
