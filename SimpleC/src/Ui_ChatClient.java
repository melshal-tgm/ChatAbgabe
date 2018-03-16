

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ChatClient implements com.trolltech.qt.QUiForm<QDialog> {
	public QWidget verticalLayoutWidget;
	public QVBoxLayout mainLayout;
	public QListWidget messages;
	public QHBoxLayout inputLayout;
	public QLineEdit input;
	public QPushButton sendButton;
	public Client client;

	public Ui_ChatClient() {
		super();
	}

	public void setupUi(QDialog ChatClient) {
		ChatClient.setObjectName("ChatClient");
		ChatClient.resize(new QSize(455, 381).expandedTo(ChatClient.minimumSizeHint()));
		QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding,
				com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding);
		sizePolicy.setHorizontalStretch((byte) 0);
		sizePolicy.setVerticalStretch((byte) 0);
		sizePolicy.setHeightForWidth(ChatClient.sizePolicy().hasHeightForWidth());
		ChatClient.setSizePolicy(sizePolicy);
		verticalLayoutWidget = new QWidget(ChatClient);
		verticalLayoutWidget.setObjectName("verticalLayoutWidget");
		verticalLayoutWidget.setGeometry(new QRect(10, 10, 437, 362));
		mainLayout = new QVBoxLayout(verticalLayoutWidget);
		mainLayout.setSpacing(10);
		mainLayout.setObjectName("mainLayout");
		mainLayout.setContentsMargins(10, 10, 10, 10);
		messages = new QListWidget(verticalLayoutWidget);
		messages.setObjectName("messages");
		messages.setMinimumSize(new QSize(400, 300));

		mainLayout.addWidget(messages);

		inputLayout = new QHBoxLayout();
		inputLayout.setSpacing(10);
		inputLayout.setObjectName("inputLayout");
		inputLayout.setContentsMargins(0, -1, -1, -1);
		input = new QLineEdit(verticalLayoutWidget);
		input.setObjectName("input");
		input.setMinimumSize(new QSize(350, 30));

		inputLayout.addWidget(input);

		sendButton = new QPushButton(verticalLayoutWidget);
		sendButton.setObjectName("sendButton");
		sendButton.setMinimumSize(new QSize(45, 30));

		inputLayout.addWidget(sendButton);

		mainLayout.addLayout(inputLayout);
		retranslateUi(ChatClient);
		ChatClient.connectSlotsByName();
	} // setupUi

	void retranslateUi(QDialog ChatClient) {
		ChatClient.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ChatClient", "Chat Client", null));
		sendButton.setText(com.trolltech.qt.core.QCoreApplication.translate("ChatClient", "Send", null));
		sendButton.clicked.connect(this, "send()");

	}

	public void send() {
		String msg = input.text();
		client.sendMessage(msg);
		input.clear();
	}

	// retranslateUi
	public static void main(String[] args) {
		QApplication.initialize(args);
		QDialog d = new QDialog();
		Ui_ChatClient ui = new Ui_ChatClient();
		ui.setupUi(d);
		d.show();
		QApplication.execStatic();
	}

}
