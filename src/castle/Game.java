package castle;

import cells.Item;
import cells.NPC;
import cells.Player;
import database.TextDatabase;
import funcs.FuncSrc;
import funcs.using.*;
import map.GameMap;
import util.Echoer;
import util.MessageHandler;
import util.NameGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game
		implements MessageHandler, Echoer {
	private HashMap<String, FuncSrc> funcs = new HashMap<>();
	private String[] funcsString;
	private GameMap map;
	private ArrayList<Item> theItems = new ArrayList<>();
	private Player player;

	private boolean gameEnded = false;

	//    构造方法
	public Game() {
//		onCreate();
	}

	protected void onCreate() {
		map = new GameMap();
		createItems();
		funcsString = new String[]{
				"help", "go", "wild",
				"exit", "state", "fight",
				"sleep", "save", "rename",
				"talk", "pack", "home",
				"map", "pick", "use"
		};

		funcs.put(funcsString[0], cmd -> {
			for (String s : funcsString)
				echoln(s);
			echoln("有些需要参数的命令请按如下格式输入：\n命令 [参数]\n如：go east");
			echoln("如：rename 冰封");
		});
		funcs.put(funcsString[1], this::goRoom);
		funcs.put(funcsString[2], cmd -> wildRoom());
		funcs.put(funcsString[3], cmd -> {
			saveData();
			gameEnded = true;
		});
		funcs.put(funcsString[4], cmd -> echoln(getPlayer().stateToString()));
		funcs.put(funcsString[5], cmd -> fight());
		funcs.put(funcsString[6], new FuncSleep(this));
		funcs.put(funcsString[7], cmd -> saveData());
		funcs.put(funcsString[8], cmd -> {
			if (!cmd.equals("")) {
				getPlayer().rename(cmd);
				echoln("重命名成功。新名字：" + cmd);
			} else
				echoln("格式错误。请按照\"rename [新名字]\"的格式重命名！");
		});
		funcs.put(funcsString[9], cmd -> {
			NPC npc = getMap().getCurrentRoom().isNPCExists(cmd);
			if (npc != null) {
				echoln(npc.getChat());
			} else
				echoln("你指定的名字不存在。注：Boss要在被打败之后才能对话。");
		});
		funcs.put(funcsString[10], cmd -> {
			echoln("背包中物品如下：");
			for (Item item : getTheItems())
				echoln(item.toString());
		});
		funcs.put(funcsString[11], cmd -> {
			echoln("您发动了与女仆的契约，回到了旅馆。");
			getMap().setCurrentRoom(getMap().getHome());
			echoln(getMap().getCurrentRoom().getPrompt());
		});
		funcs.put(funcsString[12], new FuncMap(this));
		funcs.put(funcsString[13], new FuncPick(this));
		funcs.put(funcsString[14], new FuncUse(this));
	}

	protected void onStart() {
		onCreate();
		echoln("欢迎来到Castle Game！");
		echoln("这是一个超复古的CUI游戏。");
		echoln("最新版本和源代码请见https://github.com/ProgramLeague/Castle-game");
		echoln("Kotlin版本与旧版本请见https://github.com/ice1000/Castle-game");
		echoln("敬请期待OL版本https://github.com/ProgramLeague/Castle-Online");
//		太羞耻了！！
//		echoln("不过在经过了冰封的改造后，你会觉得这个很有意思。");
		if (!TextDatabase.isFileExists()) {
			echoln("您可以稍后使用\"rename [新名字]\"命令来更改自己的名字。");
			new NameGenerator();
			player = new Player(
					NameGenerator.generate(),
					200,
					10,
					5
			);
			saveData();
		} else {
			player = new Player(
					null,
					-1,
					-1,
					-1
			);
			TextDatabase.getInstance().loadState(player);
			TextDatabase.getInstance().loadMap(map, "宾馆");
			echoln("检测到存档。");
		}

		echoln("您好，" + player);
		echoln("如果您需要任何帮助，请输入 'help' 。\n");
		echo("现在");
		echoln(map.getCurrentRoom().getPrompt());
	}

	@Override
	public boolean handleMessage(String line) {
		String[] words = line.split(" ");
		FuncSrc func = funcs.get(words[0]);
		String value2 = "";

		if (words.length > 1)
			value2 = words[1];

//			如果找到了该指令
		if (func != null) {
			func.runCommend(value2);
			if (gameEnded) {
//					退出指令特殊处理
				saveData();
				echoln("退出游戏，再见！");//TODO 这行根本看不到
//				System.exit(0);
				closeScreen();
				return false;
			}
		} else
			echoln("对不起，输入指令错误！");
		return true;
	}

	public String[] getFuncs() {
		return funcsString;
	}

	private void createItems() {
		theItems.add(new Item("传送宝石"));
		theItems.add(new Item("和女仆的契约"));
	}

	public ArrayList<Item> getTheItems() {
		return theItems;
	}

	/**
	 * 去一个房间
	 */
	public void goRoom(String direction) {
		if (map.goRoom(direction)) {
		    /*
	        //TODO 这里存在一个bug
            //TODO 不管使用哪种延迟方式，延迟都是自按下回车开始，而不是自显示完“正在执行”后开始。
            //TODO 可能与事件触发的机制有关，晚些时候再看看。。。
            echoln("====================");
            echoln("       正在执行      ");
            echoln("====================");
            echoln("");
            coder.Delay.delay(5000);
             */
			echoln(map.getCurrentRoom().getPrompt());
		} else
			echoln("命令格式错误或该出口不存在。");

	}

	/**
	 * 随机传送
	 */
	public void wildRoom() {
		echoln(map.wildRoom());
	}

	/**
	 * 战斗函数
	 */
	public void fight() {
		map.fightBoss(this);
		echoln(map.getCurrentRoom().getPrompt());
	}

	public void setPlayer(Player player) {
//    	减血赋值给原来的
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public GameMap getMap() {
		return map;
	}

	public void saveData() {
		try {
			TextDatabase.getInstance().saveMapAndState(map, player);
			echoln("保存成功。");
		} catch (IOException e) {
			echoln("保存失败，请检查是否有管理员权限！");
		}
	}

}
