package util.error;

import view.GUIConfig;

/**
 * Created by Eldath on 2016/8/11 0011.
 *
 * @author Eldath
 */
public class ArchiveFileUnsupportedException extends Exception {
	private String message = null, level = null;

	public ArchiveFileUnsupportedException(String inputVersion) {
		String currectVersion = GUIConfig.ARCHIVE_V;
		if (inputVersion.compareTo(GUIConfig.ARCHIVE_V) > 0) {
			level="致命错误";
			message = "存档文件超出支持范围！请尝试更新程序！" + "\r\n" +
					"现存文件版本：" + inputVersion +
					"\r\n\t支持文件版本：" + currectVersion;
		} else if (inputVersion.compareTo(currectVersion) < 0) {
			level="警告";
			message = "存档文件版本不在支持范围之内！将覆写存档，您的游戏进度将被重置！" + "\r\n" +
					"现存文件版本：" + inputVersion +
					"\r\n\t支持文件版本：" + currectVersion;
		}
	}

	public String getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
