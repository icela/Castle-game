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
	private HashMap<String, FuncSrc> commands = new HashMap<>();
	private String[] commandNames;
	private GameMap map;
	private ArrayList<Item> items = new ArrayList<>();
	public Player player;

	private boolean gameEnded = false;

	//    构造方法
	public Game() {
		onCreate();
	}

	protected void onCreate() {
		map = new GameMap();
		createItems();
		commandNames = new String[]{
				"help", "go", "wild",
				"exit", "state", "fight",
				"sleep", "save", "rename",
				"talk", "pack", "home",
				"map", "pick", "use"
		};

		commands.put(commandNames[0], cmd -> {
			for (String s : commandNames)
				echoln(s);
			echoln("有些需要参数的命令请按如下格式输入：\n命令 [参数]\n如：go east");
			echoln("如：rename 冰封");
		});
		commands.put(commandNames[1], this::goRoom);
		commands.put(commandNames[2], cmd -> echoln(map.wildRoom()));
		commands.put(commandNames[3], cmd -> {
			saveData();
			gameEnded = true;
		});
		commands.put(commandNames[4], cmd -> echoln(player.stateToString()));
		commands.put(commandNames[5], cmd -> fight());
		commands.put(commandNames[6], new FuncSleep(this));
		commands.put(commandNames[7], cmd -> saveData());
		commands.put(commandNames[8], cmd -> {
			if (!cmd.equals("")) {
				player.rename(cmd);
				echoln("重命名成功。新名字：" + cmd);
			} else
				echoln("格式错误。请按照\"rename [新名字]\"的格式重命名！");
		});
		commands.put(commandNames[9], cmd -> {
			NPC npc = map.currentRoom.isNPCExists(cmd);
			if (npc != null) {
				echoln(npc.getChat());
			} else
				echoln("你指定的名字不存在。注：Boss要在被打败之后才能对话。");
		});
		commands.put(commandNames[10], cmd -> {
			echoln("背包中物品如下：");
			for (Item item : items)
				echoln(item.toString());
		});
		commands.put(commandNames[11], cmd -> {
			echoln("您发动了与女仆的契约，回到了旅馆。");
			map.currentRoom = map.getHome();
			echoln(map.currentRoom.getPrompt());
		});
		commands.put(commandNames[12], new FuncMap(this));
		commands.put(commandNames[13], new FuncPick(this));
		commands.put(commandNames[14], new FuncUse(this));
	}

	protected void onStart() {
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
		echoln(map.currentRoom.getPrompt());
	}

	@Override
	public boolean handleMessage(String line) {
		String[] words = line.split(" ");
		FuncSrc func = commands.get(words[0]);
		String value2 = "";

		if (words.length > 1)
			value2 = words[1];

//			如果找到了该指令
		if (func != null) {
			func.runCommend(value2);
			if (gameEnded) {
				saveData();
				closeScreen();
				return false;
			}
		} else
			echoln("对不起，输入指令错误！");
		return true;
	}

	private void createItems() {
		items.add(new Item("传送宝石"));
		items.add(new Item("和女仆的契约"));
	}

	/**
	 * 去一个房间
	 */
	public void goRoom(String direction) {
		if (map.goRoom(direction))
			echoln(map.currentRoom.getPrompt());
		else
			echoln("命令格式错误或该出口不存在。");
	}

	/**
	 * 战斗函数
	 */
	public void fight() {
		map.fightBoss(this);
		echoln(map.currentRoom.getPrompt());
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
