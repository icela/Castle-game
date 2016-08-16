package game.cells.spirit;

import java.util.ArrayList;

public class NPC extends Cell {

	private ArrayList<Chat> chat;
	private int id, room;
	private int item;
	private String hello;

	public NPC(int id, String name, int room, String hello, ArrayList<Chat> chat) {
		this(id, name, room, chat);
		this.hello = hello;
	}

	public NPC(int id, String name, int room, ArrayList<Chat> chat) {
		super(name);
		this.id = id;
		this.chat = chat;
		this.room = room;
	}

	public NPC(int id, String name, int room, int item, ArrayList<Chat> chat) {
		super(name);
		this.id = id;
		this.item = item;
		this.chat = chat;
		this.room = room;
	}

	public NPC(int id, String name, int room, int item, String hello, ArrayList<Chat> chat) {
		this(id, name, room, item, chat);
		this.hello = hello;
	}

//	public void itemGet(Item item) {
//		items.add(item);
//	}

//	public void itemGive(int index,int num){
//		items.get(index).getNumOf(num);
//	}

	public ArrayList<Chat> getChat() {
		return chat;
	}

	public String getHello() {
		return hello;
	}
}
