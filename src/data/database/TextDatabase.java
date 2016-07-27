package data.database;

import game.cells.Player;
import game.commands.implement.CommandReset;
import game.map.Map;
import util.NameGenerator;
import util.error.Logger;
import view.GUIConfig;

import java.io.*;
import java.util.Base64;

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
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
	}

	private void readData() throws IOException {
		Base64.Encoder encoder = Base64.getEncoder();
		File file = new File(savePath);
		//TODO 觉得效率低了点。。。但似乎只能这样了
		BufferedReader reader = new BufferedReader(new FileReader(file));
		roomName = new String(encoder.encode(reader.readLine().getBytes()));
		roomsState = (new String(encoder.encode(reader.readLine().getBytes()))).toCharArray();
		playerName = new String(encoder.encode(reader.readLine().getBytes()));
		blood = Integer.parseInt(new String(encoder.encode(reader.readLine().getBytes())));
		strike = Integer.parseInt(new String(encoder.encode(reader.readLine().getBytes())));
		defence = Integer.parseInt(new String(encoder.encode(reader.readLine().getBytes())));
		level = Integer.parseInt(new String(encoder.encode(reader.readLine().getBytes())));
		experience = Integer.parseInt(new String(encoder.encode(reader.readLine().getBytes())));

		reader.close();
	}

	public static TextDatabase getInstance() {
		if (instance == null)
			instance = new TextDatabase();
		return instance;
	}

	public Map loadMap(String defaultName) {
		Map map = new Map();
		if (!isFileExists()) return map;
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
		return isFileExists() ? new Player(
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

	public void savePlayer(Player player) {
		this.playerName = player.toString();
		this.blood = player.getBlood();
		this.strike = player.getStrike();
		this.defence = player.getDefence();
		this.level = player.getLevel();
		this.experience = player.getExperience();
	}

	public static boolean isFileExists() {
		return new File(savePath).exists();
	}

	private File openFile() throws IOException {
		File file = new File(savePath);
		if (file.exists())
			file.delete();
		file.createNewFile();
		return file;
	}
}
