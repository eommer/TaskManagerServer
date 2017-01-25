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

public class Server{
	
	private static User user;
	private static ServerSocket socketServer;
	private static final int PORT = 1500;
	private static enum Request{CONNEXION, INSCRIPTION, VALIDATION, ACTUALISATION, SUPPRESSION;}

	public static void main(String[] args) throws InterruptedException {

		Request clientRequest;

		try {
			
			socketServer = new ServerSocket(PORT);

			while (true) {
				Socket connectionSocket;

				connectionSocket = socketServer.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				clientRequest = Request.valueOf(in.readLine());
				//Connection d'un utilisateur
				switch(clientRequest){
					case CONNEXION : 
						ConnexionThread coThread = new ConnexionThread(connectionSocket);
						coThread.start();
						break;
					 
					//inscription d'un utilisateur
					case INSCRIPTION :
						InscriptionThread inscThread = new InscriptionThread(connectionSocket);
						inscThread.start();
						break;
					
					//Mise à jour d'une tâche
					case VALIDATION :
						ValidationThread vaThread = new ValidationThread(connectionSocket);
						vaThread.start();
						break;
					
					//Actualisation d'un utilisateur
					case ACTUALISATION : 
						ActualisationThread actThread = new ActualisationThread(connectionSocket);
						actThread.start();
						break;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

	}

}
