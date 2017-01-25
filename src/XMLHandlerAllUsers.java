import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;


public class XMLHandlerAllUsers extends DefaultHandler{

	
   public String id;
   private String nameBalise;
   private String mail;
   public boolean userIsFind = false;
   
   public XMLHandlerAllUsers(String mail){
	   this.mail = mail;
   }

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
				  if(str.equals(this.mail)){
					  System.out.println("mail found");
					  this.userIsFind=true;
				  }
			  }
			  else if(this.nameBalise == "id" && this.userIsFind==true){
				  this.id = str;
				  System.out.println("id found : " + this.id);
				throw new SAXException();
			  }  
		  }
   }

}