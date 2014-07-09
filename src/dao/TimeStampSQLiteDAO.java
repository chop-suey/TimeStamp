package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import model.Timestamp;

public class TimeStampSQLiteDAO implements TimeStampDAO {
	Connection con;
	
	public TimeStampSQLiteDAO(String dbFile) {
		con = null;
		boolean newDB = !(new File(dbFile).isFile());
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		if(con != null) {
//			System.out.println("Opened database successfully");
			if(newDB) {
				initDB();
			}
		}
	}

	@Override
	public int add(Timestamp timestamp) {
		int key = 0;
		String query = "INSERT INTO Timestamp (start, stop, topic) VALUES (?, ?, ?)";
		try(PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setLong(1, getSeconds(timestamp.getStartTime()));
			stmt.setLong(2, getSeconds(timestamp.getStopTime()));
			stmt.setString(3, timestamp.getTopic());
			stmt.executeUpdate();
			key = getGeneratedKey(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	private int getGeneratedKey(Statement stmt) throws SQLException {
		int key = 0;
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			key = rs.getInt(1);
		}
		rs.close();
		return key;
	}

	@Override
	public boolean update(Timestamp timestamp) {
		String query = "UPDATE Timestamp SET start = ?, stop = ?, topic = ? WHERE id = ?";
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setLong(1, getSeconds(timestamp.getStartTime()));
			stmt.setLong(2, getSeconds(timestamp.getStopTime()));
			stmt.setString(3, timestamp.getTopic());
			stmt.setInt(4, timestamp.getId());
			stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Timestamp findByID(int id) {
		Timestamp timestamp = null;
		String query = "SELECT id, start, stop, topic FROM Timestamp WHERE id = ?";
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				timestamp = getTimestampfFromResultSet(rs);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	private Timestamp getTimestampfFromResultSet(ResultSet rs) throws SQLException {
		Timestamp timestamp = null;
		int id = rs.getInt("id");
		long start = rs.getLong("start") * 1000;
		long stop = rs.getLong("stop") * 1000;
		String topic = rs.getString("topic");
		timestamp = new Timestamp(new Date(start), new Date(stop), topic);
		timestamp.setId(id);
		return timestamp;
	}

	@Override
	public Timestamp findNewest() {
		Timestamp timestamp = null;
		
		String query = "SELECT id, start, stop, topic FROM Timestamp ORDER BY start DESC LIMIT 1";
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				timestamp = getTimestampfFromResultSet(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	@Override
	public Collection<Timestamp> findAll() {
		Collection<Timestamp> collection = new LinkedList<Timestamp>();
		String query = "SELECT id, start, stop, topic FROM Timestamp ORDER BY start ASC";
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				collection.add(getTimestampfFromResultSet(rs));
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return collection;
	}
	
	@Override
	public Collection<String> getTopics() {
		Collection<String> topicCollection = new LinkedList<String>();
		
		String query = "SELECT DISTINCT topic FROM Timestamp ORDER BY topic ASC";
		
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				topicCollection.add(rs.getString("topic"));
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return topicCollection;
	}

	private final void initDB() {
		String query = 	"CREATE TABLE Timestamp " +
						"(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						" start	INTEGER	DEFAULT 0, " +
						" stop	INTEGER DEFAULT 0, " +
						" topic CHAR(128) DEFAULT '')";
		try(PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private long getSeconds(Date date) {
		if(date == null) {
			return 0;
		}
		return date.getTime() / 1000;
		
	}
}
