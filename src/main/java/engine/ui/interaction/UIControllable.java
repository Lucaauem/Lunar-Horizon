package engine.ui.interaction;

public interface UIControllable {
  default void onUp() {}
  default void onDown() {}
  default void onLeft() {}
  default void onRight() {}
  default void onStart() {}
  default void onAction() {}
}
