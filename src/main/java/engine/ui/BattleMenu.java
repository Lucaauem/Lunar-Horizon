package engine.ui;

// TODO Could maybe be used as normal menu
public class BattleMenu {
	private static final char INDICATOR = '-';

	private UiButton[] buttons;
	private UiButton currentButton;
	private int currentIndex = 0;
	private boolean isVisible = false;

	public void setButtons(UiButton[] buttons) {
		this.buttons = buttons;
		this.moveCursor(0);
	}

	public void moveCursor(int ammount) {
		if(this.currentIndex + ammount < 0 || this.currentIndex + ammount >= this.buttons.length) {
			return;
		}

		this.currentIndex = (this.currentIndex + ammount) % this.buttons.length;

		UiButton originalButton = this.buttons[this.currentIndex];
		this.currentButton = originalButton.changeText(INDICATOR + originalButton.getText());
	}

	public void clickButton() {
		if(this.currentIndex != 0) {
			this.buttons[this.currentIndex].click();
		}
	}

	public void setVisibility(boolean visible) {
		this.isVisible = visible;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void render() {
		for(int i=0; i<this.buttons.length; i++) {
			if(i == this.currentIndex) {
				this.currentButton.render();
			} else {
				this.buttons[i].render();
			}
		}
	}
}
