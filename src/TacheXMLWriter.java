import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import model.Tache;

public class TacheXMLWriter {
	
	public XMLOutputFactory factory;
	XMLStreamWriter writer;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public boolean tacheAlreadyExists(String id){
		
		return new File("Taches/"+id+".xml").exists();
		
	}
	
	public void writeTache(String id, Tache tache) throws XMLStreamException, IOException {
		
		factory = XMLOutputFactory.newInstance();
		 
	    writer = factory.createXMLStreamWriter(new FileOutputStream("Taches/" + id +".xml"));
	    
	    writer.writeStartDocument();
	    writer.writeStartElement("tache");
	    writeElt("id", tache.tacheID);
	    writeElt("titre", tache.titre);
	    writeElt("texte", tache.texte);
	    writeElt("priorite", tache.priorite);
	    writeElt("etat", tache.etat);
	    writeElt("createur", tache.idCreateur);
	    writeElt("realisateur", tache.idRealisateur);
	    writeElt("dateCreation", tache.dateCreation.format(formatter));
	    writeElt("dateFin", tache.dateFin.format(formatter));
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
