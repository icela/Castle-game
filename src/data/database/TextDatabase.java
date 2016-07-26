package data.database;

import game.cells.Player;
import game.map.GameMap;
import util.NameGenerator;

import java.io.*;

/**
 * 封装数据库操作，单例模式
 * Created by ice1000 on 2016/1/28.
 */
public class TextDatabase {

	private static final String savePath = "." + File.separator + "save.ice";

	private String playerName = "";
	private char[] roomsState;
	private String roomName;
	private int blood = 0;
	private int strike = 0;
	private int defence = 0;
	private int level = 0;
	private int experience = 0;

	private static TextDatabase instance;

	private TextDatabase() {
		try {
			readData();
		} catch (Exception ignored) {
		}
	}

	private void readData() throws IOException {
		File file = new File(savePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		roomName = reader.readLine();
		roomsState = reader.readLine().toCharArray();
		playerName = reader.readLine();
		blood = Integer.parseInt(reader.readLine());
		strike = Integer.parseInt(reader.readLine());
		defence = Integer.parseInt(reader.readLine());
		level = Integer.parseInt(reader.readLine());
		experience = Integer.parseInt(reader.readLine());

		reader.close();
	}

	public static TextDatabase getInstance() {
		if (instance == null)
			instance = new TextDatabase();
		return instance;
	}

	public GameMap loadMap(String defaultName) {
		GameMap map = new GameMap();
		if (!fileExists()) return map;
		map.setRoomsState(roomsState);
		if (roomName == null)
			roomName = defaultName;
		map.loadRoom(roomName);
		return map;
	}

	private void saveMap(GameMap map) throws IOException {
		this.roomName = map.currentRoom.toString();
		this.roomsState = map.getRoomsState();
	}

	public void saveFile(GameMap map, Player player) throws IOException {
		File file = openFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		saveMap(map);
		savePlayer(player);
		writer.write(this.getInformation());
		writer.close();
	}

	private String getInformation() {
		return this.roomName + "\r\n" +
				String.valueOf(this.roomsState) + "\r\n" +
				this.playerName + "\r\n" +
				this.blood + "\r\n" +
				this.strike + "\r\n" +
				this.defence + "\r\n" +
				this.level + "\r\n" +
				this.experience + "\r\n"
				;
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

	public void savePlayer(Player player) throws IOException {
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
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}
}
