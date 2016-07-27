package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;

/**
 * Created by Eldath on 2016/7/27 0027.
 */
public class CommandVersion implements BaseCommand {
	private Game game;

	public CommandVersion(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
		game.echoln("");
	}
}
