package game;

import game.cells.Item;
import game.cells.NPC;
import game.cells.Player;
import data.database.TextDatabase;
import game.commands.BaseCommand;
import game.commands.implement.CommandMap;
import game.commands.implement.CommandPick;
import game.commands.implement.CommandSleep;
import game.commands.implement.CommandUse;
import game.map.GameMap;
import util.interfaces.Echoer;
import util.interfaces.MessageHandler;
import util.NameGenerator;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game
		implements MessageHandler, Echoer {
	private HashMap<String, BaseCommand> commands = new HashMap<>();
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
				"game/map", "pick", "use"
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
		commands.put(commandNames[6], new CommandSleep(this));
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
				echoln("指定的名字不存在。注：Boss要在被打败之后才能对话。");
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
		commands.put(commandNames[12], new CommandMap(this));
		commands.put(commandNames[13], new CommandPick(this));
		commands.put(commandNames[14], new CommandUse(this));
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
		echoln("如果您需要任何帮助，请键入 'help' 并回车。\n");
		echo("您的位置：");
		echoln(map.currentRoom.getPrompt());
	}

	@Override
	public boolean handleMessage(String line) {
		String[] words = line.split(" ");
		BaseCommand func = commands.get(words[0]);
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
			echoln("对不起，输入指令有误！");
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
			JOptionPane.showMessageDialog(
					null,
					"保存失败",
					"请在随后弹出的对话框中选择 “是”！",
					JOptionPane.ERROR_MESSAGE
			);
			try {
				//TODO 记得加上 管理员权限获取器。
				File file = new File("\\temp\\uac.bat");
				file.createNewFile();
				// TODO 我给你再次整理了缩进和转义字符，现在在IDEA里面不报错，也就是说语法上完全没问题了。
				// TODO 现在你该检查逻辑上的问题了。我在检查的时候，顺便在所有\n后面换行了。
				String data = ":: BatchGotAdmin\n" +
						":-------------------------------------\n" +
						"REM --> Check for permissions\n" +
						">nul 2>&1\n" +
						"%SYSTEMROOT%\\system32\\cacls.exe\n" +
						"%SYSTEMROOT%\\system32\\config\\system\n" +
						"REM --> If error flag set, we do not have admin.\n" +
						"if '%errorlevel%' NEQ '0' (" + "\n" +
							"echo Requesting administrative privileges...\n" +
							"goto UACPrompt)\n" +
						"else ( goto gotAdmin )\n" +
							":UACPrompt \n" +
							"echo Set UAC = CreateObject ^ (\"Shell.Application\" ^) > \"%temp%\\getadmin.vbs\n" +
							"echo UAC.ShellExecute \"%~s0\", \"\", \"\", \"runas\", 1 >> \"%temp%\\getadmin.vbs\" \"%temp%\\getadmin.vbs\\\n" +
						"exit / B \n" +
						":gotAdmin\n" +
						"if exist \"%temp%\\getadmin.vbs\" (del \"%temp%\\getadmin.vbs\" )" + "\n" +
						"pushd \"%CD%\" " + "\n" +
						"CD / D \"%~dp0\" " + "\n" +
						":--------------------------------------";
				FileWriter fileWriter = new FileWriter(file.getName());
				fileWriter.write(data);
				Process process = Runtime.getRuntime().exec("\\temp\\uac.bat");
				InputStream in = process.getInputStream();
				int c;
				while ((c = in.read()) != -1) {
					// TODO
				}

				in.close();
				process.waitFor();
			} catch (Exception e1) {
				//TODO 异常处理
			}
		}
	}

}
