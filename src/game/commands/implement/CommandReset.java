package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;
import util.error.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Eldath on 2016/7/26 0026.
 *
 * @author Eldath
 */
public class CommandReset implements BaseCommand {

	private Game game;

	public CommandReset(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
		File archive = new File(System.getProperty("user.dir") + File.separator + "save.ice");
		if (archive.delete()) {
			game.echoln("存档删除成功。");
			game.echoln("请重新启动程序。");
		} else {
			game.echoln("尝试清空存档... ...");
			//TODO 请勿移去这段！
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(archive, true));
				out.write("");
				out.close();
			} catch (IOException e) {
				game.echoln("存档清空失败。");
				Logger.getInstance().log(e);
			}
		}
		game.echoln("");
	}
}
