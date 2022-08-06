import java.io.IOException;
import java.util.Scanner;

public class Main {
	static String DirName;
	static String fileName;

	public static void main(String[] args) throws IOException {

		DirName = null;
		fileName = null;
		Scanner user_input = new Scanner(System.in);
		System.out.print("Enter Directory Name: ");
		DirName = user_input.next();
		System.out.print("Enter Filename: ");
		fileName = user_input.next();
		user_input.close();
		System.out.println();
		DirectoryFileHandler directoryFileHandler = new DirectoryFileHandler(DirName, fileName);
		directoryFileHandler.runMetrics();

	}
}