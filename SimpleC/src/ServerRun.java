

public class ServerRun extends Thread {
	Server server;

	public ServerRun(Server server, Ui_ChatServer ui) {
		this.server = new Server(5050, ui);
	}

	public void run() {
		server.start();
	}
}