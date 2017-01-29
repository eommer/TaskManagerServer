import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandlerAllUsersList extends DefaultHandler{


   private String nameBalise;
   public String id;
   private String mail;
   public Map<String, String> listUsers = new HashMap<>();
   
   public void startDocument() throws SAXException {
   
   }

   public void endDocument() throws SAXException {
   }   

   public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) throws SAXException {
	   this.nameBalise = qname;
   }
   
   public void endElement(String uri, String localName, String qName)throws SAXException{
	   
   }
   
   public void characters(char[] data, int start, int end) throws SAXException{   
	   String str = new String(data, start, end).trim();
	   
		  if(str.isEmpty()){
			  return;
		  }
		  else{
			  if(this.nameBalise == "mail"){
				  this.mail = str;
			  }
			  else if(this.nameBalise == "id"){
				  this.id = str;
				  listUsers.put(this.id, this.mail);
			  }
		  }
   }

}
