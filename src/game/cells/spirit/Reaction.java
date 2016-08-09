package game.cells.spirit;

import java.util.HashMap;

/**
 * @author Eldath
 *         Created by Eldath on 2016/8/8 0008.
 */
public class Reaction {
	private String a, b, result;

	public Reaction(String a, String b, String result) {
		this.a = a;
		this.b = b;
		this.result = result;
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public int getAID() {
		return Integer.parseInt(a.split("^")[1]);
	}

	public int getBID() {
		return Integer.parseInt(b.split("^")[1]);
	}

	public String getResult() {
		return result;
	}

	public HashMap<String, String> getReaction() {
		HashMap<String, String> result1 = new HashMap<>(3, 1);
		result1.put("A", a);
		result1.put("B", b);
		result1.put("RESULT", result);
		return result1;
	}
}
