import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;


public class XMLHandlerUser extends DefaultHandler{

   public User user;
   private String nameBalise = "";
   private SaxParserTache parserTache;;

   public void startDocument() throws SAXException {
      user = new User();
      parserTache = new SaxParserTache();
      
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
				  user.userID = str; 
			  }
			  else if(this.nameBalise == "nom"){
				  user.nom = str; 
			  }
			  else if(this.nameBalise == "prenom"){
				  user.prenom = str; 
			  }
			  else if(this.nameBalise == "mail"){
				  user.mail = str; 
			  }
			  else if(this.nameBalise == "mdp"){
				  user.mdp = str; 
			  }
			  else if(this.nameBalise == "tacheRea"){
				  Tache tc = parserTache.ParserTache(str);
				  user.lstTachesRea.add(tc);
			  }
			  else if(this.nameBalise == "tacheCrea"){
				  Tache tc = parserTache.ParserTache(str);
				  if(tc != null)
					  user.lstTachesCrea.add(tc);
				  else
					  System.out.println("Tache introuvable ==> non ajoutée");
			  }
		  }
   }

}