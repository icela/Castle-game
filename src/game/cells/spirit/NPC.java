package game.cells.spirit;

import game.cells.item.Item;
import game.cells.spirit.Chat;

import java.util.ArrayList;

public class NPC extends Cell {

	private ArrayList<Chat> chat;
	private int id, item, room;
	private final int item;


	public NPC(int id, int name, int room, ArrayList<Chat> chat, int item) {
		super(name);
		this.id = id;
		this.chat = chat;
		this.item = item;
		this.room = room;
	}
	
	public NPC(int id, int name, int room, ArrayList<Chat> chat) {
		super(name);
		this.id = id;
		this.chat=chat;
		this.room=room;
	}

// TODO ???????????
//	public void itemGet(Item item) {
//		items.add(item);
//	}

//	public void itemGive(int index,int num){
//		items.get(index).getNumOf(num);
//	}

	public String getChat() {
		return chat;
	}
}
