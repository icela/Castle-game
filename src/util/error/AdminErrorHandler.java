package util.error;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * Created by ice1000 on 2016/7/26.
 *
 * @author ice1000
 */
public class AdminErrorHandler {
	private static final Logger logger=Logger.getInstance();
	public static void handleError() {
		JOptionPane.showMessageDialog(
				null,
				"请在随后弹出的对话框中选择 ‘是’！",
				"错误提示",
				JOptionPane.ERROR_MESSAGE
		);
		try {
			File file = new File("\\temp\\uac.bat");
			file.createNewFile();
			// 我给你再次整理了缩进和转义字符，现在在IDEA里面不报错，也就是说语法上完全没问题了。
			// 现在你该检查逻辑上的问题了。我在检查的时候，顺便在所有\n后面换行了。
			// Finished at 2016-07-26 14:45
			String data = "@echo off\n" +
					":: BatchGotAdmin\n" +
					":-------------------------------------\n" +
					"REM --> Check for permissions\n" +
					">nul 2>&1" +
					"\"%SYSTEMROOT%\\system32\\cacls.exe\"" +
					"\"%SYSTEMROOT%\\system32\\config\\system\"\n" +
					"REM --> If error flag set, we do not have admin.\n" +
					"if '%errorlevel%' NEQ '0' (\n" +
					"echo Requesting administrative privileges...\n" +
					"goto UACPrompt\n" +
					")else ( goto gotAdmin )\n" +
					":UACPrompt\n" +
					"echo Set UAC = CreateObject ^ (\"Shell.Application\"^) > \"%temp%\\getadmin.vbs\"\n" +
					"echo UAC.ShellExecute \"%~s0\", \"\", \"\", \"runas\", 1 >> \"%temp%\\getadmin.vbs\"\n" +
					"\"%temp%\\getadmin.vbs\"\n" +
					"exit / B \n" +
					":gotAdmin\n" +
					"if exist \"%temp%\\getadmin.vbs\" (del \"%temp%\\getadmin.vbs\" )\n" +
					"pushd \"%CD%\"\n" +
					"CD / D \"%~dp0\"\n" +
					":--------------------------------------";
			FileWriter fileWriter = new FileWriter(file.getName(), true);
			fileWriter.write(data);
			Process process = Runtime.getRuntime().exec("\\temp\\uac.bat");
			InputStream in = process.getInputStream();
			in.close();
			process.waitFor();
			fileWriter.close();
			file.delete();
		} catch (Exception e1) {
			logger.log(e1);
		}
	}
}
