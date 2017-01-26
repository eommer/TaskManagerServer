
import java.io.File;
import java.io.IOException;
import model.Tache;
import model.User;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;

import jdk.internal.org.xml.sax.SAXException;

public class SaxParserUser {
	
	private String nameXMLFile ="";
	
	public User ParserUser(String ID){
		try {
	
			if(ID.equals("User not found")){
				System.out.println("User Not Found");
				return null;
			}
			else{
			nameXMLFile = "Users/" + ID + ".xml";
			if(!new File(nameXMLFile).exists()){
				System.out.println("Fichier utilisateur non trouv�");
				return null;
			}
			else{
				System.out.println("Fichier utilisateur trouv�");
				System.out.println("name file : " + nameXMLFile);
				
		        SAXParserFactory factory = SAXParserFactory.newInstance();
		
		        SAXParser parser = factory.newSAXParser();
		        
		        XMLHandlerUser xmlHandlerUser;
		
		        parser.parse(nameXMLFile, xmlHandlerUser = new XMLHandlerUser());
		        
		        return xmlHandlerUser.user;
				}
			}
	
	     } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}
}
