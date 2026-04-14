package engine.ui.components;

import engine.ui.UIElement;
import engine.ui.components.list.UIListElement;
import engine.ui.interaction.UIControllable;

public class UIMenuGrid extends UIElement implements UIControllable {
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

  private void updateCursor(int dirX, int dirY) {
    this.elements[this.cursorIndexY][this.cursorIndexX].getLabel().setPrefix("");
    this.cursorIndexY = Math.min(Math.max(0, this.cursorIndexY + dirY), this.elements.length - 1);
    this.cursorIndexX = Math.min(Math.max(0, this.cursorIndexX + dirX), this.elements[this.cursorIndexY].length - 1);
    this.elements[this.cursorIndexY][this.cursorIndexX].getLabel().setPrefix(CURSOR_PREFIX);
  }

  @Override
  public void onAction() {
    this.elements[this.cursorIndexY][this.cursorIndexX].click();
  }

  @Override
  public void onRight() {
    this.updateCursor(1, 0);
  }

  @Override
  public void onLeft() {
    this.updateCursor(-1, 0);
  }

  @Override
  public void onDown() {
    this.updateCursor(0, 1);
  }

  @Override
  public void onUp() {
    this.updateCursor(0, -1);
  }

  @Override
  public void renderSelf() {
    for (int i=0; i<this.elements.length; i++) {
      for (int j=0; j<this.elements[i].length; j++) {
        UIListElement element = this.elements[i][j];
        element.setOffset(HORIZONTAL_SPACING_PX * j, -VERTICAL_SPACING_PX * i);
        element.render();
      }
    }
  }
}
