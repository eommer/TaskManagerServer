import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.util.converter.LocalDateStringConverter;

public class ClientTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		/*User user;
		user = testInscription();
		testValidation(user);
		testConnexion(user);*/
		TestValidation();
	}
	
	public static void testConnexion(User user) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
		
		Socket socket = new Socket("localHost", 1500);
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes("CONNEXION\n");
		Thread.sleep(1);
		outToServer.writeBytes("johndo@hotmail.fr\n");
		Thread.sleep(1);
		outToServer.writeBytes("123\n");
		System.out.println("connexion effectuée");
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String msg = in.readLine();
		if(msg.equals("OK")){
			ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
			user = (User) objectIn.readObject();
			
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
		
	}
	
	
	public static User testInscription() throws UnknownHostException, IOException, ClassNotFoundException{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		User user;
		user = new User();
		user.mail="oliver@gmail.com";
		user.mdp="123";
		user.nom="olivier";
		user.prenom="jacques";
		Tache tc = new Tache();
		tc.dateCreation=LocalDate.parse("05/08/2010", formatter);
		tc.dateFin=LocalDate.parse("02/01/2017", formatter);
		tc.priorite="haute";
		tc.tacheID="0008";
		tc.texte="bl";
		user.lstTachesCrea.add(tc);
		
		Socket socket = new Socket("localHost", 1500);
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes("INSCRIPTION\n");
		
		/* ENVOIE DU NOUVEL UTILISATEUR */
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.writeObject(user);
		outputStream.flush();
		
		/* RECUPERATION UTILISATEUR AVEC NEW ID */
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String msg = in.readLine();
		if(msg.equals("OK")){
			
			System.out.println("Utilisateur créé sur le serveur");
			
			ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
			user = (User) objectIn.readObject();
			
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
			System.out.println("Probleme inscription Utilisateur : changer email");
		}
		
		socket.close();
		
		return user;
	}

	public static void TestValidation() throws UnknownHostException, IOException, InterruptedException{
		SaxParserTache saxParserTache = new SaxParserTache();
		Tache task = saxParserTache.ParserTache("0001");
		
		Socket socket = new Socket("localHost", 1500);
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes("VALIDATION\n");
		
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.writeObject(task);
		outputStream.flush();
		
		Thread.sleep(50);
		socket.close();
		
	}

}
