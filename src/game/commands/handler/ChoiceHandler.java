package game.commands.handler;

import com.sun.istack.internal.NotNull;
import data.database.SQLiteDatabase;
import game.cells.spirit.Chat;
import game.cells.spirit.Choice;
import util.error.Logger;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Eldath on 2016/8/22 0022.
 *
 * @author Eldath
 */
public class ChoiceHandler {

	private ArrayList<Chat> allChats;

	private void init() {
		try {
			this.allChats = SQLiteDatabase.getInstance().getAllChats();
		} catch (SQLException e) {
			Logger.log(e);
		}
	}

	public String handlerChoice(@NotNull String input, @NotNull Choice superChoice) {

		@NotNull String choose = input.toUpperCase();
		String currentSequel = "", result = null;

		if (!(choose.contains("A") && choose.contains("B") && choose.contains("C")))
			return null;
		if (choose.contains("A"))
			currentSequel = superChoice.getSequelA();
		else if (choose.contains("B"))
			currentSequel = superChoice.getSequelB();
		else if (choose.contains("C"))
			currentSequel = superChoice.getSequelC();

		if (currentSequel.contains("CHAT^")) {
			result = allChats.get(Integer.parseInt(currentSequel.split("CHAT^")[1])).getText();
		} else if (currentSequel.contains("END_OF_CHAT"))
			result = "";
		else if (currentSequel.contains("ENDING"))
			result = ""; //TODO 先在spirit和SQLiteDatabase处理了ENDING表再来艹这里... ...
		else if (currentSequel.contains("INFO"))
			result = ""; //TODO 先在spirit和SQLiteDatabase处理了INFO表再来艹这里... ...
		return result;
	}
}
