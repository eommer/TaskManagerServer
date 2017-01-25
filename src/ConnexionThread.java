import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnexionThread extends Thread {

	public User user;
	private SaxParserUser saxParserUser;
	private String mail = "";
	private String mdp = "";
	private String id;
	private Socket socketClient;

	public ConnexionThread(Socket socket) {
		socketClient = socket;
	}

	public void run() {

		try {
			System.out.println("CONNEXION");
			BufferedReader inFromClient;
			inFromClient = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			OutputStream out = socketClient.getOutputStream();

			this.mail = inFromClient.readLine();
			this.mdp = inFromClient.readLine();
			
			System.out.println(mail);
			System.out.println(mdp);

			saxParserUser = new SaxParserUser();
			String idToSearch = new SaxParserAllUsers().ParserUserId(mail);
			System.out.println(idToSearch);
			user = saxParserUser.ParserUser(idToSearch);

			// Envoie de l'utilisateur récuperé			
			DataOutputStream outToClient = new DataOutputStream(out);
			
			if(user != null){
				
				System.out.println("USER FOUND");
				
				outToClient.writeBytes("OK\n");
				ObjectOutputStream objectOut = new ObjectOutputStream(out);
				objectOut.writeObject(user);
	            objectOut.close();
	         // AFFICHAGE USER RECUPERE

				System.out.println("ID : " + user.userID);
				System.out.println("Nom : " + user.nom);
				System.out.println("Prenom : " + user.prenom);
				System.out.println("Email : " + user.mail);
				System.out.println("Mdp : " + user.mdp);
				for (Tache t : user.lstTachesRea) {
					System.out.println("Tache a réaliser : " + t);
				}
				for (Tache t : user.lstTachesCrea) {
					System.out.println("Tache créée : " + t);
				}

			}
			else{
				
				System.out.println("USER NOT FOUND");
				
				outToClient.writeBytes("NO USER\n");
			}

            socketClient.close();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
