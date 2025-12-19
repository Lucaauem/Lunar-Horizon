package de.luca.lunarhorizon.engine.ui.text;

import org.json.JSONArray;
import org.json.JSONObject;
import de.luca.lunarhorizon.util.FileHandler;
import java.util.Map;

public class TextLoader {
	private static final String TEXT_PATH = "src/main/assets/texts/";

	private final JSONObject textData;

	public TextLoader(String path) {
		this.textData = FileHandler.readJSON(TEXT_PATH + path + ".json");
	}

	public String[] loadText(String textId) {
		JSONArray texts = this.textData.getJSONArray(textId);
		String[] text = new String[texts.length()];

		for(int i=0; i<texts.length(); i++) {
			text[i] = texts.getString(i);
		}

		return text;
	}

	public String[] loadTemplateText(String textId, Map<String,String> values) {
		String[] texts = this.loadText(textId);

		for(int i=0; i<texts.length; i++) {
			for (Map.Entry<String, String> entry : values.entrySet()) {
				texts[i] = texts[i].replace("{{ " + entry.getKey() + " }}", entry.getValue());
			}
		}

		return texts;
	}
}
