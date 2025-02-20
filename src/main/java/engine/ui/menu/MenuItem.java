package engine.ui.menu;

public class MenuItem {
	private final String label;
	private final Runnable action;

	public MenuItem(String label, Runnable action) {
		this.label = label;
		this.action = action;
	}

	public void action() {
		action.run();
	}

	public String getLabel() {
		return label;
	}
}
