package engine.ui;

public class UILayer {
    private final UIElement[] elements;
    private final int zValue;
    private boolean visible = false;

    public UILayer(int z, UIElement[] elements) {
        this.elements = elements;
        this.zValue = z;
    }

    public void toggle() {
      this.visible = !this.visible;
    }

    public boolean isVisible() {
      return this.visible;
    }

    public void render() {
      for(UIElement element: this.elements) {
        element.render();
      }
    }

    public int getZ() {
      return this.zValue;
    }
}
