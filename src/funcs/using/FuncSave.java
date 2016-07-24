package funcs.using;

import castle.Game;
import funcs.FuncSrc;

public class FuncSave extends FuncSrc {

	public FuncSave(Game game) {
		super(game);
	}

	@Override
	public void runCommend(String cmd) {
		game.saveData();
	}

}
