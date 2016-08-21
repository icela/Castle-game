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
	String choiceA, sequelA;
	@NotNull
	String choiceB, sequelB;
	@NotNull
	String choiceC, sequelC;

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

	public HashMap<String, String> getInfo() {
		HashMap<String, String> result = new HashMap<>(4, 2);
		result.put("ID", String.valueOf(id));

		result.put("CHOICEA", choiceA);
		result.put("SEQUELA", sequelA);

		result.put("CHOICEB", choiceB);
		result.put("SEQUELB", sequelB);
		if (choiceC != null && sequelC != null) {
			result.put("CHOICEC", choiceC);
			result.put("SEQUELC", sequelC);
		}
		return result;
	}
}
