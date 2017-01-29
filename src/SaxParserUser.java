
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.User;

public class SaxParserUser {

	private String nameXMLFile = "";

	public User ParserUser(String ID) {
		try {

			if (ID.equals("User not found")) {
				System.out.println("User Not Found");
				return null;
			} else {
				nameXMLFile = "Users/" + ID + ".xml";
				if (!new File(nameXMLFile).exists()) {
					System.out.println("Fichier utilisateur non trouvé");
					return null;
				} else {
					System.out.println("Fichier utilisateur trouvé");
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
