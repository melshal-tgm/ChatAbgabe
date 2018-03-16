

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;

public class ControllerC {
	public static void main(String[] args) {
		QApplication.initialize(args);
		QDialog d = new QDialog();
		Ui_ChatClient ui = new Ui_ChatClient();
		ui.client = new Client(5050, "localhost", ui);
		ui.setupUi(d);
		d.show();
		ui.client.start();
		QApplication.execStatic();
	}
}
