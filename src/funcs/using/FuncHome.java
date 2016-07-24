package funcs.using;

import castle.Game;
import funcs.FuncSrc;

/**
 * 回城堡
 * Created by asus1 on 2016/2/1.
 */
public class FuncHome extends FuncSrc {

	public FuncHome(Game game) {
		super(game);
	}

	@Override
	public void runCommend(String cmd) {
		game.echoln("您发动了与女仆的契约，回到了旅馆。");
		game.getMap().setCurrentRoom(game.getMap().getHome());
		game.echoln(game.getMap().getCurrentRoom().getPrompt());
	}
}
