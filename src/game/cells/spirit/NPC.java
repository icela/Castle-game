package game.cells.spirit;

import game.cells.item.Item;

import java.util.ArrayList;

public class NPC extends Cell {

	private ArrayList<String> chat;
	private int id, item, room;
	private final ArrayList<Item> items = new ArrayList<>();


	public NPC(int id, int name, int room, ArrayList<String> chat, int item) {
		super(name);
		this.id = id;
		this.chat = chat;
		this.item = item;
		this.room = room;
	}
	
	public NPC(int id, int name, int room, ArrayList<String> chat) {
		super(name);
		this.id = id;
		this.chat=chat;
		this.room=room;
	}

	public void itemGet(Item item) {
		items.add(item);
	}

//	public void itemGive(int index,int num){
//		items.get(index).getNumOf(num);
//	}

	public String getChat() {
		return chat;
	}
}
