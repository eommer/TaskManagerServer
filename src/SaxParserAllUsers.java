import java.io.IOException;


import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class SaxParserAllUsers {
	
	private String nameXMLFile ="Users.xml";
	private XMLHandlerAllUsers xmlHandlerAllUsers;
	private XMLHandlerAllUsersList xmlHandlerAllUsersList;
	
	public String ParserUserId(String email){
		try {
			
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	
	        SAXParser parser = factory.newSAXParser();
	        
	        
	
	        parser.parse(nameXMLFile, xmlHandlerAllUsers = new XMLHandlerAllUsers(email));
	        
	        if(xmlHandlerAllUsers.userIsFind)
	        	return xmlHandlerAllUsers.id;
	        else
	        	return "User not found";
	
	
	     }catch(SAXException e){
	    	 System.out.println("EXCEPTION");
	    	 return xmlHandlerAllUsers.id;
	     }catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}
	
	public Map<String, String> updateListUsers() throws SAXException, IOException, ParserConfigurationException{
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
        SAXParser parser = factory.newSAXParser();
        
        parser.parse(nameXMLFile, xmlHandlerAllUsersList = new XMLHandlerAllUsersList());
		
		return xmlHandlerAllUsersList.listUsers;
			
	}
}