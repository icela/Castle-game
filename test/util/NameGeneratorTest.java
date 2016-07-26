package util;

/**
 * @author ice1000
 *         <p>
 *         Created by ice1000 on 2016/7/26.
 */
public class NameGeneratorTest {
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(NameGenerator.generate());
		}
	}
}
