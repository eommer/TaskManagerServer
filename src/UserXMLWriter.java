import java.io.FileOutputStream;
import java.io.IOException;
import model.Tache;
import model.User;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class UserXMLWriter {
	
	public XMLOutputFactory factory;
	XMLStreamWriter writer;
	
	public void writeUser(String id, User user) throws XMLStreamException, IOException {
		
		factory = XMLOutputFactory.newInstance();
		 
	    writer = factory.createXMLStreamWriter(new FileOutputStream("Users/" + id +".xml"));
	    
	    writer.writeStartDocument();
	    writer.writeStartElement("user");
	    writeElt("id", user.userID);
	    writeElt("nom", user.nom);
	    writeElt("prenom", user.prenom);
	    writeElt("mail", user.mail);
	    writeElt("mdp", user.mdp);
	    writer.writeStartElement("lstTaches");
	    for(Tache tc : user.lstTachesRea){
	    	System.out.println("REALISATION");
	    	writeElt("tacheRea", tc.tacheID);
	    	new TacheXMLWriter().writeTache(tc.tacheID, tc);
	    }
	    for(Tache tc : user.lstTachesCrea){
	    	System.out.println("CREATION");
	    	writeElt("tacheCrea", tc.tacheID);
	    	new TacheXMLWriter().writeTache(tc.tacheID, tc);
	    }
	    writer.writeEndElement();
	    writer.writeEndElement();
	    writer.writeEndDocument();
	    writer.flush();
		
	}
	
	private void writeElt(String balise, String param) throws XMLStreamException{
		writer.writeStartElement(balise);
		writer.writeCharacters(param);
		writer.writeEndElement();
	}
	

	
}
