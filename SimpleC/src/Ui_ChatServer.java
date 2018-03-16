

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ChatServer implements com.trolltech.qt.QUiForm<QDialog> {
	public QWidget horizontalLayoutWidget;
	public QHBoxLayout mainLayout;
	public QListWidget messages;
	public QVBoxLayout userLayout;
	public QListWidget clients;
	public QPushButton disconnect;
	public Server server = new Server(5050, this);;

	public Ui_ChatServer() {
		super();
	}

	public void setupUi(QDialog ChatServer) {
		ChatServer.setObjectName("ChatServer");
		ChatServer.resize(new QSize(450, 340).expandedTo(ChatServer.minimumSizeHint()));
		QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding,
				com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding);
		sizePolicy.setHorizontalStretch((byte) 0);
		sizePolicy.setVerticalStretch((byte) 0);
		sizePolicy.setHeightForWidth(ChatServer.sizePolicy().hasHeightForWidth());
		ChatServer.setSizePolicy(sizePolicy);
		ChatServer.setSizeGripEnabled(false);
		ChatServer.setModal(false);
		horizontalLayoutWidget = new QWidget(ChatServer);
		horizontalLayoutWidget.setObjectName("horizontalLayoutWidget");
		horizontalLayoutWidget.setGeometry(new QRect(10, 10, 432, 322));
		mainLayout = new QHBoxLayout(horizontalLayoutWidget);
		mainLayout.setSpacing(10);
		mainLayout.setObjectName("mainLayout");
		mainLayout.setContentsMargins(10, 10, 10, 10);
		messages = new QListWidget(horizontalLayoutWidget);
		messages.setObjectName("messages");
		QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding,
				com.trolltech.qt.gui.QSizePolicy.Policy.Ignored);
		sizePolicy1.setHorizontalStretch((byte) 0);
		sizePolicy1.setVerticalStretch((byte) 0);
		sizePolicy1.setHeightForWidth(messages.sizePolicy().hasHeightForWidth());
		messages.setSizePolicy(sizePolicy1);
		messages.setMinimumSize(new QSize(300, 300));
		messages.setMaximumSize(new QSize(16777215, 16777215));
		messages.setBaseSize(new QSize(300, 400));

		mainLayout.addWidget(messages);

		userLayout = new QVBoxLayout();
		userLayout.setSpacing(10);
		userLayout.setObjectName("userLayout");
		userLayout.setContentsMargins(0, -1, -1, -1);
		clients = new QListWidget(horizontalLayoutWidget);
		clients.setObjectName("clients");
		clients.setEnabled(true);
		QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding,
				com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding);
		sizePolicy2.setHorizontalStretch((byte) 0);
		sizePolicy2.setVerticalStretch((byte) 0);
		sizePolicy2.setHeightForWidth(clients.sizePolicy().hasHeightForWidth());
		clients.setSizePolicy(sizePolicy2);
		clients.setMinimumSize(new QSize(100, 270));
		clients.setMaximumSize(new QSize(16777215, 16777215));
		clients.setBaseSize(new QSize(0, 0));
		clients.setSortingEnabled(true);

		userLayout.addWidget(clients);

		disconnect = new QPushButton(horizontalLayoutWidget);
		disconnect.setObjectName("disconnect");
		QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding,
				com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding);
		sizePolicy3.setHorizontalStretch((byte) 0);
		sizePolicy3.setVerticalStretch((byte) 0);
		sizePolicy3.setHeightForWidth(disconnect.sizePolicy().hasHeightForWidth());
		disconnect.setSizePolicy(sizePolicy3);
		disconnect.setMinimumSize(new QSize(100, 20));
		disconnect.setMaximumSize(new QSize(100, 20));

		userLayout.addWidget(disconnect);

		mainLayout.addLayout(userLayout);

		retranslateUi(ChatServer);

		ChatServer.connectSlotsByName();
		//new ServerRun().start();
	} // setupUi

	void retranslateUi(QDialog ChatServer) {
		ChatServer.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ChatServer", "Chat Server", null));
		disconnect.setText(com.trolltech.qt.core.QCoreApplication.translate("ChatServer", "Disconnect User", null));
	} // retranslateUi

	public static void main(String[] args) {
		QApplication.initialize(args);
		QDialog d = new QDialog();
		Ui_ChatServer ui = new Ui_ChatServer();
		ui.setupUi(d);
		d.show();
		QApplication.execStatic();
	}

}
