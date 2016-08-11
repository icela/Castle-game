package game.map;

import com.sun.istack.internal.Nullable;
import game.Game;
import game.cells.spirit.Boss;
import game.cells.spirit.NPC;
import game.cells.spirit.Player;
import util.error.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

	private Boss boss;
	private int id;
	private final String description;
	private String welcomeWord;
	private HashMap<String, Integer> exits;
	private ArrayList<NPC> NPCs;

	//构造方法
	public Room(int id, String description) {
		this.id = id;
		this.description = description;
		exits = new HashMap<>();
		boss = null;
	}

	//构造方法
	Room(int id, String description, String welcomeWord) {
		this(id, description);
		this.welcomeWord = welcomeWord;
	}

	public Room(
			int id,
			String description,
			@Nullable String welcomeWord,
			@Nullable String BossName,
			int blood,
			int strike,
			int defence,
			int experience,
			@Nullable String dieText) {
		this(id, description, welcomeWord == null ? "欢迎来到这里。" : welcomeWord);
		if (BossName != null) {
			if (dieText != null) boss = new Boss(BossName, blood, strike, defence, experience, dieText);
			else boss = new Boss(BossName, blood, strike, defence, experience);
		} else
			boss = null;

		NPCs = new ArrayList<>();
		exits = new HashMap<>();
//		NPCs.add(boss);
	}

	//返回房间名
	@Override
	public String toString() {
		return description;
	}

	//检查房间名
	public boolean matchName(String name) {
		return description.equals(name);
	}

	//    设置一个出口。
	void setExit(String str, int targetRoomId) {
		exits.put(str, targetRoomId);
	}

	//   显示房间的详情。
	public String getPrompt() {
		StringBuilder sb = new StringBuilder();
		String ifaBoss = "这里安全。";
		sb.append(welcomeWord).append('\n');
		sb.append("您的位置：").append(this.description).append('\n');
		sb.append("出口有: ");
		for (String str : exits.keySet()) sb.append(str).append(' ');
		sb.append('\n');
		if (boss != null) if (boss.getOrNot()) ifaBoss = "冰封".equals(boss.toString()) ?
				"你来到了神秘空间。这里只能通过\\wild传送离开。冰封正坐在这写码呢。"
				: "这里的Boss是" + boss + ",正准备接受你的挑战呢！";
		else ifaBoss = "这里的Boss是" + boss + ",已经被你打败过啦O(∩_∩)O哈哈~";
		sb.append(ifaBoss);
		if (NPCs != null && NPCs.size() > 0) {
			sb.append("这里还有：\n");
			for (NPC npc : NPCs) sb.append(npc.getName());
		}
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	//   使用此类的返回值，赋给原本的Room。
	int showRoomId(String direction) {
		return exits.get(direction);
	}

	//   战斗函数
	Player fightBoss(Player player, Game game) {
		return boss.fight(player, game);
	}

	//    检查Boss是否已经被挑战过
	public boolean bossGetItem() {
		try {
			return boss.getOrNot();
		} catch (NullPointerException e) {
			Logger.log(e);
			return true;
		}
	}

	public void setBossItem(int itemId) {
		boss.item = itemId;
	}

	public int getBossItem() {
		return boss.item;
	}

	void setBossGetItem(boolean get) {
		if (boss != null) boss.setGotItem(get);
	}

	public NPC isNPCExists(String name) {
		for (NPC npc : NPCs) if (name.equals(npc.getName())) return npc;
		return null;
	}

	boolean checkExit(String exit) {
		return exits.containsKey(exit);
	}
}
