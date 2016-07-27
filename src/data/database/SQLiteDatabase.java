package data.database;

import game.map.Exits;
import game.map.Room;
import util.error.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
			Logger.getInstance().log(e);
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
	public ArrayList<Exits> getExits() throws SQLException {
		// 与顺序无关
		ResultSet set = statement.executeQuery("SELECT * FROM MAP");
		ArrayList<Exits> exitses = new ArrayList<>();
		while (set.next()) {
			exitses.add(new Exits(
					set.getInt("fromid"),
					set.getInt("toid"),
					set.getInt("dir")
			));
		}
		set.close();
		return exitses;
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
