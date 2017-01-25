import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.XMLStreamException;

public class ValidationThread extends Thread{

	private Socket socketClient;
	private Tache task;
	private User userCreat;
	private User userRea;
	private User user;
	
	public ValidationThread(Socket socket){
		socketClient = socket;
	}
	
	public void run(){
		
		try {
			
			System.out.println("VALIDATION");
			Thread.sleep(20);
			ObjectInputStream objectIn = new ObjectInputStream(socketClient.getInputStream());
			task = (Tache) objectIn.readObject();
			
			SaxParserUser saxParserUser = new SaxParserUser();
			
			/* Récupère l'utilisateur qui a créé la tache et celui qui doit la réaliser */
			if(!task.idCreateur.equals(task.idRealisateur)){
				userCreat = saxParserUser.ParserUser(task.idCreateur);
				userRea = saxParserUser.ParserUser(task.idRealisateur);

			}
			else{
				userCreat = saxParserUser.ParserUser(task.idCreateur);
				userRea = userCreat;
			}
			
			
			/* Ajoute la tache aux deux utilisateurs */
			
			System.out.println("ID DE LA TACHE : " + task.tacheID);
			
			//Supprime la tache si elle éxiste déja chez l'utilisateur pour y mettre la version à jour
			ArrayList<Tache> temporaryCreaLst = new ArrayList<Tache>();
			
			for(Tache t : userCreat.lstTachesCrea){
				System.out.println("check task");
				if(t.tacheID.equals(task.tacheID)){
					System.out.println("tache deja présente ds Créa");
				}
				else{
					temporaryCreaLst.add(t);
				}
			}
			
			temporaryCreaLst.add(task);
			
			System.out.println("/////////////////**************");
			for(Tache t : temporaryCreaLst){
				System.out.println(t);
			}
			System.out.println("/////////////////**************");
			
			userCreat.lstTachesCrea.clear();
			userCreat.lstTachesCrea = temporaryCreaLst;
			
			temporaryCreaLst.clear();
			
			for(Tache t : userRea.lstTachesRea){
				System.out.println("check task");
				if(t.tacheID.equals(task.tacheID)){
					System.out.println("tache deja présente ds Réa");
				}
				else{
					temporaryCreaLst.add(t);
				}
			}
			
			temporaryCreaLst.add(task);
			userRea.lstTachesRea.clear();
			userRea.lstTachesRea = temporaryCreaLst;
			
			

			
			User user = userCreat;
			
			System.out.println("ID : " + user.userID);
			System.out.println("Nom : " + user.nom);
			System.out.println("Prenom : " + user.prenom);
			System.out.println("Email : " + user.mail);
			System.out.println("Mdp : " + user.mdp);
			for (Tache t : user.lstTachesRea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache a réaliser : " + t);
			}
			for (Tache t : user.lstTachesCrea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache créée : " + t);
			}
			
			System.out.println("*********************************************************************");
			
			user = userRea;
			
			System.out.println("ID : " + user.userID);
			System.out.println("Nom : " + user.nom);
			System.out.println("Prenom : " + user.prenom);
			System.out.println("Email : " + user.mail);
			System.out.println("Mdp : " + user.mdp);
			for (Tache t : user.lstTachesRea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache a réaliser : " + t);
			}
			for (Tache t : user.lstTachesCrea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache créée : " + t);
			}
			
			
			UserXMLWriter userXmlWriter = new UserXMLWriter();
			userXmlWriter.writeUser(userCreat.userID, userCreat);
			userXmlWriter.writeUser(userRea.userID, userRea);
			
			this.socketClient.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
