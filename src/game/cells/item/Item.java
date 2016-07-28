package game.cells.item;

public class Item {
	private String name = "";
	private String desc = "";
	private int id;

	public boolean get = false;
	public int num;
	public int eventId;
	public String eventExtraData;

	public Item(int id, String name, int eventId, String eventExtraData, String desc) {
		this(id, name, eventId, eventExtraData, desc, 0);
	}
	public Item(int id, String name, int eventId, String eventExtraData, String desc, int num) {
		this.name = name;
		this.num = num;
		this.eventId = eventId;
		this.eventExtraData = eventExtraData;
		this.id = id;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}
}
