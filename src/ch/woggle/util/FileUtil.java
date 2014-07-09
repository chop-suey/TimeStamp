package ch.woggle.util;

import java.io.File;

public class FileUtil {
	public static String getValidFileName(String fileName) {
		if(fileName == null) {
			fileName = "";
		}
		fileName = fileName.trim();
		if(fileName == "" || fileName.startsWith(".")) {
			fileName = Settings.getString("DEFAULT_EXPORT_FILENAME") + fileName;
		} else if(fileName.endsWith(File.separator)) {
			fileName += Settings.getString("DEFAULT_EXPORT_FILENAME");
		}
		return fileName;
	}
}
