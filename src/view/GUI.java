package view;

import game.Game;
import util.error.Logger;
import util.interfaces.Echoer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

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
	private JScrollBar scrollBar;
	private Stack<String> inputList;
	private Stack<String> anotherInputList;

	/**
	 * 我知道你绝对不会想仔细研究这段极长的构造方法的，
	 * 没错要的就是这种效果
	 *
	 * 嗯嗯对没错~ ~
	 */
	public GUI() {
		frame = new JFrame(GUIConfig.GUI_FORM_TITLE);
		inputList = new Stack<>();
		anotherInputList = new Stack<>();
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
						anotherInputList.push(inputList.peek());
						textField.setText(inputList.peek());
						inputList.pop();
					}
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),
				JComponent.WHEN_FOCUSED
		);
		textField.registerKeyboardAction(
				e -> {
					if (!anotherInputList.empty()) {
						textField.setText(anotherInputList.peek());
						inputList.push(anotherInputList.peek());
						anotherInputList.pop();
					} else {
						textField.setText("");
					}
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),
				JComponent.WHEN_FOCUSED
		);
		textArea = new JTextArea();
		Font font = loadFont(System.getProperty("user.dir") + "/lib/MSYHMONO.ttf",
				GUIConfig.FONT_SIZE);
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

		try {
			frame.setIconImage(ImageIO.read(new File(
					"res" + File.separator + "drawable" + File.separator + "ic_launcher.png"
			)));
		} catch (IOException e) {
			Logger.getInstance().log(e);
		}
		frame.setSize(
				GUIConfig.FRAME_WIDTH,
				GUIConfig.FRAME_HEIGHT
		);
//		绝对布局
//		frame.setLayout(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(textField, BorderLayout.SOUTH);
		scrollPane = new JScrollPane(textArea);
		scrollBar = scrollPane.getVerticalScrollBar();
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setResizable(GUIConfig.RESIZABLE);
		frame.setLocation(
				GUIConfig.FRAME_WIDTH / 8,
				GUIConfig.FRAME_HEIGHT / 8
		);
		frame.setVisible(true);
		textField.requestFocus(true);
	}

	/**
	 * @param fontFileName 外部字体名
	 * @param fontSize     第二个是字体大小
	 */
	private static Font loadFont(String fontFileName, float fontSize) {
		try {
			File file = new File(fontFileName);
			FileInputStream font = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, font);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			font.close();
			return dynamicFontPt;
		} catch (Exception e) {
			Logger.getInstance().log(e);
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
		this.scrollPane.getViewport().setViewPosition(
				new Point(0, this.textArea.getLineCount() * height));
	}

	@Override
	public void echoln(String words) {
		echo(words + "\n");
	}

	@Override
	public void closeScreen() {
		System.gc();
		frame.dispose();
		System.exit(0);
	}

	@Override
	public void clearScreen() {
		System.gc();
		textArea.setText("");
		echoln("屏幕已清空。");
	}
}
