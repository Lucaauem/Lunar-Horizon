package engine.ui.menu;

import engine.Game;
import engine.controls.Controller;
import engine.controls.TextboxController;
import engine.ui.Text;
import org.joml.Vector2i;
import java.util.Stack;

public class UiTextbox {
	private final Stack<String> texts;
	private final Vector2i position;
	private Text currentText;
	private boolean open = false;
	private Controller currentController;
	private Runnable onClose;

	public UiTextbox(String[] texts, Vector2i position) {
		this.texts = new Stack<>();
		this.position = position;

		this.setTexts(texts);
	}

	public void open() {
		this.currentController = Game.getController();
		Game.setController(new TextboxController(this));
		this.open = true;
		this.onClose = null;
	}

	public void open(Runnable onClose) {
		this.open();
		this.onClose = onClose;
	}

	public void close() {
		this.open = false;
		Game.setController(currentController);

		if(this.onClose != null) {
			this.onClose.run();
		}
	}

	public boolean isOpen() {
		return this.open;
	}

	public void setTexts(String[] texts) {
		this.texts.clear();
		for(int i=texts.length-1; i>=0; i--) {
			this.texts.push(texts[i]);
		}
		this.next();
	}

	public void next() {
		if(this.texts.isEmpty()) {
			this.close();
			return;
		}
		this.currentText = new Text(this.texts.pop(), this.position);
	}

	public void render() {
		if(this.currentText != null) {
			this.currentText.render();
		}
	}
}
