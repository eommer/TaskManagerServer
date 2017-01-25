import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

	public String userID;
	public String nom;
	public String prenom;
	public String mail;
	public String mdp;
	public ArrayList<Tache> lstTachesRea = new ArrayList<Tache>();
	public ArrayList<Tache> lstTachesCrea = new ArrayList<Tache>();
	
	public User(){
		
	}
	
	public User(String userID, String nom, String prenom, String mail, String password){
		this.userID = userID;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mdp = password;
	}

}
