

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.trolltech.qt.gui.QApplication;
/**
 * Server für die Client über den die Nachrichten geschickt werden.
 * @author Elshal
 *
 */
public class Server {
	private static int connectionid; //jeder Client eine ID
	private int port;// Portadresse
	private boolean keepGoing; 
	private Ui_ChatServer sg;// ServerGUI
	private ArrayList<ClientThread> al; //Arraylist für die Clients

	/**
	 * Konstruktor
	 */
	public Server(int port, Ui_ChatServer sg) {
		this.port = port;
		this.sg = sg;
		al = new ArrayList<ClientThread>();
	}
	/**
	 * Eine synchronisierte Broadcast Methode.
	 * Die ist für das Anzeigen der Nachricht bei allen Teilnehmern zuständig.
	 * Der Server ist auch ein Teilnehmer.
	 * @param msg Die Nachricht die angezeigt wird
	 */
	public synchronized void broadcast(String msg) { // sync funktioniert so wie ein LOCK !!!!!!!
		 // Nachricht in die GUI
		QApplication.invokeLater(new Runnable() { //wird spaeter ausgefuehrt
			@Override
			public void run() {
				sg.messages.addItem(msg);
			}
		});
		// Rueckwaerts loop, falls jemand dc ist und wir ihn kicken muessen
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// versucht nachricht an client zu schicken falls false remove
			if (!ct.writeMsg(msg))
				al.remove(i);
		}

	}
	/**
	 * Synchronized Methode zum Entfernen von Connections
	 * Also ClientsThreads
	 * 
	 * @param id Thread der entfernt wird.
	 */
	public synchronized void remove(int id) {
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}
	/**
	 * Protected für Project erreichbar aber nicht von außerhalb.
	 * Stopped den Server
	 */
	protected void stop() {
		keepGoing = false;
	}
	/**
	 * Startet den ServerSocket und wartet auf Clients
	 * Bis keepgoing false gesetzt wird
	 * Die Threads werden hinzugefügt und gestartet
	 */
	public void start() {
		keepGoing = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (keepGoing) {
				// wartet auf einen client bis es accept machen kann
				Socket socket = serverSocket.accept();
				if (!keepGoing)
					break;
				
				ClientThread t = new ClientThread(socket);
				al.add(t);// fügt client in die ArrayList hinzu
				t.start();// startet Thread
			}
			serverSocket.close();

			for (int i = 0; i < al.size(); ++i) {
				ClientThread ct = al.get(i);
				ct.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Client Threads
	 * 
	 * @author Elshal
	 *
	 */
	public class ClientThread extends Thread {
		Socket socket;
		int id;
		String cm;// ChatMessage
		ObjectOutputStream sOutput;
		ObjectInputStream sInput;
		/**
		 * Der Thread bekommt eine Id
		 *  Input und Output werden erstellt und greifen auf den Socket zu
		 * @param socket Socket für IO
		 */
		public ClientThread(Socket socket) {
			id = ++connectionid;// ++ Berechnung vorher
			this.socket = socket;
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * Wird beim Starten des Threads ausgeführt
		 * Nachricht wird über socketInput eingelesen
		 * Falls kein Fehler unterläuft wird die Nachricht an alle gesendet
		 * Wenn Connection geschlossen wird mittels KeepGoing wird der Thread entfernt und geschlossen
		 */
		public void run() {
			boolean keepGoing = true;
			while (keepGoing) {
				try {
					cm = (String) sInput.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				// sendet an alle anderen
				broadcast(cm);
			}
			// remove client id
			remove(id);
			close();
		}

		/**
		 * schließen von Input/Output und socket
		 */
		private void close() {
			// TODO Auto-generated method stub

			try {
				if (sOutput != null)
					sOutput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (sInput != null)
					sInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * Message write
		 */
		private boolean writeMsg(String msg) {
			if (!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}

}