import java.beans.XMLEncoder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import model.Tache;
import model.User;

public class Server{
	
	private static User user;
	private static ServerSocket socketServer;
	private static final int PORT = 1500;
	

	public static void main(String[] args) throws InterruptedException {

		try {
			
			socketServer = new ServerSocket(PORT);

			while (true) {
				Socket connectionSocket;

				connectionSocket = socketServer.accept();
				
				ClientThread clientTh = new ClientThread(connectionSocket);
				clientTh.start();
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

	}

}
