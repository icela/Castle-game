package game.commands.implement;

import data.database.SQLiteDatabase;
import data.database.TextDatabase;
import game.Game;
import game.cells.item.Item;
import game.cells.spirit.Reaction;
import game.commands.BaseCommand;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即使用背包中的物品。
 *         //TODO 未完成。
 */
public class CommandUse implements BaseCommand {

	private final Game game;
	private ArrayList<Item> items;
	private String cmd, A, B;

	public CommandUse(Game game) {
		this.game = game;
	}

	private void onReady() {
		try {
			items = SQLiteDatabase.getInstance().getItems();
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
		for (Item item : items) {
			if (item.getId() != Integer.getInteger(this.A)) { // 如果背包中没有这个物品=
				game.echoln("您的背包中还没有此物品呢~~ 快去得到吧！");
				return;
			}
		}
	}
}
