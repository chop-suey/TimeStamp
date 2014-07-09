package model;

import java.util.Date;

import ch.woggle.util.DateFormat;

public class Timestamp {
	private static final long MILLIS_PER_MINUTE = 60000;
	private static final String DEFAULT_TOPIC = "";
	
	private boolean running;
	private Date start, stop;
	private int id;
	private static long accuracy;
	private String topic;
	
	public Timestamp(Date start, Date stop, String topic) {
		setStartTime(start);
		setStopTime(stop);
		this.topic = topic;
	}
	
	
	public Timestamp() {
		this(new Date(), null, DEFAULT_TOPIC);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getStartTime() {
		return new Date(getMilliseconds(start));
	}
	
	public final void setStartTime(Date start) {
		running = (start != null);
		this.start = start;
	}
	
	public Date getStopTime() {
		return new Date(getMilliseconds(stop));
	}
	
	public final void setStopTime(Date stop) {
		this.running = (stop == null);
		this.stop = stop;
	}
	
	public static int getAccuracy() {
		return (int)accuracy;
	}
	
	public static void setAccuracy(int accuracy) {
		Timestamp.accuracy = accuracy;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public int getDiffSeconds() {
		if(start == null) {
			start = new Date(0);
		}
		if(stop == null || start.compareTo(stop) > 0) {
			stop = new Date(start.getTime());
		}
		return (int)(getSeconds(stop) - getSeconds(start));
	}
	
	public double getDiffMinutes() {
		return (double)getDiffSeconds() / 60.0;
	}
	
	public double getDiffHours() {
		return (double) getDiffSeconds() / 3600.0;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public String toString() {
		String date = DateFormat.getDateString(start);
		String startTime = DateFormat.getDayTimeString(start);
		String stopTime = DateFormat.getDayTimeString(stop);
		return date + "\t" + startTime + "\t" + stopTime + "\t" + getDiffHours() + "\t" + topic;
	}
	
	private long getSeconds(Date date) {
		return getMilliseconds(date) / 1000;
	}
	
	private long getMilliseconds(Date date) {
		if(date == null) {
			return 0;
		}
		long accuracyMillis = (accuracy == 0 ? 1 : accuracy * MILLIS_PER_MINUTE);
		return accuracyMillis * Math.round(date.getTime() / (double) accuracyMillis);
	}
	
	
}