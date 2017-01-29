import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Tache;

public class SaxParserTache {

	private String nameXMLFile = "";

	public Tache ParserTache(String ID) {
		try {

			nameXMLFile = "Taches/" + ID + ".xml";
			System.out.println("name file : " + nameXMLFile);
			if (!new File(nameXMLFile).exists()) {
				System.out.println("Fichier Tache non trouvé");
				return null;
			} else {

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