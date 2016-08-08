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
	private ArrayList<Item> userItems, items;
	private ArrayList<RoomItemPair> pairs;
	private String cmd, A, B;

	public CommandUse(Game game) {
		this.game = game;
	}

	private void onReady() {
		try {
			items = SQLiteDatabase.getInstance().getItems();
			pairs = SQLiteDatabase.getInstance().getRoomItemPairs();
			userItems = SQLiteDatabase.getInstance().getItems();
			//TODO 一样，记得改成TextDatabase
		} catch (SQLException e) {
			Logger.log(e);
		}
		String[] tmp = cmd.replace("use ", "").split(" on ");
		this.A = tmp[1];
		this.B = tmp[2];
	}

	@Override
	public void runCommand(String cmd) {
		this.cmd = cmd;
		onReady();
		// 这个写的我要死要死的... ...
		// 新建一个用户背包中物品的迭代器
		Iterator<Item> playerIter = userItems.iterator();
		Iterator<RoomItemPair> pairIter = pairs.iterator();
		while (playerIter.hasNext()) {
			int itemInt = playerIter.next().getId(); // 玩家所持某个物品的编号
			if (Integer.getInteger(this.A) != itemInt) {
				// 如果命令中给出的物品A的编号与玩家所持所有物品的编号的编号均不相等（未持有这个物品）
				game.echoln("您未持有此反应物！");
				return;
			}
			Iterator<Reaction> reactionIter = playerIter.next().getReaction().iterator();
			if (!reactionIter.hasNext()) {
				game.echoln("此物品不是反应物！");
				return;
			}
			// 新建一个内容为玩家所持某个物品的反应的迭代器
			while (reactionIter.hasNext()) {
				String tempA = reactionIter.next().getA();
				String tempB = reactionIter.next().getB();
				if (Objects.equals(tempA, this.A)) {
					// 如果反应物A的编号与命令中给出的物品A的编号相等
					while (pairIter.hasNext()) {
						if (pairIter.next().item != Integer.getInteger(tempB)) {
							// 如果房间中所有物品编号与命令中给出的物品B的编号均不相等（房间中没有这个物品）
							game.echoln("房间中没有指定的反应物！");
							return;
						}
					}
				}
				// 好了！！满足所有条件，（终于）可以开始反应了... ...
				playerIter.remove();
				userItems.add(items.get(Integer.parseInt(reactionIter.next().getResult())));
			}
		}
	}
}
