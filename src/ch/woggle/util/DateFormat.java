package ch.woggle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	private static final String DATETIME_FORMAT = Settings.getString("DATETIME_FORMAT");
	private static final String DATE_FORMAT = Settings.getString("DATE_FORMAT");
	private static final String DAYTIME_FORMAT = Settings.getString("DAYTIME_FORMAT");
	
	public static String getDateTimeString(Date date) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(date);
	}
	
	public static Date parseDateTimeString(String dateTime) throws ParseException {
		return new SimpleDateFormat(DATETIME_FORMAT).parse(dateTime);
	}
	
	public static String getDateString(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
	
	public static String getDayTimeString(Date date) {
		return new SimpleDateFormat(DAYTIME_FORMAT).format(date);
	}
}
