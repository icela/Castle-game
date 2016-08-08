package data.database;

import game.cells.spirit.Chat;
import game.cells.spirit.NPC;
import game.cells.spirit.Reaction;
import game.map.RoomItemPair;
import game.cells.item.Item;
import game.map.Exit;
import game.map.Room;
import util.error.Logger;

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
 */
public class SQLiteDatabase
		implements Closeable {

	private static SQLiteDatabase instance;
	private Statement statement;

	private SQLiteDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			statement = DriverManager.getConnection("jdbc:sqlite:data.db").createStatement();
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	public static SQLiteDatabase getInstance() {
		if (instance == null)
			instance = new SQLiteDatabase();
		return instance;
	}

	/**
	 * CREATE TABLE ROOM(id INTEGER PRIMARY KEY AUTOINCREMENT,
	 * disc TEXT, welc TEXT,boss TEXT,blood INTEGER,
	 * strike INTEGER, defence INTEGER,exp INTEGER, die TEXT);
	 */
	public ArrayList<Room> getRooms() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM ROOM ORDER BY id ASC");
		ArrayList<Room> rooms = new ArrayList<>();
		while (set.next()) {
			rooms.add(new Room(
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
	public ArrayList<Exit> getExits() throws SQLException {
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
	public ArrayList<Item> getItems() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM ITEM");
		int itemID=set.getInt("id");
		ResultSet reactionSet=statement.executeQuery("SELECT * FROM REACTION WHERE a=ITEM^"+itemID);
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

	public ArrayList<NPC> getNPC() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM NPC");
		int npcid = set.getInt("id");
		ResultSet chatSet = statement.executeQuery("SELECT * FROM CHAT WHERE npcid=" + npcid);
		ArrayList<NPC> NPCs = new ArrayList<>();
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
		return NPCs;
	}

	/**
	 * CREATE TABLE BOSS_GET_ITEM (room INTEGER, item INTEGER);
	 *
	 * @return pair
	 */
	public ArrayList<RoomItemPair> getRoomItemPairs() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM BOSS_GET_ITEM");
		ArrayList<RoomItemPair> pairs = new ArrayList<>();
		while (set.next()) {
			pairs.add(new RoomItemPair(
					set.getInt("room"),
					set.getInt("item")
			));
		}
		set.close();
		return pairs;
	}

	@Override
	public void close() throws IOException {
		try {
			statement.close();
		} catch (SQLException e) {
			// 告诉你，这叫嫁祸于IOException #(滑稽)
			// 这样真的好嘛... ...#(滑稽）
			throw new IOException(e.getSQLState());
		}
	}
}
