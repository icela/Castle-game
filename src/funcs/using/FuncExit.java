package funcs.using;

import castle.Game;
import funcs.FuncSrc;

public class FuncExit extends FuncSrc {

	public FuncExit(Game game) {
		super(game);
	}

	private void bye() {
		isGameEnded = true;
	}

	@Override
	public void runCommend(String cmd) {
		game.saveData();
		bye();
	}

}
