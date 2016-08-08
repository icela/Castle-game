package game.cells.item;

import game.cells.spirit.Reaction;

import java.util.ArrayList;

public class Item {
	private String name = "";
	private String desc = "";
	private int id;
	private ArrayList<Reaction> reaction = new ArrayList<>();

	//	public boolean get = false;
	public int num;
	public int eventId;
	public String eventExtraData;

	/**
	 * 构造一个物品基类
	 *
	 * @param id             物品编号，用来识别（字符串匹配不如直接匹配整数），对应的字段是id
	 * @param name           物品名，对应的字段是name
	 * @param eventId        事件编号，对应的字段是event
	 * @param eventExtraData 事件的extra信息，这是用来节约代码量的，比如一个回血道具，
	 *                       回血事件就只写一个，这个字符串就存你回到底多少血，
	 *                       这样避免小血瓶中血瓶每一个都写一个差不多的方法，看着难受
	 * @param desc           物品介绍，仅用于查看物品信息
	 */
	public Item(int id, String name, int eventId, String eventExtraData, String desc, ArrayList<Reaction> reaction) {
		this(id, name, eventId, eventExtraData, desc, 0, reaction);
	}

	/**
	 * 另一个构造器
	 *
	 * @param id             {@link #id}
	 * @param name           {@link #name}
	 * @param eventId        {@link #eventId}
	 * @param eventExtraData {@link #eventExtraData}
	 * @param desc           {@link #desc}
	 * @param num            物品数量
	 */
	public Item(int id, String name, int eventId, String eventExtraData, String desc, int num, ArrayList<Reaction> reaction) {
		this.name = name;
		this.num = num;
		this.eventId = eventId;
		this.eventExtraData = eventExtraData;
		this.id = id;
		this.desc = desc;
		this.reaction = reaction;
	}

	public String getName() {
		return name;
	}
}
