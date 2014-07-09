package view;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;
import model.Timestamp;
import ch.woggle.util.DateFormat;
import ch.woggle.util.Language;

public class EditTimestampDialog {
	private Controller controller;
	
	private JTextField start, stop, topic;
	private Timestamp timestamp;
	private JDialog editDialog;
	
	public EditTimestampDialog(JDialog parent, Controller controller, Timestamp timestamp) {
		this.controller = controller;
		this.timestamp = timestamp;
		initTextFields();
		initDialog(parent);
	}
	
	public void display() {
		editDialog.setVisible(true);
	}
	
	private final void initDialog(JDialog parent) {
		editDialog = new JDialog(parent, Language.get("EDIT_TIMESTAMP_TITLE"), Dialog.ModalityType.APPLICATION_MODAL);
		editDialog.add(getEditDialogPanel());
		
		
		editDialog.pack();
	}
	
	private final JPanel getEditDialogPanel() {
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
		
		editPanel.add(new JLabel("ID: " + timestamp.getId()));
		editPanel.add(new JLabel(Language.get("STARTTIME_LABEL")));
		editPanel.add(start);
		editPanel.add(new JLabel(Language.get("STOPTIME_LABEL")));
		editPanel.add(stop);
		editPanel.add(new JLabel(Language.get("TOPIC_LABEL")));
		editPanel.add(topic);
		editPanel.add(getSaveButton());
		
		return editPanel;
	}
	
	private final void initTextFields() {
		initStartTimeTextField();
		initStopTimeTextField();
		initTopicTextField();
	}
	
	private final void initStartTimeTextField() {
		start = new JTextField(DateFormat.getDateTimeString(timestamp.getStartTime()));
	}
	
	private final void initStopTimeTextField() {
		stop = new JTextField(DateFormat.getDateTimeString(timestamp.getStopTime()));
	}
	
	private final void initTopicTextField() {
		topic = new JTextField(timestamp.getTopic());
	}
	
	private final JComponent getSaveButton() {
		JButton button = new JButton(Language.get("EDIT_TIMESTAMP_SAVE_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChanges();
				// close dialog
				editDialog.dispose(); 
			}
		});
		return button;
	}
	
	private void saveChanges() {
		try {
			timestamp.setStartTime(DateFormat.parseDateTimeString(start.getText()));
			timestamp.setStopTime(DateFormat.parseDateTimeString(stop.getText()));
		} catch(ParseException e) {
			e.printStackTrace();
		}
		timestamp.setTopic(topic.getText());
		controller.updateEditedTimestamp(timestamp);
	}
}
