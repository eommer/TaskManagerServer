import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

	private static ServerSocket socketServer;
	private static final int PORT = 1500;
	

	public static void main(String[] args) throws InterruptedException {

		try {
			
			socketServer = new ServerSocket(PORT);

			while (true) {
				Socket connectionSocket = socketServer.accept();
				ClientThread clientTh = new ClientThread(connectionSocket);
				clientTh.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		

		

	}

}
