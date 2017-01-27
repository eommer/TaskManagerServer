import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Tache;


public class XMLHandlerTache extends DefaultHandler{

   public Tache tache;
   private String nameBalise = "";
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

   public void startDocument() throws SAXException {
      tache = new Tache();
   }

   public void endDocument() throws SAXException {
   }   

   public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) throws SAXException {
	   this.nameBalise = qname;
   }
   
   public void endElement(String uri, String localName, String qName)throws SAXException{
	   
   }
   
   public void characters(char[] data, int start, int end){   
	   String str = new String(data, start, end).trim();
	   
		  if(str.isEmpty()){
			  return;
		  }
		  else{
			  if(this.nameBalise == "id"){
				  tache.tacheID = str; 
			  }
			  else if(this.nameBalise == "titre"){
				  tache.titre = str; 
			  }
			  else if(this.nameBalise == "texte"){
				  tache.texte = str; 
			  }
			  else if(this.nameBalise == "priorite"){
				  tache.priorite = str; 
			  }
			  else if(this.nameBalise == "etat"){
				  tache.etat = str; 
			  }
			  else if(this.nameBalise == "createur"){
				  tache.idCreateur = str; 
			  }
			  else if(this.nameBalise == "realisateur"){
				  tache.idRealisateur = str; 
			  }
			  
			  else if(this.nameBalise == "dateCreation"){
				  tache.dateCreation = LocalDate.parse(str, formatter); 
			  }
			  else if(this.nameBalise == "dateFin"){
				  tache.dateFin = LocalDate.parse(str, formatter); 
			  }
		  }
   }

}
