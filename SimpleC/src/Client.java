

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.trolltech.qt.gui.QApplication;

public class Client {

  private int port;
  private String host;
  private Ui_ChatClient cg;// gui
  private Socket socket;
  private ObjectInputStream sInput;
  private ObjectOutputStream sOutput;
  /**
   * Konstuktor
   */
  public Client(int port, String host, Ui_ChatClient cg) {
    this.port = port;
    this.host = host;
    this.cg = cg;
  }
  /**
   * Erstellt den Socket für Host und Port
   * Startet den Client und erstellt die Streams
   * 
   * @return
   */
  public boolean start() {
    try {
      socket= new Socket(host, port);
      sInput= new ObjectInputStream(socket.getInputStream());
      sOutput= new ObjectOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    new Listener().start(); //Neuer Listener Thread
    return true;
    
  }
  /**
   * Sendet eine Nachricht
   */
  public void sendMessage(String message) {
    try {
      sOutput.writeObject(message);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /**
   * Schließt offene Verbindungen
   */
  public void disconnect() {
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
   * Listener Thread von oben Zeile 41
   * @author Elshal
   *
   */
  class Listener extends Thread {
	/**
	 * Beim Starten des Threads wird diese Methode ausgeführt
	 * Versucht eine Nachricht einzulesen und diese der Chatbox hinzu zufügen.
	 */
    public void run() {
      while (true) {
        try {
          String incomingMsg = (String) sInput.readObject();
          QApplication.invokeLater(new Runnable() {
            @Override
            public void run() {
              cg.messages.addItem(incomingMsg); // fuegt nachricht in die Chatbox
            }
          });
        } catch (ClassNotFoundException | IOException e) {
          // TODO Auto-generated catch block
          break;
        }
      }
    }
  }
}