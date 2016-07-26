package view;

import game.Game;
import util.interfaces.Clearable;
import util.interfaces.Echoer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Stack;

/**
 * 视图
 * Created by asus1 on 2016/1/31.
 */
public class GUI extends Game
		implements Echoer, Clearable {

	private final JTextField textField;
	public final JTextArea textArea;
	public final JFrame frame;
	private final JScrollPane scrollPane;
	private final JScrollBar scrollBar;
	private final Stack<String> inputList;

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;
//	private static int INPUT_WIDTH = 500;

	public GUI() {
		frame = new JFrame(GUIInfo.GUI_FORM_TITLE);
		inputList = new Stack<>();
		textField = new JTextField();
		textField.registerKeyboardAction(
				e -> {
					handleMessage(textField.getText());
					inputList.push(textField.getText());
					textField.setText("");
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED
		);
		textField.registerKeyboardAction(
				e -> {
					if (!inputList.empty()) {
						textField.setText(inputList.peek());
						inputList.pop();
					}
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),
				JComponent.WHEN_FOCUSED
		);
		textField.registerKeyboardAction(
				e -> textField.setText(""),
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),
				JComponent.WHEN_FOCUSED
		);
		textArea = new JTextArea();
		Font font = loadFont(System.getProperty("user.dir") + "/lib/MSYHMONO.ttf",
				GUIInfo.FONT_SIZE);
		textArea.setFont(font);
		textField.setFont(font);
		textArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				echoln("别点这里，点下面。");
				textField.requestFocus(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				textField.requestFocus(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
//				echoln("对，放开就好。");
				textField.requestFocus(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				textField.requestFocus(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				textField.requestFocus(true);
			}
		});

//		Intellij IDEA 标准配色
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
		scrollBar = scrollPane.getVerticalScrollBar();
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setResizable(GUIInfo.RESIZABLE);
		frame.setLocation(FRAME_WIDTH / 8, FRAME_HEIGHT / 8);
		frame.setVisible(true);
		textField.requestFocus(true);
	}

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
		}
	}

	public static void main(String[] args) {
		GUI game = new GUI();
		game.onStart();
	}

	@Override
	public void echo(String words) {
		textArea.append(words);
		int i = textArea.getText().length();
		int MAX_LENGTH = 10000;
		if (i > MAX_LENGTH) {
			textArea.setText(textArea.getText().substring(
					i - MAX_LENGTH, i
			));
		}
		// 滚动到最底下
		scrollBar.setValue(scrollBar.getMaximum() - 20);
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

	@Override
	public void clearScreen() {
		textArea.setText("");
		echoln("屏幕已清空。");
	}
}
