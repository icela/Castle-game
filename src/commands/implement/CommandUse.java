package commands.implement;

import game.Game;
import commands.BaseCommand;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即使用背包中的物品。
 *         //TODO 未完成。
 */
public class CommandUse implements BaseCommand {

	private Game game;

	public CommandUse(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
	}
}
