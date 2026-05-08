package engine.objects.components.interaction.interactions;

import engine.ui.UIManager;
import engine.ui.components.UITextbox;

public class DialogueInteraction extends Interaction {
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
    textbox.setOnClose(this::onInteractionEnd);
    UIManager.getInstance().addElement(textbox);
    textbox.open();
  }
}
