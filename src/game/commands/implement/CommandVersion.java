package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;
import view.GUIConfig;

/**
 * Created by Eldath on 2016/7/27 0027.
 * @author Eldath
 */
public class CommandVersion implements BaseCommand {
	private Game game;

	public CommandVersion(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
		game.echoln("VERSION: " + GUIConfig.VERSION);
		game.echoln("MODEL:");
		game.echoln("OPEN-SOURCE PROJECT BASED ON ProgramLeague OF Github");
		game.echoln("Copyright(©) 2016 ProgramLeague");
	}
}
