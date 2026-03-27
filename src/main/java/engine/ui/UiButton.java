package engine.ui;

import engine.ui.text.Text;
import org.joml.Vector2i;

public class UiButton extends Clickable {
	private Text text;
	private final Runnable onClick;

	public UiButton(String text, Vector2i position, Runnable onClick) {
		this.text = new Text(text, position);
		this.onClick = onClick;
	}

	public UiButton changeText(String newText) {
		return new UiButton(newText, this.text.getPosition(), this.onClick);
	}

	public String getText() {
		return this.text.getText();
	}

	public void render() {
		this.text.render();
	}

	@Override
	public void click() {
		this.onClick.run();
	}
}
