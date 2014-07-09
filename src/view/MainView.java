package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ch.woggle.util.DateFormat;
import ch.woggle.util.Language;
import ch.woggle.util.Settings;
import controller.Controller;

public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6313156717813295316L;
	
	private static final String WINDOW_TITLE = Settings.getString("WINDOW_TITLE");
	private static final String NO_TIME_PLACEHOLDER = Settings.getString("NO_TIME_PLACEHOLDER");
	
	private Controller controller;
	private JLabel startTime, stopTime, topic;
	
	public MainView(Controller controller) {
		super(WINDOW_TITLE);
		setCloseOperation();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.controller = controller;
		startTime = new JLabel(NO_TIME_PLACEHOLDER);
		stopTime = new JLabel(NO_TIME_PLACEHOLDER);
		topic = new JLabel();
		initView();
	}
	
	public void display() {
		this.setVisible(true);
	}
	
	public void updateStartTime(Date start) {
		startTime.setText(DateFormat.getDateTimeString(start));
	}
	
	public void updateStopTime(Date stop) {
		if(stop == null) {
			stopTime.setText(NO_TIME_PLACEHOLDER);
		} else {
			stopTime.setText(DateFormat.getDateTimeString(stop));
		}
	}
	
	public void updateTopic(String text) {
		topic.setText(text);
	}
	
	public String promptTopic() {
		return JOptionPane.showInputDialog(this, Language.get("ASK_FOR_SUBJECT"));
	}
	
	public String promptFileName() {
		String fileName = null;
		JFileChooser fc = new JFileChooser();
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			fileName = fc.getSelectedFile().getAbsolutePath();
		}
		return fileName;
	}
	
	private final void initView() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		addComponents();
		setSize(180, 240);
	}
	
	private final void setCloseOperation() {
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.exit();
			}
		});
	}
	
	private final void addComponents() {
		this.add(getStartButton());
		this.add(new JLabel(Language.get("STARTTIME_LABEL")));
		this.add(startTime);
		this.add(topic);
		this.add(getStopButton());
		this.add(new JLabel(Language.get("STOPTIME_LABEL")));
		this.add(stopTime);
		this.add(Box.createVerticalGlue());
		this.add(getListButton());
		this.add(Box.createVerticalGlue());
		this.add(getExitButton());
	}
	
	private final JButton getStartButton() {
		JButton button = new JButton(Language.get("START_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.startTime();
			}
		});
		return button;
	}
	
	private final JButton getStopButton() {
		JButton button = new JButton(Language.get("STOP_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.stopTime();
			}
		});
		return button;
	}
	
	private final JButton getListButton() {
		JButton button = new JButton(Language.get("LIST_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showList();
			}
		});
		return button;
	}
	
	private final JButton getExitButton() {
		JButton button = new JButton(Language.get("EXIT_BUTTON"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		return button;
	}
}
