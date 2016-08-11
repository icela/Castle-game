package game.commands.implement;

import data.database.TempDatabase;
import game.Game;
import game.cells.item.Item;
import game.commands.BaseCommand;
import util.error.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eldath on 2016/7/24 0024.
 *
 * @author Eldath
 *         本类作用即拾起周边的物体。
 */
public class CommandPick implements BaseCommand {

	private final Game game;
	private ArrayList<Item> userItems, allItems;
	private HashMap<Integer, Integer> roomPairs;
	private int needID;

	public CommandPick(Game game) {
		this.game = game;
	}

	private void onReady(String cmd) {
		allItems = TempDatabase.getInstance().getAllItems();
		roomPairs = TempDatabase.getInstance().getRoomPairs();
		userItems = TempDatabase.getInstance().getUserItems();
		this.needID = Integer.parseInt(cmd.split("pick ")[1]);
	}

	@Override
	public void runCommand(String cmd) {
		onReady(cmd);
		for (int i = 0; i <= roomPairs.size(); i++)
			if (roomPairs.get(game.getCurrentRoom().getId()) != this.needID) {
				// 如果房间中所有物品编号与命令中给出的物品B的编号均不相等（房间中没有这个物品）
				game.echoln("您所在的房间中没有指定的可拾起物品！");
				return;
			}
		roomPairs.remove(needID);
		userItems.add(allItems.get(this.needID));
	}
}
