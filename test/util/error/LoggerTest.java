package util.error;

import java.util.Arrays;

/**
 * @author ice1000
 *         Created by ice1000 on 2016/7/26.
 */
public class LoggerTest {

	public static void test1() {
		try {
			final char[] chars = {'1', '2'};
			System.out.println(Arrays.toString(chars));
			chars[100] = '@';
		} catch (IndexOutOfBoundsException e) {
			Logger.getInstance().log(e);
		}
	}

	public static void test2() {
		Logger.getInstance().log("错误信息：这是一个测试错误信息");
	}

	public static void main(String[] args) {
		test1();
	}
}
