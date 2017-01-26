import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import model.Tache;
import model.User;

public class ClientThread extends Thread {

	private Socket socketClient;
	private Tache task;
	private User userCreat;
	private User userRea;
	private User user;
	private BufferedReader din;
	private ObjectInputStream ois;
	private DataOutputStream dout;
	private ObjectOutputStream oos;

	private static enum Request {
		CONNEXION, INSCRIPTION, VALIDATION, ACTUALISATION, SUPPRESSION;
	}

	public ClientThread(Socket socket) throws IOException {
		socketClient = socket;

		// Sortie
		OutputStream os = socketClient.getOutputStream();
		dout = new DataOutputStream(os);
		oos = new ObjectOutputStream(os);

		// Entr�e
		InputStream is = socketClient.getInputStream();
		din = new BufferedReader(new InputStreamReader(is));
		ois = new ObjectInputStream(is);

		System.out.println("OK");

	}

	public void run() {
		try {
			System.out.println("THREAD CLIENT STARTED");

			while (!socketClient.isClosed()) {
				Request clientRequest = Request.valueOf(din.readLine());

				// Connection d'un utilisateur
				switch (clientRequest) {
				case CONNEXION:
					Connexion();
					break;

				// inscription d'un utilisateur
				case INSCRIPTION:
					Inscription();
					break;

				// Mise � jour d'une t�che
				case VALIDATION:
					Validation();
					break;

				// Actualisation d'un utilisateur
				case ACTUALISATION:
					Actualisation();
					break;

				// Suppression d'une tache
				case SUPPRESSION:
					Suppression();
					break;
				}

			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("THREAD CLIENT CLOSED");
		}
	}

	/**
	 * M�thode permettant de mettre � jour/ajouter une tache. Re�oie l'objet
	 * tache et g�nere un ID si cette derni�re n'en poss�de pas ou n'�xiste pas
	 * encore et renvoie "OK". Pr�voir un timeOut cot� client car le serveur ne
	 * r�pond rien en cas de probl�me rencontr�.
	 */
	private void Validation() {

		try {

			System.out.println("VALIDATION");
			Thread.sleep(20);
			task = (Tache) ois.readObject();

			SaxParserUser saxParserUser = new SaxParserUser();

			/*
			 * R�cup�re l'utilisateur qui a cr�� la tache et celui qui doit la
			 * r�aliser
			 */
			if (!task.idCreateur.equals(task.idRealisateur)) {
				userCreat = saxParserUser.ParserUser(task.idCreateur);
				userRea = saxParserUser.ParserUser(task.idRealisateur);
			} else {
				userCreat = saxParserUser.ParserUser(task.idCreateur);
				userRea = userCreat;
			}

			TacheXMLWriter tacheXmlWriter = new TacheXMLWriter();

			if (task.tacheID == null || task.tacheID.equals("") || !tacheXmlWriter.tacheAlreadyExists(task.tacheID)) {
				System.out.println("nouvelle tache");
				task.tacheID = UUID.randomUUID().toString();

			}
			System.out.println(task.tacheID);
			tacheXmlWriter.writeTache(task.tacheID, task);

			/* Ajoute la tache aux deux utilisateurs */

			System.out.println("ID DE LA TACHE : " + task.tacheID);

			// Supprime la tache si elle �xiste d�ja chez l'utilisateur pour y
			// mettre la version � jour
			ArrayList<Tache> temporaryCreaLst = new ArrayList<Tache>();

			for (Tache t : userCreat.lstTachesCrea) {
				System.out.println("check task");
				if (t.tacheID.equals(task.tacheID)) {
					System.out.println("tache deja pr�sente ds Cr�a");
				} else {
					temporaryCreaLst.add(t);
				}
			}

			temporaryCreaLst.add(task);

			System.out.println("/////////////////**************");
			for (Tache t : temporaryCreaLst) {
				System.out.println(t);
			}
			System.out.println("/////////////////**************");

			userCreat.lstTachesCrea.clear();
			userCreat.lstTachesCrea = temporaryCreaLst;

			temporaryCreaLst.clear();

			for (Tache t : userRea.lstTachesRea) {
				System.out.println("check task");
				if (t.tacheID.equals(task.tacheID)) {
					System.out.println("tache deja pr�sente ds R�a");
				} else {
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
				System.out.println("Tache a r�aliser : " + t);
			}
			for (Tache t : user.lstTachesCrea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache cr��e : " + t);
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
				System.out.println("Tache a r�aliser : " + t);
			}
			for (Tache t : user.lstTachesCrea) {
				System.out.println("//////////////////////////");
				System.out.println("Tache cr��e : " + t);
			}

			UserXMLWriter userXmlWriter = new UserXMLWriter();
			userXmlWriter.writeUser(userCreat.userID, userCreat);
			userXmlWriter.writeUser(userRea.userID, userRea);

			dout.writeBytes("OK\n");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
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

	/**
	 * M�thode appell�e lorsqu'un client souhaite inscrire un utilisateur.
	 * Re�oie l'objet utilisateur sans ID et renvoie "OK" suivi de l'objet
	 * utilisateur avec un ID ou "Problem parsing Users.xml" si probl�me de
	 * parsing ou "Email already use" si l'email n'est pas disponnible
	 */
	private void Inscription() {
		String newId;
		String returnMsg = "";

		try {

			user = (User) ois.readObject();

			UserXMLWriter xmlWriter = new UserXMLWriter();

			if (new SaxParserAllUsers().ParserUserId(user.mail).equals("User not found")) {

				System.out.println("Email disponnible");
				newId = UUID.randomUUID().toString();
				user.userID = newId;
				System.out.println("New Id : " + user.userID);
				xmlWriter.writeUser(user.userID, user);

				AllUsersXMLWriter allUsersXMLWriter = new AllUsersXMLWriter();
				allUsersXMLWriter.writeUser(user);

				System.out.println("OK");
				dout.writeBytes("OK\n"); // msg de confirmation pr le
											// client

				oos.writeObject(user);
			} else if (new SaxParserAllUsers().ParserUserId(user.mail) == null) {
				dout.writeBytes("Problem parsing Users.xml\n");
			} else {
				System.out.println("Email d�j� utilis�e");
				dout.writeBytes("Email already use\n");
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�thode appell�e lorsqu'un client souhaite se connecter. Re�oie le mail
	 * puis le mdp et renvoie "OK" puis l'utilisateur ou "NO USER" si
	 * l'utilisateur n'�xiste pas
	 */
	private void Connexion() {

		SaxParserUser saxParserUser;
		String mail = "";
		String mdp = "";
		String id = "";

		try {
			System.out.println("CONNEXION");

			mail = din.readLine(); // r�cup�ration mail
			mdp = din.readLine(); // r�cup�ration mdp

			System.out.println(mail);
			System.out.println(mdp);

			/* Recherche si un utilisateur avec cet email �xiste */
			saxParserUser = new SaxParserUser();
			String idToSearch = new SaxParserAllUsers().ParserUserId(mail);
			System.out.println(idToSearch);
			user = saxParserUser.ParserUser(idToSearch);

			// Envoie de l'utilisateur r�cuper�
			if (user != null) {

				System.out.println("USER FOUND");

				dout.writeBytes("OK\n");
				oos.writeObject(user);

				// AFFICHAGE USER RECUPERE

				System.out.println("ID : " + user.userID);
				System.out.println("Nom : " + user.nom);
				System.out.println("Prenom : " + user.prenom);
				System.out.println("Email : " + user.mail);
				System.out.println("Mdp : " + user.mdp);
				
				for (Tache t : user.lstTachesRea) {
					System.out.println("Tache a r�aliser : " + t);
				}
				for (Tache t : user.lstTachesCrea) {
					System.out.println("Tache cr��e : " + t);
				}

			} else {
				System.out.println("USER NOT FOUND");

				dout.writeBytes("NO USER\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�thode appell�e lorsqu'un client souhaite r�cuper� la derni�re version
	 * de son utilisateur enregistr�e sur le serveur. Re�oie l'id de
	 * l'utilisateur puis Renvoie "OK" suivi de l'utilisateur
	 */
	private void Actualisation() {
		try {

			SaxParserUser saxParserUser = new SaxParserUser();
			String id = "";

			System.out.println("ACTUALISATION");

			id = din.readLine(); // r�cup�ration id de l'utilisateur
			user = saxParserUser.ParserUser(id); // R�cup�ration de
													// l'utilisateur enregistr�
													// sur le serveur

			/* Si l'utilisateur �xiste sur le serveur */
			if (user != null) {

				System.out.println("USER FOUND");
				dout.writeBytes("OK\n");
				oos.writeObject(user);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void Suppression() {

	}

}
