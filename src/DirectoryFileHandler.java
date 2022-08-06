import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import metrics.cyclomatic_complexity.CyclomaticComplexity;
import metrics.halstead.HalsteadMetrics;

public class DirectoryFileHandler {
	String dirName;
	static String fileName = null;
	List<String> javaFiles = null;
	List<char[]> halsteadFileRead = null;
	ArrayList<String> cyclomaticFilesRead;

	public DirectoryFileHandler(String dirName, String fileName) {
		super();
		this.dirName = dirName;
		this.fileName = fileName;
		fileFetch(dirName);
	}

	public static char[] ReadFileToCharArray(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString().toCharArray();
	}

	public static List<char[]> ParseFilesInDir(List<String> javaFiles) throws IOException {
		if (javaFiles.isEmpty()) {
			System.out.println("There is no java source code in the provided directory");
			System.exit(0);
		}
		List<char[]> halsteadFileRead = new ArrayList<char[]>();
		for (int i = 0; i < javaFiles.size(); i++) {
			halsteadFileRead.add(ReadFileToCharArray(javaFiles.get(i)));
		}
		return halsteadFileRead;
	}

	public static List<String> retrieveFiles(String directory) {
		List<String> Files = new ArrayList<String>();
		File dir = new File(directory);
		if (!dir.isDirectory()) {
			System.out.println("The provided path is not a valid directory");
			System.exit(1);
		}

		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				Files.addAll(retrieveFiles(file.getAbsolutePath()));
			}
			try {
				if (file.getName().startsWith((fileName))) {
					Files.add(file.getAbsolutePath());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Files;
	}

	public List<String> fileFetch(String dirName2) {
		javaFiles = retrieveFiles(dirName);
		codeFetch(javaFiles);
		return javaFiles;
	}

	public List<char[]> codeFetch(List<String> javaFiles2) {
		System.out.println(javaFiles2);

		try {
			halsteadFileRead = ParseFilesInDir(javaFiles2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return halsteadFileRead;
	}

	public static ArrayList<String> ReadFileToCharArrayCyclo() throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(
					"C:\\Users\\sudip\\eclipse-workspace\\MetricsCode\\test_datasets\\TestCorrecteness\\" + fileName
							+ ".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		int i = 0;
		System.out.print("\n---------------------------------------------");
		System.out.println("\n\tCyclomatic Complexity Metrics");
		System.out.print("---------------------------------------------\n");
		try {
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
				System.out.println(i + ":" + line);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("---------------------------------------------\n");

		return lines;
	}

	public void runMetrics() {
		HalsteadMetrics halstead = new HalsteadMetrics();
		CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
		try {
			halstead.psvm(javaFiles, halsteadFileRead);
			cyclomaticComplexity.calculateCyclomaticComplexity(ReadFileToCharArrayCyclo());
			LinesOfCode.countLOC(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
