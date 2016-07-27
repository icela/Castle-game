package util;

/**
 * 命名器
 * Created by asus1 on 2016/1/29.
 */
public class NameGenerator {

	private static final String[] names = {
			"千里冰封",
			"奶茶未凉",
			"周明凯",
			"SpiderKing",
			"Timothy",
			"Eldath"
	};

	public static String generate() {
		double i = Math.random();
		return names[(int) (names.length * i)];
	}
}
