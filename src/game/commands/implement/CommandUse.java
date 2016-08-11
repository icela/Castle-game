package game.commands.implement;

import data.database.SQLiteDatabase;
import game.Game;
import game.cells.item.Item;
import game.commands.BaseCommand;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath, ice1000
 *         本类作用即使用背包中的物品。
 *         //TODO 未完成。
 */
public class CommandUse implements BaseCommand {

	private final Game game;
	private ArrayList<Item> userItems, allItems;
	private HashMap<Integer, Integer> roomPairs;
	private String cmd;
	private int A, B;

	public CommandUse(Game game) {
		this.game = game;
	}

	private void init() {
		try {
			allItems = SQLiteDatabase.getInstance().getItems();
			roomPairs = SQLiteDatabase.getInstance().getRoomItemPairs();
			userItems = SQLiteDatabase.getInstance().getItems();
			//TODO 一样，记得改成TextDatabase
		} catch (SQLException e) {
			Logger.log(e);
		}
		String[] tmp = cmd.replace("use ", "").split(" on ");
		this.A = Integer.parseInt(tmp[1]);
		this.B = Integer.parseInt(tmp[2]);
	}

	@Override
	public void runCommand(String cmd) {
		this.cmd = cmd;
		init();
		// 这个写的我要死要死的... ...
		if (userItems.isEmpty()) {
			game.echoln("您的背包为空！");
			return;
		}
		userItems.forEach(item -> {
//			玩家所持某个物品的编号
			if (item.getId() != A) {
//				如果命令中给出的物品A的编号与玩家所持所有物品的编号的编号均不相等（未持有这个物品）
				game.echoln("您未持有此反应物！");
			}else if (item.getReactions().isEmpty()) {
				game.echoln("此物品不参与任何反应！");
			}else item.getReactions().forEach(reaction -> {
				if (reaction.getBID() != B) {
					game.echoln("指定的反应物无法与另一反应物反应！");
				}else if (reaction.getAID() == A) roomPairs.forEach((from, to) -> {
					//				如果反应物A的编号与命令中给出的物品A的编号相等
					if (roomPairs.get(game.getCurrentRoom().getId()) != reaction.getBID()) {
						// 如果房间中所有物品编号与命令中给出的物品B的编号均不相等（房间中没有这个物品）
						game.echoln("您所在的房间中没有指定的反应物！");
					} else
						// 好了！！满足所有条件，（终于）可以开始反应了... ...
						userItems.add(allItems.get(Integer.parseInt(reaction.getResult())));
				});
			});
		});
	}
}
