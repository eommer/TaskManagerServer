import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class InscriptionThread extends Thread{

	private Socket socketClient;
	private User user;
	private String newId;
	private String returnMsg = "";
	
	public InscriptionThread(Socket socket){
		
		this.socketClient = socket;
		
	}
	
	public void run(){
try {
			
			System.out.println("INSCRIPTION");
			ObjectInputStream objectIn = new ObjectInputStream(socketClient.getInputStream());
			OutputStream out = socketClient.getOutputStream();
			DataOutputStream outToClient = new DataOutputStream(out);
			
			user = (User) objectIn.readObject();
			
			UserXMLWriter xmlWriter = new UserXMLWriter();
			
			if(new SaxParserAllUsers().ParserUserId(user.mail).equals("User not found")){
				
				System.out.println("Email disponnible");
				newId = UUID.randomUUID().toString();
				user.userID = newId;
				System.out.println("New Id : " + user.userID);	
				xmlWriter.writeUser(user.userID, user);
				
				AllUsersXMLWriter allUsersXMLWriter = new AllUsersXMLWriter();
				allUsersXMLWriter.writeUser(user);
				
				System.out.println("OK");
				outToClient.writeBytes("OK\n"); //msg de confirmation pr le client
				
				ObjectOutputStream objectOut = new ObjectOutputStream(out);
				objectOut.writeObject(user);
	            objectOut.close();
			}
			else if(new SaxParserAllUsers().ParserUserId(user.mail)==null){
				outToClient.writeBytes("Problem parsing Users.xml\n");
				returnMsg = "ProblemParsing\n";
			}
			else{
				System.out.println("Email déjà utilisée");
				outToClient.writeBytes("Email already use\n");
			}
			
			outToClient.writeBytes(returnMsg);
			
			this.socketClient.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
