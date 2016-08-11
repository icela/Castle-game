package game.map;

import data.Direction;
import data.DirectionPair;
import data.database.SQLiteDatabase;
import game.Game;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 地图类
 * Created by asus1 on 2016/1/27.
 */
public class Map {

	private ArrayList<Room> rooms;
	private HashMap<Integer, Integer> itemPair;
	public Room currentRoom;
	private static final DirectionPair[] pairs = {
			null,
			new DirectionPair(Direction.UP, Direction.DOWN),
			new DirectionPair(Direction.NORTH, Direction.SOUTH),
			new DirectionPair(Direction.EAST, Direction.WEST),
	};

	public Map() {
		rooms = new ArrayList<>();
		itemPair = new HashMap<>();
//			构造地图结构
		try {
			itemPair = SQLiteDatabase.getInstance().getRoomItemPairs();
			rooms = SQLiteDatabase.getInstance().getRooms();
			SQLiteDatabase.getInstance().getExits().forEach(exit -> setExit(
					exit.from, exit.to,
					pairs[exit.dir]
			));
			itemPair.forEach((room, item) -> rooms.get(room).setBossItem(item));
		} catch (SQLException e) {
			Logger.log(e);
		}
		currentRoom = rooms.get(4);
	}

	private void setExit(int index_a, int index_b, DirectionPair pair) {
		rooms.get(index_a).setExit(String.valueOf(pair.direction1), index_b);
		rooms.get(index_b).setExit(String.valueOf(pair.direction2), index_a);
	}

	public boolean goRoom(String direction) {
		if (currentRoom.checkExit(direction)) currentRoom = rooms.get(currentRoom.showRoomId(direction));
		return currentRoom.checkExit(direction);
	}

	public boolean roomExists(String roomName) {
		for (Room room : rooms) if (room.matchName(roomName)) return true;
		return false;
	}

	public Room getHome() {
		return rooms.get(4);
	}

	public void setRoomsState(char[] state) {
		for (int i = 0; i < rooms.size(); i++) {
			char c;
			try {
				c = state[i];
			} catch (Exception e) {
				Logger.log(e);
				c = 1;
			}
			rooms.get(i).setBossGetItem(c == '1');
		}
	}

	public void loadRoom(String room_) {
		rooms.forEach(room -> {
			if (room.matchName(room_)) {
				currentRoom = room;
				return;
			}
		});
	}

	public char[] getRoomsState() {
		char[] roomsState = new char[rooms.size()];
		for (int i = 0; i < rooms.size(); i++)
			roomsState[i] = rooms.get(i).bossGetItem() ? '1' : '0';
		return roomsState;
	}

	public String wildRoom() {
		int index = (int) (Math.random() * 2000);
		index %= rooms.size();
		currentRoom = rooms.get(index);
		return currentRoom.getPrompt();
	}

	public void fightBoss(Game game) {
		game.player = currentRoom.fightBoss(game.player, game);
	}

}
