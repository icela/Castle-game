package game;

import data.database.TempDatabase;
import data.database.TextDatabase;
import game.cells.item.Item;
import game.cells.item.ItemData;
import game.cells.spirit.NPC;
import game.cells.spirit.Player;
import game.commands.BaseCommand;
import game.commands.implement.*;
import game.map.Map;
import game.map.Room;
import util.error.AdminErrorHandler;
import util.error.Logger;
import util.interfaces.Clearable;
import util.interfaces.Echoer;
import util.interfaces.MessageHandler;
import view.CUI;
import view.GUI;
import view.GUIConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game implements MessageHandler, Echoer, Clearable {

	private final HashMap<String, BaseCommand> commands = new HashMap<>();
	public ArrayList<Item> items;
	private String[] commandNames;
	private Map map;
	public Player player;
	private String specialMessage;

	private boolean gameEnded = false;

	//    构造方法
	public Game() {
		onCreate();
	}

	protected void onCreate() {
		map = new Map();
		items = TempDatabase.getInstance().getUserItems();
		commandNames = new String[]{
				"help", "go", "exit",
				"fight", "sleep", "save",
				"rename", "talk", "pack",
				"home", "map", "pick",
				"use", "reset", "version"
		};

		int index = -1;
		commands.put(commandNames[++index], cmd -> {
			for (String s : commandNames)
				echoln(s);
			echoln(TempDatabase.getInstance().getBasic("FORMAT_EXPLAIN"));
		});
		commands.put(commandNames[++index], this::goRoom);
		commands.put(commandNames[++index], cmd -> {
			saveData();
			gameEnded = true;
		});
		commands.put(commandNames[++index], cmd -> fight());
		commands.put(commandNames[++index], new CommandSleep(this));
		commands.put(commandNames[++index], cmd -> saveData());
		commands.put(commandNames[++index], cmd -> {
			if (!cmd.equals("")) {
				player.rename(cmd);
				echoln(TempDatabase.getInstance().getBasic("RENAME_SUCCESS") + cmd);
			} else echoln("格式错误。请按照\"rename [新名字]\"的格式重命名！");
		});
		commands.put(commandNames[++index], new CommandTalk(this));
		commands.put(commandNames[++index], cmd -> {
			if (!items.isEmpty()) {
				echoln("背包中物品如下：");
				items.stream().filter(item -> item.num > 0).forEach(item ->
						echoln('[' + item.num + ']' + ' ' + item.getName())
				);
			} else echoln("背包中没有物品。");
			echoln("");
		});
		commands.put(commandNames[++index], cmd -> {
			if (items.get(ItemData.MAID_RIGHT).num > 0) {
				echoln("您发动了与女仆的契约，回到了旅馆。");
				map.currentRoom = map.getHome();
				echoln(map.currentRoom.getPrompt());
			} else echoln("然而你并没有女仆的契约，不能传送。");
		});
		commands.put(commandNames[++index], new CommandMap(this));
		commands.put(commandNames[++index], new CommandPick(this));
		commands.put(commandNames[++index], new CommandUse(this));
		commands.put(commandNames[++index], new CommandReset(this));
		commands.put(commandNames[++index], new CommandVersion(this));
	}

	protected void onStart() {
		echoln("欢迎来到Castle Game！");
		echoln("这是一个超复古的CUI游戏。");
		echoln("最新版本和源代码请见https://github.com/ProgramLeague/Castle-game");
		echoln("Kotlin版本与旧版本请见https://github.com/ice1000/Castle-game");
		echoln("敬请期待OL版本https://github.com/ProgramLeague/Castle-Online");
		if (this instanceof CUI) {
			echoln("您现在是以CUI模式开启游戏，极少数功能将不可用。");
			GUIConfig.MODEL = GUIConfig.MODEL_VALUE.CUI;
		} else if (this instanceof GUI) {
			echoln("您现在是以GUI模式进行游戏，所有功能均可用。");
			GUIConfig.MODEL = GUIConfig.MODEL_VALUE.GUI;
		} else echoln("系统无法检测您用的什么模式，玩游戏也得遵守基本法啊！");

//		太羞耻了！！
//		echoln("不过在经过了冰封的改造后，你会觉得这个很有意思。");
		try {
			player = TextDatabase.getInstance().loadPlayer();
			map = TextDatabase.getInstance().loadMap("宾馆");
		} catch (IOException e1) {
			Logger.log(e1);
		}
		if (TextDatabase.fileExists())
			echoln("检测到存档。");
		else {
			echoln("您是第一次开始游戏。");
			echoln("您可以稍后使用\"rename [新名字]\"命令来更改自己的名字。");
			saveData();
		}
		echoln("");
		echoln("您好，" + player);
		if (GUIConfig.DEBUG)
			echoln("当前处于Debug模式，存档文件的Base64加密将不会启用。");
		echoln("如果您需要任何帮助，请键入 'help' 并回车。\n");
		echoln(map.currentRoom.getPrompt());
		echoln("");
	}

	public String getSpecialMessage(){
		return specialMessage;
	}

	@Override
	public boolean handleMessage(String line) {
		if(!line.contains(" ")) {
			line = specialMessage;
			return false;
		}
		String[] words = line.split(" ");
		BaseCommand func = commands.get(words[0]);
		String value2 = "";

		if (words.length > 1) value2 = words[1];

//			如果找到了该指令
		if (func != null) {
			func.runCommand(value2);
			if (gameEnded) {
				saveData();
				closeScreen();
				return false;
			}
		} else echoln("对不起，输入指令有误！");
		return true;
	}

	/**
	 * 去一个房间
	 */
	public void goRoom(String direction) {
		if (map.goRoom(direction)) echoln(map.currentRoom.getPrompt());
		else echoln("命令格式错误或该出口不存在。");
		echoln("");
	}

	/**
	 * 战斗函数
	 * now bugless
	 */
	public void fight() {
//		打之前是否持有物品（这尼玛都什么命名啊）
		boolean a = map.currentRoom.bossGetItem();
		map.fightBoss(this);
//		前面半句反正都要执行，保证一定会挑战，不会被短路干扰，但是要挑战成功才会触发这个
//		之前是没有，但是打赢了之后就有了
		if (!map.currentRoom.bossGetItem() && a) items.get(map.currentRoom.getBossItem()).num++;
		echoln(map.currentRoom.getPrompt());
		echoln("");
	}

	public Room getCurrentRoom() {
		return map.currentRoom;
	}

	public void saveData() {
		try {
			TextDatabase.getInstance().saveFile(map, player);
		} catch (IOException e) {
			Logger.log(e);
			AdminErrorHandler.handleError();
		}
	}
}
