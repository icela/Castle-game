package data.database;

import game.cells.spirit.NPC;
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
					set.getString("disc"),
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
	 * @return items
	 */
	public ArrayList<Item> getItems() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM ITEM");
		ArrayList<Item> items = new ArrayList<>();
		while (set.next()) {
			items.add(new Item(
					set.getInt("id"),
					set.getString("name"),
					set.getInt("event"),
					set.getString("extra"),
					set.getString("desc")
			));
		}
		set.close();
		return items;
	}
	/*
	public ArrayList<Object> getNPC() throws SQLException {
		ResultSet set = statement.executeQuery("SELECT * FROM NPC");
		ArrayList<Item> NPCs = new ArrayList<>();
		while (set.next()) {
			NPCs.add(new NPC(
					set.getInt("id"),
					set.getString("name"),
					set.getInt("event"),
					set.getString("extra"),
					set.getString("desc")
			));
		}
		set.close();
		return items;
	}
	*/

	/**
	 * CREATE TABLE BOSS_GET_ITEM (room INTEGER, item INTEGER);
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
	public void close() throws IOException{
		try {
			statement.close();
		} catch (SQLException e) {
			// 告诉你，这叫嫁祸于IOException #(滑稽)
			throw new IOException(e.getSQLState());
		}
	}
}
