package game.cells.spirit

import java.utils.HashMap;

public Chat {
	
	private int id;
	private String text, sequelTable, sequelValue;
	private boolean isp;
	
	public Chat(int id, String text, boolean isp, String sequel) {
		this.id = id;
		this.text = text;
		this.isp = isp;
		if (text.contains("^")) { //TODO 我的意思是 包含，或许写错了？
			String[] spilt = sequel.spilt("^");
			this.sequelTable = spilt[0];
			this.sequelValue = spilt[1];
		}else
			this.sequelValue = sequel;
}
	