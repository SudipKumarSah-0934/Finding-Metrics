import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LinesOfCode {
	public static void countLOC(String fileName) throws FileNotFoundException, IOException {
		int counter = 0;
		ArrayList<String> lines = new ArrayList<>();
		String filePath = "C:\\\\Users\\\\sudip\\\\eclipse-workspace\\\\MetricsCode\\\\test_datasets\\\\TestCorrecteness\\\\"
				+ fileName + ".txt";
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		boolean block = false;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
			line = line.trim();
			if (line.length() != 0) {
				if (!block) {
					if (line.charAt(0) == '/' && line.charAt(1) == '/')
						continue;
					if (line.charAt(0) == '/' && line.charAt(1) == '*') {
						block = true;
						continue;
					}
					if (line.contains("/*"))
						block = true;
					++counter;
				}
				if (line.charAt(line.length() - 1) == '/' && line.charAt(line.length() - 2) == '*')
					block = false;
			}
		}
		System.out.print("\n---------------------------------------------");
		System.out.println("\n\tLines Of Code:  " + counter);
		System.out.print("---------------------------------------------\n");
		return;
	}
}