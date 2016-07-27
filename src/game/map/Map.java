package game.map;

import game.Game;
import data.database.SQLiteDatabase;
import data.Direction;
import data.DirectionPair;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 地图类
 * Created by asus1 on 2016/1/27.
 */
public class Map {

	private ArrayList<Room> rooms;
	public Room currentRoom;
	private static final DirectionPair[] pairs = {
			null,
			new DirectionPair(Direction.UP, Direction.DOWN),
			new DirectionPair(Direction.NORTH, Direction.SOUTH),
			new DirectionPair(Direction.EAST, Direction.WEST),
	};

	public Map() {
		rooms = new ArrayList<>();
//			构造地图结构
		try {
			rooms = SQLiteDatabase.getInstance().getRooms();
			SQLiteDatabase.getInstance().getExits().forEach(exit -> setExit(
					exit.from, exit.to,
					pairs[exit.dir]
			));
			SQLiteDatabase.getInstance().getRoomItemPairs().forEach(pair ->
					rooms.get(pair.room).setBossItem(pair.item));
		} catch (SQLException e) {
			Logger.getInstance().log(e);
		}
		currentRoom = rooms.get(4);
	}

	private void setExit(int index_a, int index_b, DirectionPair pair) {
		rooms.get(index_a).setExit(String.valueOf(pair.direction1), index_b);
		rooms.get(index_b).setExit(String.valueOf(pair.direction2), index_a);
	}

	public boolean goRoom(String direction) {
		if (currentRoom.checkExit(direction)) {
			currentRoom = rooms.get(currentRoom.showRoomId(direction));
			return true;
		} else
			return false;
	}

	public boolean roomExists(String roomName) {
		for (Room room : rooms)
			if (room.equals(roomName))
				return true;
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
				Logger.getInstance().log(e);
				c = 1;
			}
			rooms.get(i).setBossGetItem(c == '1');
		}
	}

	public void loadRoom(String room_) {
		for (Room room : rooms) {
			if (room.equals(room_)) {
				currentRoom = room;
				break;
			}
		}
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

	public boolean fightBoss(Game game) {
		game.player = currentRoom.fightBoss(game.player, game);
		return currentRoom.bossGetItem();
	}

}
