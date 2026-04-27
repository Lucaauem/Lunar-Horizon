package engine.objects.components.interaction;

import engine.ui.UIManager;
import engine.ui.components.UITextbox;

public class DialogueInteraction implements Interactable {
  private final String textSource;
  private final String text;
  private Runnable onTextboxClose = () -> {};

  public DialogueInteraction(String textSource, String text) {
    this.textSource = textSource;
    this.text = text;
  }

  public void setOnTextboxClose(Runnable action) {
    this.onTextboxClose = action;
  }

  @Override
  public void onInteract() {
    UITextbox textbox = new UITextbox();
    textbox.setTexts(this.textSource, this.text);
    textbox.setOnClose(this.onTextboxClose);
    UIManager.getInstance().addElement(textbox);
    textbox.open();
  }
}
