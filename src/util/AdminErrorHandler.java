package util;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * Created by asus1 on 2016/7/26.
 */
public class AdminErrorHandler {
	public static void handleError() {
		JOptionPane.showMessageDialog(
				null,
				"保存失败",
				"请在随后弹出的对话框中选择 “是”！",
				JOptionPane.ERROR_MESSAGE
		);
		try {
			//TODO 记得加上 管理员权限获取器。
			File file = new File("\\temp\\uac.bat");
			file.createNewFile();
			// TODO 我给你再次整理了缩进和转义字符，现在在IDEA里面不报错，也就是说语法上完全没问题了。
			// TODO 现在你该检查逻辑上的问题了。我在检查的时候，顺便在所有\n后面换行了。
			String data = ":: BatchGotAdmin\n" +
					":-------------------------------------\n" +
					"REM --> Check for permissions\n" +
					">nul 2>&1\n" +
					"%SYSTEMROOT%\\system32\\cacls.exe\n" +
					"%SYSTEMROOT%\\system32\\config\\system\n" +
					"REM --> If error flag set, we do not have admin.\n" +
					"if '%errorlevel%' NEQ '0' (" + "\n" +
					"echo Requesting administrative privileges...\n" +
					"goto UACPrompt)\n" +
					"else ( goto gotAdmin )\n" +
					":UACPrompt \n" +
					"echo Set UAC = CreateObject ^ (\"Shell.Application\" ^) > \"%temp%\\getadmin.vbs\n" +
					"echo UAC.ShellExecute \"%~s0\", \"\", \"\", \"runas\", 1 >> \"%temp%\\getadmin.vbs\" \"%temp%\\getadmin.vbs\\\n" +
					"exit / B \n" +
					":gotAdmin\n" +
					"if exist \"%temp%\\getadmin.vbs\" (del \"%temp%\\getadmin.vbs\" )" + "\n" +
					"pushd \"%CD%\" " + "\n" +
					"CD / D \"%~dp0\" " + "\n" +
					":--------------------------------------";
			FileWriter fileWriter = new FileWriter(file.getName());
			fileWriter.write(data);
			Process process = Runtime.getRuntime().exec("\\temp\\uac.bat");
			InputStream in = process.getInputStream();
			int c;
			while ((c = in.read()) != -1) {
				// TODO
			}

			in.close();
			process.waitFor();
		} catch (Exception e1) {
			//TODO 异常处理
		}
	}
}
