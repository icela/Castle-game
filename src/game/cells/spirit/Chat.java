package game.cells.spirit;


/**
 * Created by Eldath on 2016/8/7 0007.
 *
 * @author Eldath
 */

public class Chat {
	private int id;
	private String text, sequelTable, sequelValue;
	private boolean isp, spilt = false;

	public Chat(int id, String text, boolean isp, String sequel) {
		this.id = id;
		this.text = text;
		this.isp = isp;
		if (text.contains("^")) {
			spilt = true;
			String[] spilt = sequel.split("^");
			this.sequelTable = spilt[0];
			this.sequelValue = spilt[1];
		} else this.sequelValue = sequel;
	}

	@Override
	public String toString() {
		return text;
	}

	public String getText() {
		return text;
	}

	public String getSequel() {
		if (spilt) return sequelTable + "^" + sequelValue;
		else return sequelValue;
	}
}

