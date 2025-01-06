package engine.ui;

import engine.graphics.Texture;
import org.joml.Vector2i;
import org.joml.Vector4f;
import java.util.ArrayList;
import java.util.Arrays;

public class Text{
	private static final Texture FONT_TEXTURE = new Texture("font/default.png");
	private static final ArrayList<Character> SPECIAL_CHARACTERS = new ArrayList<>(Arrays.asList(',', '!', '\'', '&', '.', '"', '?', '-'));
	private static final int CHARACTER_WIDTH = 16;
	private static final int CHARACTER_HEIGHT = 16;

	private final ArrayList<UiElement> characters;
	private final Vector2i position;

	public Text(String text, Vector2i position) {
		this.characters = new ArrayList<>();
		this.position = position;

		for(int i=0; i<text.length(); i++) {
			if(text.charAt(i) == ' ') { continue; }
			this.loadCharacter(i, text.charAt(i));
		}
	}

	private void loadCharacter(int i, char character) {
		Vector4f uv = this.getLetterUv(character);
		Vector2i characterPosition = new Vector2i(this.position.x + (i * CHARACTER_WIDTH), this.position.y);
		UiElement letter = new UiElement(FONT_TEXTURE, new Vector2i(CHARACTER_WIDTH, CHARACTER_HEIGHT), uv, characterPosition);
		letter.toggle();

		this.characters.add(letter);
	}

	// There are 3 special cases when trying to get the UVs of the given character:
	// 1. Digits
	// 2. Normal characters
	// 3. Special characters
	// Different characters of the same case must be in the same 'area' of the texture file.
	private Vector4f getLetterUv(char character) {
		int characterIndex;

		if(Character.isDigit(character)) {
			characterIndex = Character.getNumericValue(character);
		} else if(Character.isLetter(character)) {
			char lowerCharacter = Character.toString(character).toLowerCase().charAt(0);
			// Index should be subtracted by 10 (the numeric value of 'a') to start at 0. It is not neccessary in this case because
			// the font texture contains the digits before the letters (therefore taking up indexes 0 - 9)
			characterIndex = Character.getNumericValue(lowerCharacter);
		} else {
			characterIndex = SPECIAL_CHARACTERS.indexOf(character);
			assert characterIndex != -1;
			characterIndex += 36; // Offset of 36 because the digits and letters should take up the first 36 elements
		}

		int xIndex = characterIndex / 2;
		int yIndex = characterIndex % 2;

		return new Vector4f((float) xIndex / 22, (yIndex + 1) * 0.5f, (float) (xIndex + 1)/22, yIndex * 0.5f);
	}

	public void render() {
		for(UiElement character : characters) {
			character.render();
		}
	}
}
