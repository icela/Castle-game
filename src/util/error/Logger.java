package util.error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ice1000
 *         Created by ice1000 on 2016/7/26.
 */
public class Logger {

	private static Logger logger;
	private File file;

	public Logger() throws IOException {
		file = new File("error.log");
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public static Logger getInstance() {
		if (logger == null) try {
			logger = new Logger();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logger;
	}

	public void log(String log) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.append(log);
		writer.flush();
		writer.close();
	}

	public void log(Exception e) throws IOException {
		FileWriter writer = new FileWriter(file);
		for (StackTraceElement element : e.getStackTrace())
			writer.append(element.toString()).append('\n');
		writer.flush();
		writer.close();
	}
}
