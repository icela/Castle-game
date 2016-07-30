package util.error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author ice1000 Eldath
 *         Created by ice1000 on 2016/7/26.
 */
public class Logger {

    private static final String template = "log" + File.separator + "error%d.log";
    private static FileWriter writer;
    private static File file;

    private Logger() {} //防止类实例化。

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
            Logger.log(ignored);
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
            Logger.log(ignored);
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
