package dao;

import java.util.Collection;

import model.Timestamp;

public interface TimeStampDAO {
	public int add(Timestamp timestamp);
	public boolean update(Timestamp timestamp);
	public Timestamp findByID(int id);
	public Timestamp findNewest();
	public Collection<String> getTopics();
	public Collection<Timestamp> findAll();
}
