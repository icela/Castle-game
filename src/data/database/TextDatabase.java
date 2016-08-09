package data.database;

import game.cells.item.Item;
import game.cells.spirit.Player;
import game.map.Map;
import util.NameGenerator;
import util.error.Logger;
import view.GUIConfig;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 封装数据库操作，单例模式
 *
 * @author ice1000
 *         Created by ice1000 on 2016/1/28.
 */
public class TextDatabase {

	private static final String savePath = "save.ice";

	private String playerName = "";
	private char[] roomsState;
	private String roomName;
	private int blood = 0;
	private int strike = 0;
	private int defence = 0;
	private int level = 0;
	private int experience = 0;

	private ArrayList<Item> userItems;
	private HashMap<Integer, Integer> roomPairs;

	private static TextDatabase instance;

	private TextDatabase() {
		try {
			readData();
		} catch (Exception e) {
			Logger.log(e);
		}
	}

	private void readData() throws IOException {
		File file = new File(savePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		if (GUIConfig.DEBUG) {
			roomName = reader.readLine();
			roomsState = reader.readLine().toCharArray();
			playerName = reader.readLine();
			blood = Integer.parseInt(reader.readLine());
			strike = Integer.parseInt(reader.readLine());
			defence = Integer.parseInt(reader.readLine());
			level = Integer.parseInt(reader.readLine());
			experience = Integer.parseInt(reader.readLine());
			stringToArray(reader.readLine());
		} else {
			String[] text = new String(Base64.getDecoder().decode(reader.readLine().getBytes())).split("\\r\\n");
			roomName = text[0];
			roomsState = text[1].toCharArray();
			playerName = text[2];
			blood = Integer.parseInt(text[3]);
			strike = Integer.parseInt(text[4]);
			defence = Integer.parseInt(text[5]);
			level = Integer.parseInt(text[6]);
			experience = Integer.parseInt(text[7]);
		}
		reader.close();
	}

	public static TextDatabase getInstance() {
		if (instance == null)
			instance = new TextDatabase();
		return instance;
	}

	public Map loadMap(String defaultName) {
		Map map = new Map();
		if (!fileExists()) return map;
		map.setRoomsState(roomsState);
		if (roomName == null)
			roomName = defaultName;
		map.loadRoom(roomName);
		return map;
	}

	private void saveMap(Map map) {
		this.roomName = map.currentRoom.toString();
		this.roomsState = map.getRoomsState();
	}

	public void saveFile(Map map, Player player) throws IOException {
		File file = openFile();
		FileWriter writer = new FileWriter(file);
		saveMap(map);
		savePlayer(player);
		if (GUIConfig.DEBUG)
			writer.write(this.getInformation());
		else
			writer.write(new String(Base64.getEncoder().encode(this.getInformation().getBytes())));
		writer.close();
	}

	private ArrayList<Item> getAllItems() {
		ArrayList<Item> temp = new ArrayList<>();
		try {
			temp = SQLiteDatabase.getInstance().getItems();
		} catch (SQLException e) {
			Logger.log(e);
		}
		return temp;
	}

	private ArrayList<Item> stringToArray(String str) {
		ArrayList<Item> array = new ArrayList<>();
		if (str.isEmpty())
			return null;
		String[] temp = str.split(",");
		for (int i = 0; i <= temp.length; i++)
			array.add(i, getAllItems().get(i));
		return array;
	}

	private String arrayToString(ArrayList array) {
		if (array.isEmpty())
			return null;
		return String.join(",", (CharSequence[]) array.toArray());
	}

	private String mapToString(HashMap map) {
		String result = null;
		if (map.isEmpty())
			return null;
		for (int i = 0; i <= map.size(); i++) {
			Set set = map.keySet();
			Iterator iter = set.iterator();
			while (iter.hasNext()) {
				String index = iter.next().toString();
				result += iter.next() + ":" + map.get(index) + "\r\n";
			}
		}
		return result;
	}

	private String getInformation() {
		roomPairs = TempDatabase.getInstance().getRoomPairs();
		userItems = TempDatabase.getInstance().getUserItems();
		return this.roomName + "\r\n" +
				String.valueOf(this.roomsState) + "\r\n" +
				this.playerName + "\r\n" +
				this.blood + "\r\n" +
				this.strike + "\r\n" +
				this.defence + "\r\n" +
				this.level + "\r\n" +
				this.experience + "\r\n" +
				arrayToString(userItems) + "\r\n" +
				mapToString(roomPairs)
				;
	}

	public HashMap<Integer, Integer> getRoomPairs() {
		return roomPairs;
	}

	public ArrayList<Item> getUserItems() {
		return userItems;
	}

	public Player loadPlayer() {
		return fileExists() ? new Player(
				playerName,
				blood,
				strike,
				defence,
				level,
				experience
		) : new Player(
				NameGenerator.generate(),
				200,
				10,
				5
		);
	}

	private void savePlayer(Player player) {
		this.playerName = player.toString();
		this.blood = player.getBlood();
		this.strike = player.getStrike();
		this.defence = player.getDefence();
		this.level = player.getLevel();
		this.experience = player.getExperience();
	}

	public static boolean fileExists() {
		return new File(savePath).exists();
	}

	private File openFile() throws IOException {
		File file = new File(savePath);
		if (fileExists())
			file.delete();
		file.createNewFile();
		return file;
	}
}
