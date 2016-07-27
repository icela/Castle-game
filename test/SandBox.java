import java.io.IOException;

/**
 * Created by asus1 on 2016/7/26.
 * @author ice1000
 */
public class SandBox {
	public static void main(String[] args) {
		System.out.println("23333");
		System.out.println("23333");
		System.out.println("23333");
		System.out.println("23333");
		try {
			//Runtime.getRuntime().exec("cls");
			Runtime.getRuntime().exec("cmd cls");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
