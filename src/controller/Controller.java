package controller;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JDialog;

import ch.woggle.util.FileUtil;
import ch.woggle.util.Settings;
import csv.TimestampCSV;
import view.EditTimestampDialog;
import view.MainView;
import view.Table;
import model.Timestamp;
import dao.TimeStampDAO;

public class Controller {
	private static final int MAX_EXPORT_TRIES = Settings.getInt("MAX_EXPORT_TRIES");
	private static final int ACCURACY = Settings.getInt("ACCURACY");
	
	private TimeStampDAO dao;
	private Timestamp timestamp, tableSelectedTimestamp;
	private MainView view;
	
	public Controller(TimeStampDAO dao) {
		this.dao = dao;
	}
	
	public void setMainView(MainView view) {
		this.view = view;
	}
	
	public void startTime() {
		if(timestamp != null && timestamp.isRunning()) {
			stopTime();
		}
		timestamp = new Timestamp();
		Timestamp.setAccuracy(ACCURACY);
		timestamp.setTopic(view.promptTopic());
		
		timestamp.setId(dao.add(timestamp));
		view.updateStartTime(timestamp.getStartTime());
		view.updateTopic(timestamp.getTopic());
		view.updateStopTime(null);
	}
	
	public void stopTime() {
		timestamp.setStopTime(new Date());
		dao.update(timestamp);
		view.updateStopTime(timestamp.getStopTime());
	}
	
	public void showList() {
		Table table = new Table(view, this, new LinkedList<Timestamp>(dao.findAll()));
		table.display();
	}
	
	public void exportList() {
		boolean success = false;
		int maxTries = MAX_EXPORT_TRIES;
		
		TimestampCSV csv = new TimestampCSV();
		csv.addAll(dao.findAll());
		
		while(!success && maxTries > 0) {
			try {
				csv.export(FileUtil.getValidFileName(view.promptFileName()));
				success = true;
			} catch(FileNotFoundException e) {
				e.printStackTrace();
				maxTries--;
			}
		}
	}
	
	public void editTimestamp(JDialog parent) {
		if(tableSelectedTimestamp != null) {
			EditTimestampDialog dialog = new EditTimestampDialog(parent, this, tableSelectedTimestamp);
			dialog.display();
		}
	}
	
	public void setSelectedTimestamp(Timestamp timestamp) {
		tableSelectedTimestamp = timestamp;
	}
	
	public void updateEditedTimestamp(Timestamp timestamp) {
		dao.update(timestamp);
	}
	
	public void exit() {
//		System.out.println("Exit");
		System.exit(0);
	}
}
