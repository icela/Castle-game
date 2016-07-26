package util.error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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

	public void log(String log) {
		try {
			new FileWriter(file)
					.append(LocalDateTime.now().toString())
					.append('\n')
					.append(log)
					.append('\n')
					.close();
		} catch (IOException ignored) {
		}
	}

	public void log(Exception e) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.append(LocalDateTime.now().toString()).append('\n');
			for (StackTraceElement element : e.getStackTrace())
				writer.append(element.toString()).append('\n');
			writer.flush();
			writer.close();
		} catch (IOException ignored) {
		}
	}
}
