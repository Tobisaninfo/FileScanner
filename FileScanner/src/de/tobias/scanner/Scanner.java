package de.tobias.scanner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

	public static List<FileItem> scan(Path folder, Filter<? super Path> filter) throws IOException {
		List<FileItem> items = new ArrayList<>();
		DirectoryStream<Path> stream = Files.newDirectoryStream(folder, filter);

		for (Path file : stream) {
			if (Files.isRegularFile(file)) {
				Type type = null;

				List<String> readAllLines = Files.readAllLines(file);
				for (String line : readAllLines) {
					// Java
					if (line.contains("public class") || line.contains("public abstract class") || line.contains("public final class")) {
						type = Type.CLASS;
					} else if (line.contains("public interface")) {
						type = Type.INTERFACE;
					} else if (line.contains("public enum")) {
						type = Type.ENUM;
					} else if (line.contains("public @interface")) {
						type = Type.ANNOTATION;
					}
					
					// Scala
					if ((line.contains("class") || line.contains("object")) && line.contains("{")) {
						type = Type.CLASS;
					} else if ((line.contains("interface") || line.contains("trait")) && line.contains("{")) {
						type = Type.INTERFACE;
					} else if (line.contains("enum") && line.contains("{")) {
						type = Type.ENUM;
					} else if (line.contains("@interface")) {
						type = Type.ANNOTATION;
					}
					
					if (type != null)
						break;
				}

				int lines = readAllLines.size();
				FileItem item = new FileItem(file, type, lines);
				items.add(item);
			} else {
				items.addAll(scan(file, filter));
			}
		}

		stream.close();
		return items;
	}
}
