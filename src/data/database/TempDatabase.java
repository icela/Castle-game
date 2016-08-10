package data.database;

import game.Game;
import game.cells.item.Item;
import util.error.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Eldath
 *         Created by Eldath on 2016/8/9 0009.
 */
public class TempDatabase {
	private ArrayList<Item> userItems, allItems;
	private HashMap<Integer, Integer> roomPairs;
	private static TempDatabase instance;

	public TempDatabase() {
		try {
			allItems = SQLiteDatabase.getInstance().getItems();
			roomPairs = TextDatabase.getInstance().getRoomPairs();
			userItems = TextDatabase.getInstance().getUserItems();
		} catch (SQLException e) {
			Logger.log(e);
		}
	}

	public static TempDatabase getInstance() {
		if (instance == null)
			instance = new TempDatabase();
		return instance;
	}

	public ArrayList<Item> getAllItems() {
		return allItems;
	}

	public ArrayList<Item> getUserItems() {
		return userItems;
	}

	public HashMap<Integer, Integer> getRoomPairs() {
		return roomPairs;
	}

	public void setAllItems(ArrayList<Item> allItems) {
		this.allItems = allItems;
	}

	public void setRoomPairs(HashMap<Integer, Integer> roomPairs) {
		this.roomPairs = roomPairs;
	}

	public void setUserItems(ArrayList<Item> userItems) {
		this.userItems = userItems;
	}
}
