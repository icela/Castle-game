package view;

import castle.Game;
import util.Echoer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * 视图
 * Created by asus1 on 2016/1/31.
 */
public class GUI extends Game
		implements Echoer {

	private JTextField textField;
	private JTextArea textArea;
	public JFrame frame;
	private JScrollPane scrollPane;

	private static int FRAME_WIDTH = 500;
	private static int FRAME_HEIGHT = 500;
//	private static int INPUT_WIDTH = 500;

	//第一个参数是外部字体名，第二个是字体大小
	private static Font loadFont(String fontFileName, float fontSize) {
		try {
			File file = new File(fontFileName);
			FileInputStream font = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, font);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			font.close();
			return dynamicFontPt;
		} catch (Exception e) {
			return new java.awt.Font("宋体", Font.PLAIN, 14);
			//TODO 记得添加异常处理。
		}
	}

	public GUI() {
		frame = new JFrame(GUIPublicData.getTitle());
		String note = "在这里输入指令";
		textField = new JTextField(note);
		textField.registerKeyboardAction(e -> {
					handleMessage(textField.getText());
					textField.setText("");
				},
				KeyStroke.getKeyStroke(
						KeyEvent.VK_ENTER,
						0,
						true
				),
				JComponent.WHEN_FOCUSED
		);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				textField.setText(note);
			}
		});
		textArea = new JTextArea();
		Font font = loadFont(System.getProperty("user.dir") + "/lib/MSYHMONO.ttf", 13f);
		textArea.setFont(font);
		textField.setFont(font);
		textArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				echoln("别点这里，点下面。");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
//				echoln("对，放开就好。");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

//		IntelliJ IDEA 标准配色
		textArea.setBackground(new Color(43, 43, 43));
		textArea.setForeground(new Color(169, 183, 198));
		textArea.setEditable(false);

		frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
				"." + File.separator + "src" + File.separator + "drawable" + File.separator + "ic_launcher.png"
		));
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
//		绝对布局
//		frame.setLayout(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(textField, BorderLayout.SOUTH);
		scrollPane = new JScrollPane(textArea);
//		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setLocation(FRAME_WIDTH / 8, FRAME_HEIGHT / 8);
		frame.setVisible(true);
		textField.requestFocus(true);
	}

	public static void main(String[] args) {
		GUI game = new GUI();
		game.onStart();
	}

	@Override
	public void echo(String words) {
//		System.out.print(words);
		textArea.append(words);
		int i = textArea.getText().length();
		int MAX_LENGTH = 10000;
		if (i > MAX_LENGTH) {
			textArea.setText(textArea.getText().substring(
					i - MAX_LENGTH, i
			));
		}
//		scrollBar.setValue(scrollBar.getMaximum() - 20);
		// 滚动到最底下
		int height = 10;
		Point p = new Point();
		p.setLocation(0, this.textArea.getLineCount() * height);
		this.scrollPane.getViewport().setViewPosition(p);
	}

	@Override
	public void echoln(String words) {
		echo(words + "\n");
	}

	@Override
	public void closeScreen() {
		frame.dispose();
	}

}
