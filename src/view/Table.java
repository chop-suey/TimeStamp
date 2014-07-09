package view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;
import ch.woggle.util.Language;
import model.Timestamp;

public class Table {
	private JDialog tableDialog;
	private JTable table;
	private Controller controller;
	private List<Timestamp> timestamps;
	
	public Table(JFrame parent, Controller controller, List<Timestamp> timestamps) {
		tableDialog = new JDialog(parent, Language.get("TABLE_TITLE"), Dialog.ModalityType.APPLICATION_MODAL);
		this.controller = controller;
		this.timestamps = timestamps;
		initFrame();
	}
	
	public void display() {
		tableDialog.setVisible(true);
	}
	
	private final void initFrame() {
		tableDialog.setLayout(new BorderLayout());
		tableDialog.add(getActionPanel(), BorderLayout.EAST);
		tableDialog.add(getTableScrollPane(timestamps), BorderLayout.CENTER);
		tableDialog.pack();
	}
	
	private JComponent getExportButton() {
		JButton button = new JButton(Language.get("EXPORT_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exportList();
			}
		});
		return button;
	}
	
	private JComponent getEditButton() {
		JButton button = new JButton(Language.get("EDIT_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.editTimestamp(tableDialog);	
			}
		});
		return button;
	}
	
	private JComponent getTableScrollPane(Collection<Timestamp> timestamps) {
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setSelectedTimestamp();
			}
		});
		table.setModel(new TimestampTableModel(timestamps));
		return new JScrollPane(table);
	}
	
	private void setSelectedTimestamp() {
		int index = table.getSelectionModel().getLeadSelectionIndex();
		controller.setSelectedTimestamp(timestamps.get(index));
	}
	
	private JComponent getActionPanel() {
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.add(getExportButton());
		actionPanel.add(getEditButton());
		return actionPanel;
	}
}
