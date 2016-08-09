package game.commands.implement;

import data.database.SQLiteDatabase;
import game.Game;
import game.cells.item.Item;
import game.commands.BaseCommand;
import game.map.RoomItemPair;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即拾起周边的物体。
 *         //TODO 未完成
 */
public class CommandPick implements BaseCommand {

	private final Game game;
	private ArrayList<Item> userItems, allItems;
	private HashMap<Integer, Integer> roomPairs;
	int cmd;

	public CommandPick(Game game) {
		this.game = game;
	}

	private void onReady(String cmd) {
		try {
			allItems = SQLiteDatabase.getInstance().getItems();
			roomPairs = SQLiteDatabase.getInstance().getRoomItemPairs();
			userItems = SQLiteDatabase.getInstance().getItems();
			//TODO 一样，记得改成TextDatabase
		} catch (SQLException e) {
			Logger.log(e);
		}
		this.cmd = Integer.parseInt(cmd.split("pick ")[1]);
	}

	@Override
	public void runCommand(String cmd) {

	}
}
