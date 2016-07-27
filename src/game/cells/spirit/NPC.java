package game.cells.spirit;

import game.cells.item.Item;

import java.util.ArrayList;

public class NPC extends Cell {

	private String chat = "";
	private final ArrayList<Item> items = new ArrayList<>();

	public NPC(String name, String chat) {
		super(name);
		this.chat = chat;
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
