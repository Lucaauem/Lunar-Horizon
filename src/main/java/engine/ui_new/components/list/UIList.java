package engine.ui_new.components.list;

import engine.ui_new.UIElement;
import engine.ui_new.text.UIText;
import java.util.ArrayList;

public class UIList extends UIElement {
  private static final int ELEMENTS_GAP_PX = 4;

  private final ArrayList<UIListElement> content;

  public UIList() {
    this.content = new ArrayList<>();
  }

  public void addContent(UIListElement element) {
    element.setParent(this);
    this.content.add(element);
  }

  @Override
  public void render() {
    for (int i=0; i<this.content.size(); i++) {
      UIListElement element = this.content.get(i);
      element.setOffset(0, 0, 0, i * (UIText.CHARACTER_HEIGHT + ELEMENTS_GAP_PX));
      element.render();
    }
  }
}
