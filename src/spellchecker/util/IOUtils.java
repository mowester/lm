package spellchecker.util;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IOUtils {
	public static String URI_SCHEME_FILE = "file://";

	public static void writeFile(String filePath, String content) {
		System.out.println("Saving into file"+filePath);
		Path path = validateFilePath(filePath);
		try {
			Files.write(path, content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Can't write the file " + path);
		}

	}

	// TODO: add support for different URI
	public static List<String> readFileAllLines(String filePath) {

		Path path = validateFilePath(filePath);

		List<String> data = null;
		try {
			data = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Can't read the file " + path);
		}
		return data;
	}

	/**
	 * for a network location: file://hostname/path/to/the%20file.txt
	 * 
	 * for a local file, the hostname is omitted, but the slash is not
	 * file:///c:/path/to/the%20file.txt
	 */
	private static Path validateFilePath(String filePath) {
		Path fullPath;
		if (filePath != null && filePath.startsWith(URI_SCHEME_FILE)) {
			fullPath = Paths.get(URI.create(filePath));
		} else {
			// C:\projects\spell_check_test\dictionary.txt
			fullPath = Paths.get(filePath).toAbsolutePath();
		}
		System.out.println(fullPath);

		boolean isReadable = Files.isReadable(fullPath);
		if (!isReadable) {
			throw new IllegalArgumentException("Can't read the file " + filePath);
		}
		return fullPath;
	}

}
