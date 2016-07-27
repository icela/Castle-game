package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即拾起周边的物体。
 *         //TODO 未完成
 */
public class CommandPick implements BaseCommand {

	private final Game game;

	public CommandPick(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {

	}
}
