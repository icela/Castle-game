package funcs.using;

import castle.Game;
import funcs.FuncSrc;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即使用背包中的物品。
 *         //TODO 未完成。
 */
public class FuncUse implements FuncSrc {

	private Game game;

	public FuncUse(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
	}
}
