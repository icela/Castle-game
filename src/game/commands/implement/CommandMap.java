package game.commands.implement;

import data.database.TempDatabase;
import game.commands.BaseCommand;
import util.interfaces.Echoer;
import view.CUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 显示地图
 * Created by ice1000 on 2016/2/1.
 */
public class CommandMap implements BaseCommand {

	private boolean haveMap = false;
	private final Echoer echoer;

	public CommandMap(Echoer echoer) {
		this.echoer = echoer;
	}

	public void setHaveMap(boolean flag) {
		this.haveMap = flag;
	}

	public boolean isHaveMap() {
		return haveMap;
	}

	@Override
	public void runCommand(String cmd) {
		if (echoer instanceof CUI) {
//			TODO 字符画
			echoer.echoln(TempDatabase.getInstance().getBasic("MODEL_UNSUPPORTED"));
			echoer.echoln("");
			return;
		}
		if (!haveMap) {
			echoer.echoln(TempDatabase.getInstance().getBasic("NO_MAP"));
			echoer.echoln("");
			return;
		}
		JFrame frame = new JFrame(TempDatabase.getInstance().getBasic("MAP"));
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				"res" + File.separator + "drawable" + File.separator + "map.png"
		));
		label.setIcon(icon);
		frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		panel.add(label);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		echoer.echoln("");
	}
}
