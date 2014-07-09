package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ch.woggle.util.DateFormat;
import ch.woggle.util.Language;
import model.Timestamp;

public class TimestampTableModel implements TableModel {
	private String[] columnNames = {Language.get("TABLE_CH_DATE"), 
									Language.get("TABLE_CH_START"), 
									Language.get("TABLE_CH_STOP"), 
									Language.get("TABLE_CH_DIFF"), 
									Language.get("TABLE_CH_SUBJECT") };
	private Class<?>[] columnClass = { String.class, String.class, String.class, Double.class, String.class };
	private List<Timestamp> timestamps;
	
	public TimestampTableModel(Collection<Timestamp> timestamps) {
		this.timestamps = new ArrayList<Timestamp>(timestamps);
	}
	
	@Override
	public int getRowCount() {
		return timestamps.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		Timestamp timestamp = timestamps.get(rowIndex);
		switch(columnIndex) {
		case 0: // Datum
			value = DateFormat.getDateString(timestamp.getStartTime());
			break;
		case 1: // Start
			value = DateFormat.getDayTimeString(timestamp.getStartTime());
			break;
		case 2: // Stop
			value = DateFormat.getDayTimeString(timestamp.getStopTime());
			break;
		case 3: // Diff
			value = timestamp.getDiffHours();
			break;
		case 4: // topic
			value = timestamp.getTopic();
			break;
		}
		return value;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}
}
