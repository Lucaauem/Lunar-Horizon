package engine.objects.components.interaction;

import engine.input.InputManager;
import engine.ui.UIManager;
import engine.ui.components.UITextbox;

public class DialogueInteraction implements Interactable {
  private final String textSource;
  private final String text;

  public DialogueInteraction(String textSource, String text) {
    this.textSource = textSource;
    this.text = text;
  }

  @Override
  public void onInteract() {
    UITextbox textbox = new UITextbox();
    textbox.setTexts(this.textSource, this.text);
    textbox.setOnClose(() -> InputManager.getInstance().setToPreviousController());
    UIManager.getInstance().addElement(textbox);
    textbox.open();
  }
}
