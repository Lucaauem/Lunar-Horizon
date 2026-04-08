package engine.ui_new.components;

import engine.controls.InputManager;
import engine.controls.TextboxController;
import engine.ui_new.text.TextLoader;
import engine.ui_new.Anchor;
import engine.ui_new.UIElement;
import engine.ui_new.UIManager;
import engine.ui_new.text.UIText;
import org.joml.Vector2f;
import org.joml.Vector3f;
import java.util.Map;
import java.util.Stack;

public class UITextbox extends UIElement {
  private static final Anchor DEFAULT_ANCHOR = new Anchor(0.20f, 0.05f, 0.80f, 0.25f);
  private static final Vector3f BOX_COLOR = new Vector3f(0.7f, 0.7f, 0.7f);
  private static final Vector3f BORDER_COLOR = new Vector3f(0.6f, 0.6f, 0.6f);
  private static final float BORDER_SIZE = 2.0f;

  private final Stack<String> texts;
  private UIText currentText;
  private Runnable onClose;

  public UITextbox() {
    super();
    this.anchor = UITextbox.DEFAULT_ANCHOR;
    this.texts = new Stack<>();
  }

  public void setTexts(String section, String textId) {
    this.setTexts(new TextLoader(section).loadText(textId));
  }

  public void setTexts(String section, String textId, Map<String, String> variables) {
    this.setTexts(new TextLoader(section).loadTemplateText(textId, variables));
  }

  private void setTexts(String[] texts) {
    this.texts.clear();
    for(int i=texts.length-1; i>=0; i--) {
      this.texts.push(texts[i]);
    }
  }

  public void open() {
    this.setVisibility(true);
    this.next();
    InputManager.getInstance().setController(new TextboxController(this));
  }

  public void close() {
    if (UIManager.getInstance().isAdditionalElement(this)) {
      UIManager.getInstance().removeElement(this);
    } else {
      this.setVisibility(false);
    }

    if(this.onClose != null) {
      this.onClose.run();
    }
  }

  public void setOnClose(Runnable action) {
    this.onClose = action;
  }

  public void next() {
    if (this.currentText != null) {
      this.currentText.setParent(null);
      this.currentText = null;
    }

    if(this.texts.isEmpty()) {
      this.close();
    } else {
      this.currentText = new UIText(this.texts.pop(), this);
    }
  }

  @Override
  public void renderSelf() {
    this.drawRectangle(this.getScreenPosition().sub(new Vector2f(BORDER_SIZE)), this.getScreenSize().add(new Vector2f(BORDER_SIZE * 2)), BORDER_COLOR);
    this.drawRectangle(this.getScreenPosition(), this.getScreenSize(), BOX_COLOR);
    this.currentText.render();
  }
}
