package view;

/**
 * @author ice1000
 *         Created by ice1000 on 2016/7/24.
 */
public class GUIConfig {

	public static final String VERSION = "V0.0.2 Alpha";

	public static final String BUILD = "MjAxNjA4MTAwMDE=";
	//TODO Build号=Base64.getEncoder.encode(最后一次成功构建日期 (yyyymmdd) +当日构建次数 (0001) )

	public static final String ARCHIVE_V = "0000";

	//TODO 发布前记得改DEBUG常量！！
	public static final boolean DEBUG = false;

	public static String PLAYER_NAME;

	public static final String MOTTO = "When staring at stars, what do you expected to see ?";

	public static final String GUI_FORM_TITLE = "城堡游戏   by 冰封 and Eldath" + " " + VERSION;

	public static final float FONT_SIZE = 13f;

	public static final boolean RESIZABLE = true;

	public static int MODEL = MODEL_VALUE.UNKNOWN;

	public static class MODEL_VALUE {
		public static final int UNKNOWN = 0x00;
		public static final int GUI = 0x01;
		public static final int CUI = 0x02;
	}

	public static final int FRAME_WIDTH = 640;
	public static final int FRAME_HEIGHT = 640;
	//public static final int INPUT_WIDTH = 640;

	//public static final String HINT = "在这里输入指令";
	public static String Language;

	public static String getLanguage() {
		return Language;
	}

	public static void setLanguage(String language) {
		if (language.contains("zh_CN") || language.contains("zh_SG"))
			Language = "zh_CN";
		else if (language.contains("zh_TW") || language.contains("zh_HK") || language.contains("zh_MO"))
			Language = "zh_TW";
		else Language = "zh_CN";
	}
}
