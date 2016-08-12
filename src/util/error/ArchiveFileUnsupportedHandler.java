package util.error;

import view.GUI;

import javax.swing.*;

/**
 * Created by Eldath on 2016/8/11 0011.
 * @author Eldath
 */
public class ArchiveFileUnsupportedHandler {
	public static void handleError(ArchiveFileUnsupportedException e) {
		Logger.log(e.getMessage());
		if (e.getLevel().contains("警告"))
			JOptionPane.showMessageDialog(GUI.getMainFrame(),
					e.getMessage(),
					e.getLevel(),
					JOptionPane.WARNING_MESSAGE
			);
		else if (e.getLevel().contains("致命错误")) {
			JOptionPane.showMessageDialog(GUI.getMainFrame(),
					e.getMessage(),
					e.getLevel(),
					JOptionPane.ERROR_MESSAGE
			);
			System.gc();
			System.exit(0);
		}
	}
}
