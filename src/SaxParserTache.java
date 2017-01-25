import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;

import jdk.internal.org.xml.sax.SAXException;

public class SaxParserTache {
	
	private String nameXMLFile ="";
	
	public Tache ParserTache(String ID){
		try {
	
			nameXMLFile = "Taches/" + ID + ".xml";
			System.out.println("name file : " + nameXMLFile);
			if(!new File(nameXMLFile).exists()){
				System.out.println("Fichier Tache non trouvé");
				return null;
			}
			else{
				
		        SAXParserFactory factory = SAXParserFactory.newInstance();
		
		        SAXParser parser = factory.newSAXParser();
		        
		        XMLHandlerTache xmlHandlerTache;
		
		        parser.parse(nameXMLFile, xmlHandlerTache = new XMLHandlerTache());
		        
		        return xmlHandlerTache.tache;
			}
	
	     } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}
}