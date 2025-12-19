package de.luca.lunarhorizon.engine.graphics.renderer.shader;

import org.joml.Matrix4f;
import de.luca.lunarhorizon.util.FileHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDetachShader;

public class Shader {
	private final String source;
	private final HashMap<String, Integer> uniformLocationCache = new HashMap<>();
	private final int rendererId;

	public Shader(String filepath) {
		this.source = filepath;
		this.rendererId = this.create();
	}

	public void bind() {
		glUseProgram(this.rendererId);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void setUniform1i(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}

	public void setUniform4f(String name, float v0, float v1, float v2, float v3) {
		glUniform4f(getUniformLocation(name), v0, v1, v2, v3);
	}

	public void setUniformMat4f(String name, Matrix4f mat) {
		float[] matrix = {
				mat.m00(), mat.m01(), mat.m02(), mat.m03(),
				mat.m10(), mat.m11(), mat.m12(), mat.m13(),
				mat.m20(), mat.m21(), mat.m22(), mat.m23(),
				mat.m30(), mat.m31(), mat.m32(), mat.m33()
		};
		glUniformMatrix4fv(getUniformLocation(name), false, matrix);
	}

	private int getUniformLocation(String name) {
		if(this.uniformLocationCache.containsKey(name)) {
			return this.uniformLocationCache.get(name);
		}

		int location = glGetUniformLocation(this.rendererId, name);
		if(location == -1) {
			System.err.println("Warning: uniform '" + name + "' doesn't exist");
		}
		this.uniformLocationCache.put(name, location);
		return location;
	}

	private int create() {
		String[] source = this.parse();

		int program = glCreateProgram();
		int vs = this.compile(GL_VERTEX_SHADER, source[0]);
		int fs = this.compile(GL_FRAGMENT_SHADER, source[1]);

		glAttachShader(program, vs);
		glAttachShader(program, fs);
		glLinkProgram(program);
		glValidateProgram(program);

		glDetachShader(program, vs);
		glDetachShader(program, fs);

		return program;
	}

	private int compile(int type, String source) {
		int id = glCreateShader(type);
		glShaderSource(id, source);
		glCompileShader(id);

		int result = glGetShaderi(id, GL_COMPILE_STATUS);
		if(result == GL_FALSE){
			int length = glGetShaderi(id, GL_INFO_LOG_LENGTH);
			String msg = glGetShaderInfoLog(id, length);

			System.out.println("FAILED TO COMPILE SHADER");
			System.err.println(msg);

			glDeleteShader(id);
			return 0;
		}

		return id;
	}

	private String[] parse() {
		String[] lines = FileHandler.readFileLines(this.source);
		ShaderType type = ShaderType.NONE;
		List<String> vertexSource = new ArrayList<>();
		List<String> fragmentSource = new ArrayList<>();

		for(String line : lines){
			if(line.contains("#shader")){
				if(line.contains("vertex")){
					type = ShaderType.VERTEX;
				}else if(line.contains("fragment")){
					type = ShaderType.FRAGMENT;
				}
			}else {
				if(type.equals(ShaderType.VERTEX)){
					vertexSource.add(line);
				} else if(type.equals(ShaderType.FRAGMENT)){
					fragmentSource.add(line);
				}
			}
		}

		return new String[]{
				String.join("\n", vertexSource.toArray(new String[0])),
				String.join("\n", fragmentSource.toArray(new String[0]))
		};
	}
}
