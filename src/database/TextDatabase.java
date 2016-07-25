package database;

import cells.Player;
import map.GameMap;

import java.io.*;

/**
 * 封装数据库操作
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
		File file = new File(savePath);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));

			roomName = reader.readLine();
			roomsState = reader.readLine().toCharArray();
			playerName = reader.readLine();
			blood = Integer.parseInt(reader.readLine());
			strike = Integer.parseInt(reader.readLine());
			defence = Integer.parseInt(reader.readLine());
			level = Integer.parseInt(reader.readLine());
			experience = Integer.parseInt(reader.readLine());

			reader.close();

		} catch (Exception ignored) {
		}
	}

	public static TextDatabase getInstance() {
		if (instance == null)
			instance = new TextDatabase();
		return instance;
	}

	public void loadMap(GameMap map, String defaultName) {
		map.setRoomsState(roomsState);
		if (roomName == null)
			roomName = defaultName;
		map.loadRoom(roomName);
	}

	public void saveMap(GameMap map) throws IOException {
		File file = new File(savePath);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		this.roomName = map.getCurrentRoom().toString();
		this.roomsState = map.getRoomsState();
		writer.write(this.getInformation());
		writer.close();
	}

	public void saveMapAndState(GameMap map, Player player) throws IOException {
		File file = openFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		this.roomName = map.getCurrentRoom().toString();
		this.roomsState = map.getRoomsState();
		this.playerName = player.toString();
		this.blood = player.getBlood();
		this.strike = player.getStrike();
		this.defence = player.getDefence();
		this.level = player.getLevel();
		this.experience = player.getExperience();

		writer.write(this.getInformation());
		writer.close();
	}

	public String getInformation() {
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

	/**
	 * @param player 玩家指针
	 *               读取数据
	 */
	public void loadState(Player player) {
		player.setValues(
				playerName,
				blood,
				strike,
				defence,
				level,
				experience
		);
	}

	public void saveState(Player player) throws IOException {
//		System.out.println("正在保存数据。。");
		File file = openFile();
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(file));
		this.playerName = player.toString();
		this.blood = player.getBlood();
		this.strike = player.getStrike();
		this.defence = player.getDefence();
		this.level = player.getLevel();
		this.experience = player.getExperience();

		writer.write(this.getInformation());

		writer.close();
	}

	public static boolean isFileExists() {
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
