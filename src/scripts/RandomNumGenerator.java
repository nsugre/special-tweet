package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RandomNumGenerator {

	public static void main(String[] args) {
		List<String> lines = new ArrayList<>();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		File file = null;
		try {
			String path = System.getProperty("user.dir") + "\\data\\" + "text.txt";
			file = new File(path);
			fileReader = new FileReader(file, StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() > 5) {
					lines.add(line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(lines);
	}

}
