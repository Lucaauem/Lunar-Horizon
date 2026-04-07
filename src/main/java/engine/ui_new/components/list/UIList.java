package engine.ui_new.components.list;

import engine.ui_new.UIElement;
import engine.ui_new.interaction.Scrollable;
import engine.ui_new.text.UIText;
import java.util.ArrayList;

public class UIList extends UIElement implements Scrollable {
  private static final int ELEMENTS_GAP_PX = 4;
  private static final String CURSOR_PREFIX = "-";

  private final ArrayList<UIListElement> content;
  private int scrollOffset = 0;
  private int cursorIndex = 0;
  private int pageSize = 0;

  public UIList() {
    this.content = new ArrayList<>();
  }

  public void addContent(UIListElement element) {
    element.setParent(this);
    this.content.add(element);

    if (this.content.size() == 1) {
      updateCursor(0);
    }
  }

  public void moveCursorUp() {
    this.updateCursor(-1);
  }

  public void moveCursorDown() {
    this.updateCursor(1);
  }

  private void updateCursor(int dir) {
    if (this.cursorIndex + dir == this.content.size()) {
      return;
    }

    this.content.get(this.cursorIndex).getLabel().setPrefix("");

    if (this.cursorIndex + dir < this.scrollOffset) {
      this.scrollUp();
    }

    this.cursorIndex = Math.max(this.cursorIndex += dir, 0);

    if ((dir == 1) && (this.cursorIndex >= this.pageSize)) {
      this.scrollDown();
    }

    this.content.get(this.cursorIndex).getLabel().setPrefix(UIList.CURSOR_PREFIX);
  }

  public void selectElement() {
    if (!this.content.isEmpty()) {
      this.content.get(this.cursorIndex).click();
    }
  }

  @Override
  public void render() {
    this.pageSize = 0;
    for (int i=this.scrollOffset; i<this.content.size(); i++) {
      UIListElement element = this.content.get(i);
      element.setOffset(0, -((i - scrollOffset) * (UIText.CHARACTER_HEIGHT + UIList.ELEMENTS_GAP_PX)));

      if(!element.isInsideAnchor()) {
        break;
      }

      this.pageSize++;
      element.render();
    }
  }

  @Override
  public void scrollUp() {
    this.scrollOffset = Math.max(this.scrollOffset -= 1, 0);
  }

  @Override
  public void scrollDown() {
    this.scrollOffset++;
  }
}
