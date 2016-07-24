package funcs.using;

import castle.Game;
import funcs.FuncSrc;

public class FuncWild extends FuncSrc {

	public FuncWild(Game game) {
		super(game);
	}

	@Override
	public void runCommend(String cmd) {
		// 传送
		game.wildRoom();
	}

}
