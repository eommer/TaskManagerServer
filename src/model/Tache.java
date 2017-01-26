package model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Tache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String titre;
	public String tacheID;
	public String texte;
	public String priorite;
	public String etat;
	public String idCreateur;
	public String idRealisateur;
	public LocalDate dateFin;
	public LocalDate dateCreation;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public Tache(){}
	
	public Tache(String titre, String tacheID, String texte, String priorite, String etat, LocalDate dateFin, LocalDate dateCreation, String idCreateur, String idRealisateur){
		this.titre = titre;
		this.tacheID = tacheID;
		this.texte = texte;
		this.priorite = priorite;
		this.etat = etat;
		this.dateFin = dateFin;
		this.dateCreation = dateCreation;
		this.idCreateur = idCreateur;
		this.idRealisateur = idRealisateur;
	}
	
	public String toString(){
		String str = "ID : " + this.tacheID
				+ "Txt : " + this.texte + "\n"
				+ " Priorite : " + this.priorite + "\n"
				+ " Etat : " + this.etat + "\n"
				+ " Date Fin : " + this.dateFin + "\n"
				+ " Date Creation : " + this.dateCreation;
		return str;
	}
	
}
