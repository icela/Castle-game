package game.cells;

import util.interfaces.Echoer;

public class Player extends Cell {

	int blood = 0;
	int strike = 0;
	int defence = 0;
	private int level = 0;
	int experience = 0;
	private final int[] limit = {
			5,
			10,
			30,
			50,
			80,
			120,
			160,
			200,
			250,
			320,
			400,
			480,
			560,
			640,
			720,
			800,
			900,
			1000,
			1120,
			1240,
			1380,
			1520,
			1680,
			1840,
			2000,
			2200,
			2400,
			2600,
			2800,
			3200,
			3600,
			4000,
			99999999,
			99999999,
	};

	/**
	 * 在玩家中，经验表示拥有的经验，在怪物中表示打败后得到的经验。
	 *
	 * @param name    名字
	 * @param blood   血量
	 * @param defence 防御
	 * @param strike  攻击
	 */
	public Player(String name, int blood, int strike, int defence) {
		super(name);
		this.blood = blood;
		this.strike = strike;
		this.defence = defence;
	}

	/**
	 * @param name       {@link #name}
	 * @param blood      {@link #blood}
	 * @param strike     {@link #strike}
	 * @param defence    {@link #defence}
	 * @param level      {@link #level}
	 * @param experience {@link #experience}
	 */
	public Player(String name, int blood, int strike, int defence, int level, int experience) {
		this(name, blood, strike, defence);
		this.level = level;
		this.experience = experience;
	}

	public String stateToString() {
		return "等级：" + (level + 1) +
				"\n经验值：" + experience +
				"/" + limit[level] +
				"\n姓名：" + name +
				"\n攻击：" + strike +
				"\n防御：" + defence +
				"\n体力值：" + blood;
	}

	@Override
	public String toString() {
		return super.getName();
	}

	int win(int experience, Echoer echoer) {
		this.experience += experience;
		if (this.experience >= limit[level]) {
			this.experience -= limit[level];
			level++;
			strike += level * 2;
			defence += level * 2;
			blood += level * 20;
			echoer.echoln("恭喜您升级啦~\\(≧▽≦)/~啦啦啦\n各种属性加成哦~");
		}
		return experience;
	}

	public boolean treat() {
		if (blood <= 120) {
			blood = 120;
			return true;
		} else
			return false;
	}

	public Player getStateData() {
		return this;
	}

	public void rename(String newName) {
		this.name = newName;
	}

	public int getBlood() {
		return blood;
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public int getStrike() {
		return strike;
	}

	public int getDefence() {
		return defence;
	}

	//	public void savePlayer(){
//		database.savePlayer(
//				this.name + "\r\n" +
//				this.blood + "\r\n" +
//				this.strike + "\r\n" +
//				this.defence + "\r\n" +
//				this.level + "\r\n" +
//				this.experience + "\r\n"
//		);
//	}

}
