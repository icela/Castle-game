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
	private static final String template = "log" + File.separator + "error%d.log";

	private File file;

	public Logger() {
	}

	public static Logger getInstance() {
		if (logger == null)
			logger = new Logger();
		return logger;
	}

	public void log(String log) {
		try {
			init();
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
			init();
			FileWriter writer = new FileWriter(file);
			writer.append(LocalDateTime.now().toString()).append('\n');
			for (StackTraceElement element : e.getStackTrace())
				writer.append(element.toString()).append('\n');
			writer.flush();
			writer.close();
		} catch (IOException ignored) {
		}
	}

	private void init() throws IOException {
		String fileName;
		for (int i = 0; ; i++) {
			fileName = String.format(template, i);
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
				break;
			}
		}
	}
}
