package funcs.using;

import castle.Game;
import funcs.FuncSrc;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 显示地图
 * Created by asus1 on 2016/2/1.
 */
public class FuncMap extends FuncSrc{

	public FuncMap(Game game) {
		super(game);
	}

	@Override
	public void DoFunc(String cmd) {
		JFrame frame = new JFrame("地图");
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		Image image = Toolkit.getDefaultToolkit().getImage(
				"." + File.separator + "drawable" + File.separator + "map.png"
		);
		ImageIcon icon = new ImageIcon(image);
		label.setIcon(icon);
		frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		panel.add(label);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
