package game.map;

/**
 * 传输数据用
 * Created by asus1 on 2016/2/15.
 */
public class Exits {
	public final int from;
	public final int to;
	public final int dir;

	public Exits(int from, int to, int dir) {
		this.from = from;
		this.to = to;
		this.dir = dir;
	}
}
