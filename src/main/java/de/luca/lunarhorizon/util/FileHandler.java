package de.luca.lunarhorizon.util;

import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileHandler {
	public static JSONObject readJSON(String path) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(path)));
			return new JSONObject(content);
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	public static String[] readFileLines(String fileName) {
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			List<String> lines = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				lines.add(line);
				line = br.readLine();
			}

			return lines.toArray(new String[0]);
		} catch (Exception e){
			System.out.println("ERROR READING FILE: " + fileName);
			return new String[0];
		}
	}

	public static String[] getFilesInDir(String directory, boolean showExtensions) {
		try {
			File[] files = new File(directory).listFiles();
			assert files != null;
			String[] fileNames = new String[files.length];

			for(int i=0; i<files.length; i++) {
				fileNames[i] = showExtensions ? files[i].getName() : files[i].getName().replaceFirst("[.][^.]+$", "");
			}

			return fileNames;
		} catch (Exception e){
			System.out.println("ERROR READING SCENES");
			return new String[0];
		}
	}

	public static String[][] readStringArray2D(int numRows, int numCols, String path) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String[][] data = new String[numRows][numCols];
			int lineCounter = 0;

			String line = br.readLine();
			while(line != null) {
				String[] tileIds = line.split(" ");

				System.arraycopy(tileIds, 0, data[lineCounter], 0, tileIds.length);

				lineCounter++;
				line = br.readLine();
			}

			return data;
		} catch (Exception e){
			System.out.println("ERROR READING FILE: " + path);
			return new String[0][0];
		}
	}

	public static void writeToFile(String path, String content) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);
			writer.close();
		} catch (Exception e){
			System.out.println("ERROR WRITING FILE: " + path);
		}
	}

	public static void createFile(String path) {
		try {
			File file = new File(path);
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("ERROR CREATING FILE: " + path);
		}
	}

	public static byte[] readBinary(String path) {
		try(FileInputStream fis = new FileInputStream(path)) {
			return fis.readAllBytes();
		} catch (Exception e) {
			return new byte[0];
		}
	}
}
