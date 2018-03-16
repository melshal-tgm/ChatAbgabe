


import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;

public class ControllerS {
	public Server server;
	public Ui_ChatServer ui;

	public ControllerS(Server server, Ui_ChatServer ui) {
		this.server = server;
		this.ui = ui;
	}

	public static void main(String[] args) {
		Ui_ChatServer ui = new Ui_ChatServer();
		Server server = new Server(5050, ui);
		ServerRun run = new ServerRun(server, ui);
		run.start();
		QApplication.initialize(args);
		QDialog d = new QDialog();
		ui.setupUi(d);
		d.show();
		QApplication.execStatic();
	}

}

