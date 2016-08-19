package data.database;

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
	private static ArrayList<Item> userItems, allItems;
	private static HashMap<Integer, Integer> roomPairs;
	private static HashMap<String, String> basic;
	private static TempDatabase instance;

	public TempDatabase() {
		try {
			allItems = SQLiteDatabase.getInstance().getItems();
			roomPairs = TextDatabase.getInstance().getRoomPairs();
			userItems = TextDatabase.getInstance().getUserItems();
			basic = SQLiteDatabase.getInstance().getBasic();
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	public static TempDatabase getInstance() {
		if (instance == null) instance = new TempDatabase();
		return instance;
	}

	public String getBasic(String key) {
		return basic.get(key);
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
		TempDatabase.allItems = allItems;
	}

	public static void setRoomPairs(HashMap<Integer, Integer> roomPairs) {
		TempDatabase.roomPairs = roomPairs;
	}

	public static void setUserItems(ArrayList<Item> userItems) {
		TempDatabase.userItems = userItems;
	}
}
