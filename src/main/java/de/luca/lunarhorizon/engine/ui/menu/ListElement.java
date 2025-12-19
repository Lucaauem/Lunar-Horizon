package de.luca.lunarhorizon.engine.ui.menu;

public class ListElement {
	private final String label;
	private final Runnable action;

	public ListElement(String label, Runnable action) {
		this.label = label;
		this.action = action;
	}

	public String getLabel() {
		return this.label;
	}

	public Runnable getAction() {
		return this.action;
	}
}
