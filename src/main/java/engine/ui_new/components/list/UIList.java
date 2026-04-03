package engine.ui_new.components.list;

import engine.ui_new.UIElement;
import engine.ui_new.interaction.Scrollable;
import engine.ui_new.text.UIText;
import java.util.ArrayList;

public class UIList extends UIElement implements Scrollable {
  private static final int ELEMENTS_GAP_PX = 4;

  private final ArrayList<UIListElement> content;
  private int scrollOffset = 0;

  public UIList() {
    this.content = new ArrayList<>();
  }

  public void addContent(UIListElement element) {
    element.setParent(this);
    this.content.add(element);
  }

  @Override
  public void render() {
    for (int i=this.scrollOffset; i<this.content.size(); i++) {
      UIListElement element = this.content.get(i);

      element.setOffset(0, -((i - scrollOffset) * (UIText.CHARACTER_HEIGHT + UIList.ELEMENTS_GAP_PX)));

      if(!element.isInsideAnchor()) {
        break;
      }

      element.render();
    }
  }

  @Override
  public void scrollUp() {
    this.scrollOffset--;
  }

  @Override
  public void scrollDown() {
    this.scrollOffset++;
  }
}
