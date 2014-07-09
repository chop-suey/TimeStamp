package ch.woggle.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static Properties properties;
	
	public static void setPropertyFile(String file) throws IOException {
		properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
		properties.load(stream);
	}
	
	public static double getDouble(String key) {
		return Double.parseDouble(properties.getProperty(key));
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key));
	}
	
	public static String getString(String key) {
		return properties.getProperty(key);
	}
}
