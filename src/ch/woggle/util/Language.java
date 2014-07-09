package ch.woggle.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Language {
	private static Properties lang;
	
	public static void setLanguageFile(String file) throws IOException {
		lang = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
		lang.load(stream);
	}
	
	public static String get(String key) {
		return lang.getProperty(key, "< notfound >");
	}
}
