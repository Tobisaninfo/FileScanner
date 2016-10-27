package de.tobias.scanner;

import java.nio.file.Path;

public class FileItem {

	private final Path path;
	private final Type type;
	private final int lines;

	public FileItem(Path path, Type type, int lines) {
		this.path = path;
		this.type = type;
		this.lines = lines;
	}

	public Path getPath() {
		return path;
	}

	public Type getType() {
		return type;
	}

	public int getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return path.toString();
	}
}
