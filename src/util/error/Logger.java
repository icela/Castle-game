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

    private static final String template = "log" + File.separator + "error%d.log";
    private static FileWriter writer;
    private static File file;

    private Logger() {
    } //防止类实例化。

    private static class LoggerLoader {
        private static final Logger instance = new Logger();
    }

    public static Logger getInstance() {
        return LoggerLoader.instance;
    }

    //不知道为啥，Sun说这是单例的标准写法。。。

    public static void log(String log) {
        init();
        try {
            writer
                    .append(LocalDateTime.now().toString())
                    .append('\n')
                    .append(log)
                    .append('\n')
                    .close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public static void log(Exception e) {
        init();
        try {
            writer.append(LocalDateTime.now().toString()).append('\n');
            for (StackTraceElement element : e.getStackTrace())
                writer.append(element.toString()).append('\n');
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private static void init() {
        try {
            File path=new File("log");
            if (!path.exists())
                path.mkdir();
            //历史遗留问题。。【滑稽滑稽滑稽】
            for (int i = 0; ; i++) {
                file = new File(String.format(template, i));
                if (!file.exists()) {
                    file.createNewFile();
                    break;
                }
            }
            writer = new FileWriter(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
