import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class AllUsersXMLWriter {
	
	//XMLStreamWriter writer;

	public void writeUser(User user) throws XMLStreamException, IOException, SAXException, ParserConfigurationException, TransformerException {
		
		System.out.println("XML WRITER");
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
		domFactory.setIgnoringComments(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder(); 
		Document doc = builder.parse(new File("Users.xml")); 

		NodeList nodes = doc.getElementsByTagName("users");
		
		System.out.println("nb Users : " + nodes.getLength());
 
		Element mailElt = doc.createElement("mail");
		Element idElt = doc.createElement("id");
		
		Text mailValue = doc.createTextNode(user.mail);
		Text idValue = doc.createTextNode(user.userID);
		
		Element userElt = doc.createElement("user"); 
		
		mailElt.appendChild(mailValue);
		idElt.appendChild(idValue);
		
		userElt.appendChild(mailElt);
		userElt.appendChild(idElt);
		
		System.out.println(mailElt);
		
		nodes.item(0).appendChild(userElt);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("Users.xml"));
		transformer.transform(source, result);
	    
		
	}
	/*
	private void writeElt(String balise, String param) throws XMLStreamException{
		writer.writeStartElement(balise);
		writer.writeCharacters(param);
		writer.writeEndElement();
	}*/
}
