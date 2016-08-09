package game.commands.implement;

import data.database.SQLiteDatabase;
import data.database.TextDatabase;
import game.Game;
import game.cells.item.Item;
import game.cells.spirit.Reaction;
import game.commands.BaseCommand;
import game.map.RoomItemPair;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即使用背包中的物品。
 *         //TODO 未完成。
 */
public class CommandUse implements BaseCommand {

	private final Game game;
	private ArrayList<Item> userItems, allItems;
	private HashMap<Integer, Integer> roomPairs;
	private String cmd;
	int A, B;

	public CommandUse(Game game) {
		this.game = game;
	}

	private void onReady() {
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
		onReady();
		// 这个写的我要死要死的... ...
		// 新建一个用户背包中物品的迭代器
		Iterator<Item> playerIter = userItems.iterator();
		if (!playerIter.hasNext()) {
			game.echoln("您的背包为空！");
			return;
		}
		while (playerIter.hasNext()) {
			int itemInt = playerIter.next().getId(); // 玩家所持某个物品的编号
			if (this.A != itemInt) {
				// 如果命令中给出的物品A的编号与玩家所持所有物品的编号的编号均不相等（未持有这个物品）
				game.echoln("您未持有此反应物！");
				return;
			}
			Iterator<Reaction> reactionIter = playerIter.next().getReaction().iterator();
			if (!reactionIter.hasNext()) {
				game.echoln("此物品不参与任何反应！");
				return;
			}
			// 新建一个内容为玩家所持某个物品的反应的迭代器
			while (reactionIter.hasNext()) {
				if (reactionIter.next().getBID() != this.B) {
					game.echoln("指定的反应物无法与另一反应物反应！");
					return;
				}
				if (reactionIter.next().getAID() == this.A) {
					// 如果反应物A的编号与命令中给出的物品A的编号相等
					for (int i = 0; i <= roomPairs.size(); i++) {
						if (roomPairs.get(game.getCurrentRoom().getId()) != reactionIter.next().getBID()) {
							// 如果房间中所有物品编号与命令中给出的物品B的编号均不相等（房间中没有这个物品）
							game.echoln("您所在的房间中没有指定的反应物！");
							return;
						}
					}
				}
				// 好了！！满足所有条件，（终于）可以开始反应了... ...
				playerIter.remove();
				userItems.add(allItems.get(Integer.parseInt(reactionIter.next().getResult())));
			}
		}
	}
}
