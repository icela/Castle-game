package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;

import java.io.File;

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
	public void runCommand(String cmd) {
		File archive = new File("save.ice");
		if (archive.delete() || !archive.exists()) game.echoln("存档删除成功。\r\n请重新启动程序。");
		else game.echoln("存档删除失败。");
		game.echoln("");
	}
}
