package engine.controls;

import engine.ui.components.UIMenuGrid;

public class GridController extends Controller {
  private final UIMenuGrid menu;

  public GridController(UIMenuGrid menu) {
    this.menu = menu;
  }

  @Override
  protected void onUp(float dt) {
    this.menu.moveCursorUp();
    needReset = true;
  }

  @Override
  protected void onDown(float dt) {
    this.menu.moveCursorDown();
    needReset = true;
  }

  @Override
  protected void onLeft(float dt) {
    this.menu.moveCursorLeft();
    needReset = true;
  }

  @Override
  protected void onRight(float dt) {
    this.menu.moveCursorRight();
    needReset = true;
  }

  @Override
  protected void onStart(float dt) {

  }

  @Override
  protected void onAction(float dt) {
    this.menu.selectElement();
    needReset = true;
  }
}
