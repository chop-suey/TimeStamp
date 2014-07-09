package timestamp;

import java.io.IOException;

import ch.woggle.util.Language;
import ch.woggle.util.Settings;
import controller.Controller;
import dao.TimeStampDAO;
import dao.TimeStampSQLiteDAO;
import view.MainView;

public class TimeStamp {
	public static void main(String[] args) {
		initProperties();
		initLanguageFile();
		TimeStampDAO dao = new TimeStampSQLiteDAO(Settings.getString("dbName"));
		Controller controller = new Controller(dao);
		MainView mView = new MainView(controller);
		controller.setMainView(mView);
		mView.display();
	}

	public static void initProperties() {
		try {
			Settings.setPropertyFile("settings.conf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initLanguageFile() {
		try {
			Language.setLanguageFile(Settings.getString("lang"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
