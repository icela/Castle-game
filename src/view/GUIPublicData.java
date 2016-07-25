package view;

/**
 * @author ice1000
 * Created by ice1000 on 2016/7/24.
 */
public class GUIPublicData {
	private static final String GUI_FORM_TITLE = "城堡游戏   by 千里冰封"+" "+getVersion();
	private static final String VERSION = "V0.0.1 Alpha"
	public static String getTitle(){
		return GUI_FORM_TITLE;
	}
	public static String getVersion(){
		return VERSION;
	}
}
