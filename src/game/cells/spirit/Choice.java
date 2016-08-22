package game.cells.spirit;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;

/**
 * Created by Eldath on 2016/8/21 0021.
 *
 * @author Eldath
 */
public class Choice {

	int id;
	@NotNull
	String choiceA = "", sequelA = "";
	@NotNull
	String choiceB = "", sequelB = "";
	String choiceC = "", sequelC = "";

	public Choice(int id, String choiceA, String sequelA) {
		this.id = id;
		this.choiceA = choiceA;
		this.sequelA = sequelA;
	}

	public Choice(int id, String choiceA, String sequelA, String choiceB, String sequelB) {
		this(id, choiceA, sequelA);
		this.choiceB = choiceB;
		this.sequelB = sequelB;
	}

	public Choice(int id, String choiceA, String sequelA, String choiceB, String sequelB, String choiceC, String sequelC) {
		this(id, choiceA, sequelA, choiceB, sequelB);
		this.choiceC = choiceC;
		this.sequelC = sequelC;
	}

	@Override
	public String toString() {
		String result;
		result = choiceA + "\t" + choiceB;
		if (choiceC.contains("") && sequelC.contains(""))
			result = result + "\t" + choiceC;
		return result;
	}

	public String getSequelA() {
		return sequelA;
	}

	public String getSequelB() {
		return sequelB;
	}

	public String getSequelC() {
		return sequelC;
	}
}
