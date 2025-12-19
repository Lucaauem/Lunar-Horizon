package de.luca.lunarhorizon.engine.ui.menu;

import de.luca.lunarhorizon.engine.Game;
import de.luca.lunarhorizon.engine.controls.Controller;
import de.luca.lunarhorizon.engine.controls.TextboxController;
import de.luca.lunarhorizon.engine.ui.text.Text;
import de.luca.lunarhorizon.engine.ui.text.TextLoader;
import org.joml.Vector2i;
import java.util.Map;
import java.util.Stack;

public class UiTextbox {
	public static final Vector2i DEFAULT_POSITION = new Vector2i(10, 10);

	private final Stack<String> texts;
	private final Vector2i position;
	private Text currentText;
	private boolean open = false;
	private Controller currentController;
	private Runnable onClose;

	public UiTextbox() {
		this.texts = new Stack<>();
		this.position = DEFAULT_POSITION;
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

	public void setTexts(String section, String textId) {
		String[] texts = new TextLoader(section).loadText(textId);
		this.texts.clear();
		for(int i=texts.length-1; i>=0; i--) {
			this.texts.push(texts[i]);
		}
		this.next();
	}

	public void setTexts(String section, String textId, Map<String, String> variables) {
		String[] texts = new TextLoader(section).loadTemplateText(textId, variables);
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
