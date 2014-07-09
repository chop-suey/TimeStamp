package csv;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;

import ch.woggle.util.DateFormat;
import ch.woggle.util.Settings;
import model.Timestamp;

public class TimestampCSV {
	private static final String CSV_VALUE_SEPARATOR = Settings.getString("CSV_VALUE_SEPARATOR");
	private static final String CSV_FILE_SUFFIX = Settings.getString("CSV_FILE_SUFFIX");
	private Collection<Timestamp> timestamps;
	
	public TimestampCSV() {
		timestamps = new LinkedList<Timestamp>();
	}
	
	public void add(Timestamp timestamp) {
		timestamps.add(timestamp);
	}
	
	public void addAll(Collection<Timestamp> timestamps) {
		this.timestamps.addAll(timestamps);
	}
	
	public void export(String name) throws FileNotFoundException {
		if(!name.endsWith(CSV_FILE_SUFFIX)) {
			name = name + CSV_FILE_SUFFIX;
		}
		PrintWriter writer = new PrintWriter(name);
		for(Timestamp timestamp : timestamps) {
			writer.println(getCSVLine(timestamp));
		}
		writer.close();
	}
	
	private String getCSVLine(Timestamp timestamp) {
		String date = DateFormat.getDateString(timestamp.getStartTime());
		String startTime = DateFormat.getDayTimeString(timestamp.getStartTime());
		String stopTime = DateFormat.getDayTimeString(timestamp.getStopTime());
		return 	date + CSV_VALUE_SEPARATOR +
				startTime + CSV_VALUE_SEPARATOR +
				stopTime + CSV_VALUE_SEPARATOR +
				timestamp.getDiffHours() + CSV_VALUE_SEPARATOR +
				timestamp.getTopic();
	}
}
