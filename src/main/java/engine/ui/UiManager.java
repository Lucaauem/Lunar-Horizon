package engine.ui;

import java.util.HashMap;

public class UiManager {
	private static UiManager instance;

	private final HashMap<String, UiElement> elements = new HashMap<>();

	private UiManager() {}

	public static UiManager getInstance() {
		if(instance == null) {
			instance = new UiManager();
		}
		return instance;
	}

	public void addElement(String id, UiElement element) {
		elements.put(id, element);
	}

	public UiElement getElement(String id) {
		return elements.get(id);
	}

	public void render() {
		for(UiElement element : elements.values()) {
			if(element.isVisible()) {
				element.render();
			}
		}
	}
}
