package engine.graphics;

import engine.Game;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {
	public static final String TEXTURES_PATH_BASE = "src/main/assets/textures/";
	private static final Map<String, Texture> CACHED_TEXTURES = new HashMap<>();

	private final int id;
	private final int height;
	private final int width;

	public Texture(String path) {
		if(CACHED_TEXTURES.containsKey(path)) {
			Texture cachedTexture = CACHED_TEXTURES.get(path);
			this.id = cachedTexture.id;
			this.width = cachedTexture.width;
			this.height = cachedTexture.height;
			return;
		}

		String fullPath = TEXTURES_PATH_BASE + path;

		// Load texture
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = stbi_load(fullPath, width, height, channels, 0);

		assert image != null : "ERROR: (Texture) Could not load image '" + path + "'";
		this.id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.id);

		// Set texture parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
				0, GL_RGBA, GL_UNSIGNED_BYTE, image);

		stbi_image_free(image);
		this.width = width.get(0);
		this.height = height.get(0);

		CACHED_TEXTURES.put(path, this);
	}

	public void bind() {
		this.bind(0);
	}


	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, this.id);
		Game.shader.setUniform1i("u_Texture", slot);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
