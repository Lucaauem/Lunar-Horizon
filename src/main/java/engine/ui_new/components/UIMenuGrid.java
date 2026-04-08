package engine.ui_new.components;

import engine.ui_new.UIElement;
import engine.ui_new.components.list.UIListElement;

public class UIMenuGrid extends UIElement {
  private static final String CURSOR_PREFIX = "-";
  private static final int VERTICAL_SPACING_PX = 25;
  private static final int HORIZONTAL_SPACING_PX = 80;

  private final UIListElement[][] elements;
  private int cursorIndexX = 0;
  private int cursorIndexY = 0;

  public UIMenuGrid(int rows, int cols) {
    this.elements = new UIListElement[rows][cols];
  }

  // Fills in order: row -> column
  public void setElements(UIListElement[] elements) {
    int index = 0;

    for (UIListElement[] row : this.elements) {
      for (int i=0; (i<row.length) && (index<elements.length); i++) {
        UIListElement element = elements[index++];
        element.setParent(this);
        row[i] = element;
      }
    }

    this.elements[0][0].getLabel().setPrefix(CURSOR_PREFIX);
  }

  public void setElement(int indexX, int indexY, UIListElement newElement) {
    this.elements[indexY][indexX] = newElement;
  }

  public void selectElement() {
    this.elements[this.cursorIndexY][this.cursorIndexX].click();
  }

  private void updateCursor(int dirX, int dirY) {
    this.elements[this.cursorIndexY][this.cursorIndexX].getLabel().setPrefix("");
    this.cursorIndexY = Math.min(Math.max(0, this.cursorIndexY + dirY), this.elements.length - 1);
    this.cursorIndexX = Math.min(Math.max(0, this.cursorIndexX + dirX), this.elements[this.cursorIndexY].length - 1);
    this.elements[this.cursorIndexY][this.cursorIndexX].getLabel().setPrefix(CURSOR_PREFIX);
  }

  public void moveCursorLeft() {
    this.updateCursor(-1, 0);
  }

  public void moveCursorRight() {
    this.updateCursor(1, 0);
  }

  public void moveCursorUp() {
    this.updateCursor(0, -1);
  }

  public void moveCursorDown() {
    this.updateCursor(0, 1);
  }

  @Override
  public void render() {
    for (int i=0; i<this.elements.length; i++) {
      for (int j=0; j<this.elements[i].length; j++) {
        UIListElement element = this.elements[i][j];
        element.setOffset(HORIZONTAL_SPACING_PX * j, -VERTICAL_SPACING_PX * i);
        element.render();
      }
    }
  }
}
