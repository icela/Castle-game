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

    private static final Logger logger=new Logger();
    private static final String template = "log" + File.separator + "error%d.log";
    private static File file;
    private static FileWriter writer;
    //TODO 待我吃完饭回来解决这个bug... ...

    public Logger() {
        try {
            for (int i = 0; ; i++) {
                file = new File(String.format(template, i));
                if (!file.exists()) {
                    file.createNewFile();
                    break;
                }
                writer = new FileWriter(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class LoggerLoader {
        private static final Logger instance = new Logger();
    }

    public static Logger getInstance() {
        return LoggerLoader.instance;
    }

    //不知道为啥，Sun说这是单例的标准写法。。。

    public static void log(String log) {
        try {
            writer
                    .append(LocalDateTime.now().toString())
                    .append('\n')
                    .append(log)
                    .append('\n')
                    .close();
        } catch (IOException ignored) {
        }
    }

    public static void log(Exception e) {
        try {
            writer.append(LocalDateTime.now().toString()).append('\n');
            for (StackTraceElement element : e.getStackTrace())
                writer.append(element.toString()).append('\n');
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
    }
}
