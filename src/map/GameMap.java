package map;

import castle.Game;
import database.Database;
import util.Direction;
import util.DirectionPair;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 地图类
 * Created by asus1 on 2016/1/27.
 */
public class GameMap {

	private ArrayList<Room> theRooms;
	private Room currentRoom;
	private static final DirectionPair[] pairs ={
			null,
		new DirectionPair(Direction.UP,   Direction.DOWN ),
		new DirectionPair(Direction.NORTH,Direction.SOUTH),
		new DirectionPair(Direction.EAST, Direction.WEST ),
	};

	public GameMap() {
		theRooms = new ArrayList<>();
//			构造地图结构
		try {
			theRooms = Database.getRooms();
//			System.out.println("theRooms.size = " + theRooms.size());
			for (Exits exit : Database.getExits()) {
				setExit(
						exit.from, exit.to,
						pairs[exit.dir]
				);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		currentRoom = theRooms.get(4);
	}

	private void setExit(int index_a, int index_b, DirectionPair pair){
		theRooms.get(index_a).setExit(String.valueOf(pair.getDirection1()), index_b);
		theRooms.get(index_b).setExit(String.valueOf(pair.getDirection2()), index_a);
	}

	public void setCurrentRoom(Room room){
		currentRoom = room;
	}

	public boolean goRoom(String direction){
		if( currentRoom.checkExit(direction) ) {
			currentRoom = theRooms.get(currentRoom.showRoomId(direction));
			return true;
		}
		else
			return false;
	}

	public boolean isRoomExists(String roomName){
		for (Room room : theRooms) {
			if(room.equals(roomName)){
				return true;
			}
		}
		return false;
	}

	public Room getHome(){
		return theRooms.get(4);
	}

	public void setRoomsState(char[] state){
		for (int i = 0; i < theRooms.size(); i++) {
			char c;
			try{c = state[i];}
			catch (Exception e){c = 1;}
			theRooms.get(i).setBossGetItem(c == '1');
		}
	}

	public void loadRoom(String room_){
		for (Room room : theRooms) {
			if(room.equals(room_)){
				currentRoom = room;
				break;
			}
		}
	}

	public char[] getRoomsState(){
		char[] roomsState = new char[theRooms.size()];
		for (int i = 0; i < theRooms.size(); i++)
			roomsState[i] = theRooms.get(i).isBossGetItem() ? '1' : '0';
		return roomsState;
	}

	public String wildRoom(){
		int index = (int) (Math.random()*2000);
		index %= theRooms.size();
		currentRoom = theRooms.get(index);
		return currentRoom.getPrompt();
	}

	public void fightBoss(Game game){
		game.setPlayer(currentRoom.fightBoss(game.getPlayer(), game));
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
}
