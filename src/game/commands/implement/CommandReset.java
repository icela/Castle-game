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
	public void runCommend(String cmd) {
		File archive = new File(System.getProperty("user.dir") + File.separator + "save.ice");
		if (archive.delete())
			game.echoln("存档删除成功。");
		else
			game.echoln("存档删除失败。");
	}
}
