package funcs.using;

import castle.Game;
import funcs.FuncSrc;

public class FuncSleep implements FuncSrc {

	private final Game game;

	public FuncSleep(Game game) {
		this.game = game;
	}

	@Override
	public void runCommend(String cmd) {
//		int bloodMore = Integer.parseInt(cmd);
		if (game.getMap().currentRoom.toString().matches("宾馆|卧室")) {
			if (!game.getMap().currentRoom.isBossGetItem()) {
				game.echo("女仆顺从地送你进入梦乡。睡觉中");
				for (int i = 0; i < 8; i++) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException ignored) {
					}
				}
				game.echo("\n已经睡觉，体力");
				if (game.player.treat())
					game.echoln("恢复至120.");
				else
					game.echoln("超过120不用恢复的~");
			} else
				game.echoln("睡觉需要女仆服侍，然而她看起来不大愿意啊。。。");
		} else
			game.echoln("只有宾馆或卧室能睡觉。");
	}

}
