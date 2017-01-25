import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ActualisationThread extends Thread{

	public User user;
	private SaxParserUser saxParserUser;
	private String mail = "";
	private String mdp = "";
	private String id;
	private Socket socketClient;

	public ActualisationThread(Socket socket) {
		socketClient = socket;
	}
	
	public void run(){
		
	}
}
