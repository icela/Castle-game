package data.database;

import game.cells.item.Item;
import game.cells.spirit.Chat;
import game.cells.spirit.Choice;
import game.cells.spirit.NPC;
import game.cells.spirit.Reaction;
import game.map.Exit;
import game.map.Room;
import util.error.Logger;
import view.GUIConfig;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ice1000
 *         Created by asus1 on 2016/7/24.
 * @link https://github.com/xerial/sqlite-jdbc
 */
public class SQLiteDatabase
		implements Closeable {

	private static SQLiteDatabase instance;
	private Statement statement;

	private SQLiteDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			statement = DriverManager.getConnection("jdbc:sqlite:zh_CN.db").createStatement();
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	public static SQLiteDatabase getInstance() {
		if (instance == null) instance = new SQLiteDatabase();
		return instance;
	}

	private String crossPlatformHandler(String input) {
		input.replaceAll("%PLAYER_NAME%", GUIConfig.PLAYER_NAME);
		input.replaceAll("%NEWLINE", System.getProperty("line.separator"));
		return input;
	}

	public synchronized ArrayList<Choice> getAllChoice() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM CHOICE ORDER BY id ASC");
		ArrayList<Choice> choice = new ArrayList<>();
		while (set.next()) {
			choice.add(new Choice(
					set.getInt("id"),
					set.getString("choiceA"),
					set.getString("sequelA"),
					set.getString("choiceB"),
					set.getString("sequelB"),
					set.getString("choiceC"),
					set.getString("sequelC")
			));
		}
		set.close();
		return choice;
	}

	public synchronized HashMap<String, String> getBasic() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM BASIC ORDER BY id ASC");
		HashMap<String, String> basic = new HashMap<>(25, 10);
		while (set.next()) {
			basic.put(
					set.getString("key"),
					set.getString("value")
			);
		}
		return basic;
	}

	/**
	 * CREATE TABLE ROOM(id INTEGER PRIMARY KEY AUTOINCREMENT,
	 * disc TEXT, welc TEXT,boss TEXT,blood INTEGER,
	 * strike INTEGER, defence INTEGER,exp INTEGER, die TEXT);
	 */
	public synchronized ArrayList<Room> getAllRooms() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM ROOM ORDER BY id ASC");
		ArrayList<Room> rooms = new ArrayList<>();
		while (set.next()) {
			rooms.add(new Room(
					set.getInt("id"),
					set.getString("name"),
					set.getString("welc"),
					set.getString("boss"),
					set.getInt("blood"),
					set.getInt("strike"),
					set.getInt("defence"),
					set.getInt("exp"),
					set.getString("die")
			));
		}
		set.close();
		return rooms;
	}

	/**
	 * CREATE TABLE MAP( id INTEGER PRIMARY KEY AUTOINCREMENT, fromid INTEGER, toid INTEGER, dir INTEGER);
	 */
	public synchronized ArrayList<Exit> getAllExits() throws SQLException {
		// 与顺序无关
		ResultSet set = statement.executeQuery("SELECT * FROM MAP");
		ArrayList<Exit> exitses = new ArrayList<>();
		while (set.next()) {
			exitses.add(new Exit(
					set.getInt("fromid"),
					set.getInt("toid"),
					set.getInt("dir")
			));
		}
		set.close();
		return exitses;
	}

	/**
	 * CREATE TABLE ITEM(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, event INTEGER, extra TEXT, desc TEXT);
	 *
	 * @return items
	 */
	public synchronized ArrayList<Item> getAllItems() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM ITEM");
		int itemID = set.getInt("id");
		ResultSet reactionSet = statement.executeQuery(
				"SELECT * FROM REACTION WHERE a=\'ITEM^" + itemID + "\' OR b=\'ITEM^" + itemID + "\'"
		);
		ArrayList<Reaction> Reactions = new ArrayList<>();
		while (reactionSet.next())
			Reactions.add(new Reaction(
					reactionSet.getString("a"),
					reactionSet.getString("b"),
					reactionSet.getString("result")
			));
		ArrayList<Item> items = new ArrayList<>();
		while (set.next()) {
			items.add(new Item(
					itemID,
					set.getString("name"),
					set.getInt("event"),
					set.getString("extra"),
					set.getString("desc"),
					Reactions
			));
		}
		set.close();
		return items;
	}

	public synchronized ArrayList<Chat> getAllChats() throws SQLException {
		ArrayList<Chat> chats = new ArrayList<>();
		ResultSet chatSet = statement.executeQuery("SELECT * FROM CHAT ORDER BY id");
		while (chatSet.next()) {
			chats.add(new Chat(
					chatSet.getInt("id"),
					chatSet.getString("text"),
					chatSet.getBoolean("isp"),
					chatSet.getString("sequel")
			));
		}
		return chats;
	}

	public synchronized ArrayList<NPC> getCurrentNPC(int roomID) throws SQLException {
		return getNPCSuper(statement.executeQuery("SELECT * FROM NPC WHERE room=" + roomID));
	}

	public synchronized ArrayList<NPC> getAllNPC() throws SQLException {
		return getNPCSuper(statement.executeQuery("SELECT * FROM NPC"));
	}

	private synchronized ArrayList<NPC> getNPCSuper(ResultSet set) throws SQLException {
		ArrayList<NPC> NPCs = new ArrayList<>();
		while (set.next()) {
			int npcid = set.getInt("id");
			ResultSet chatSet = statement.executeQuery("SELECT * FROM CHAT WHERE npcid=" + npcid + "ORDER BY id");
			ArrayList<Chat> Chats = new ArrayList<>();
			while (chatSet.next()) {
				Chats.add(new Chat(
						chatSet.getInt("id"),
						chatSet.getString("text"),
						chatSet.getBoolean("isp"),
						chatSet.getString("sequel")
				));
			}
			while (set.next()) {
				NPCs.add(new NPC(
						npcid,
						set.getString("name"),
						set.getInt("room"),
						set.getInt("item"),
						set.getString("hello"),
						Chats
				));
			}
			set.close();
		}
		return NPCs;
	}

	/**
	 * CREATE TABLE BOSS_GET_ITEM (room INTEGER, item INTEGER);
	 *
	 * @return pair
	 */
	public synchronized HashMap<Integer, Integer> getAllRoomItemPairs() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM BOSS_GET_ITEM");
		HashMap<Integer, Integer> pairs = new HashMap<>();
		while (set.next()) pairs.put(set.getInt("room"), set.getInt("item"));
		set.close();
		return pairs;
	}

	@Override
	public void close() throws IOException {
		try {
			statement.close();
		} catch (SQLException e) {
//			告诉你，这叫嫁祸于IOException #(滑稽)
//			这样真的好嘛... ...#(滑稽）
//			大丈夫，萌大奶！#(认真)
			throw new IOException(e.getSQLState());
		}
	}
}
