package view;

import castle.Game;

import java.util.Scanner;

/**
 * 这个是针对没有GUI的OS的CUI版
 * Created by asus1 on 2016/1/31.
 */
public class CUI extends Game {

	private CUI() {
		super();
	}

	/**
	 * 当游戏输出东西的时候，调用这个方法并传入需要输出的字符串。
	 * @param words 需要输出的字符串。
	 */
	@Override
	public void echo(String words) {
		System.out.print(words);
	}

	/**
	 * 当游戏输出东西并换行的时候，调用这个方法并传入需要输出的字符串。
	 * @param words 需要输出的字符串。
	 * @see CUI#echo(String)
	 */
	@Override
	public void echoln(String words) {
		echo(words + '\n');
	}

	/**
	 * 游戏退出时调用
	 */
	@Override
	public void closeScreen() {
		System.exit(0);
	}

	/**
	 * 游戏运行。
	 * HandleMessage是Game类实现的一个方法，你传入用户输入的指令，然后它会就处理这个指令，并给与反馈。
	 */
	private void onResume() {
		String line;
		boolean loop = true;
		Scanner in = new Scanner(System.in);
		while (loop) {
			echoln("");
			line = in.nextLine();
			loop = HandleMessage(line);
		}
		in.close();
	}

	/**
	 * 谁要是问我这个是干嘛的，给我滚去面壁
	 */
	public static void main(String[] args) {
		CUI game = new CUI();
		game.onStart();
		game.onResume();
	}
}
