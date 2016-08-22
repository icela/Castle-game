package game.commands.implement;

import data.database.SQLiteDatabase;
import game.Game;
import game.cells.spirit.Chat;
import game.cells.spirit.NPC;
import game.commands.BaseCommand;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eldath on 2016/8/19 0019.
 *
 * @author Eldath
 */
public class CommandTalk implements BaseCommand {
	private final Game game;
	private ArrayList<NPC> currentNPCs;
	private ArrayList<Chat> currentNPCChat;
	private NPC currentNPC;
	private String npcID;

	public CommandTalk(Game game) {
		this.game = game;
	}

	private void init(String cmd) {
		this.npcID = cmd.split("talk ")[1];
		try {
			this.currentNPCs = SQLiteDatabase.getInstance().getCurrentNPC(game.getCurrentRoom().getId());
		} catch (SQLException e) {
			Logger.log(e);
		}
	}

	@Override
	public void runCommand(String cmd) {
		init(cmd);
		// 不行我得理理思路。。。
		// 首先需要判断玩家所处的这个房间里是不是有玩家想对话的NPC
		// 玩家所处房间的编号中的所有NPC的编号与请求的NPC编号是否相等
		if (currentNPCs.isEmpty())
			game.echoln("房间内没有NPC！");
		Iterator<NPC> iter = currentNPCs.iterator();
		boolean isEqual = false;
		while (iter.hasNext()) {
			if (iter.next().getNpcID() == Integer.parseInt(this.npcID)) {
				currentNPC = iter.next();
				isEqual = true;
				break;
			}
		}
		if (isEqual) {
			Chat currentChat;
			game.echoln("您现在正与" + currentNPC.getName() + "对话");
			currentNPCChat = currentNPC.getChat();
			Iterator<Chat> chatsIter = currentNPCChat.iterator();
			while (chatsIter.hasNext()) {
				currentChat = chatsIter.next();
				game.echoln(currentChat.getText());
				String sequel = currentChat.getSequel();
				if (sequel.contains("TALK^"))
					game.echoln(currentNPCChat.get(Integer.parseInt(sequel.split("TALK^ ")[1])).getText());
				else if(sequel.contains("CHOOSE^"))
					game.echoln("请输入您的选择：");
				//TODO 先弄出来一个Choice的spirit，然后再艹这里。
				else if (sequel.contains("END_OF_TALK"))
					game.echoln("您与" + currentNPC.getName() + "的对话已经结束。");
			}
		}
	}
}
