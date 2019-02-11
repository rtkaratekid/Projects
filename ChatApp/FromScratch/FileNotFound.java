import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FileNotFound {

	public static void sendFileNotFound(Socket clientSocket) throws IOException {
		
		OutputStream outs = clientSocket.getOutputStream();
		
		File htmlFile = new File("Resources/404.html");
		
		FileInputStream fileIn = new FileInputStream(htmlFile);
		
		  
		byte[] fileContent = new byte[(int)htmlFile.length()]; 
		  
		  
		fileIn.read(fileContent);
		 
		String contentlength = "Content-Length: " + String.valueOf(fileContent.length) + "\r\n";
						
		outs.write(("HTTP/1.1 404 OK\n").getBytes());
		outs.write(("Server: Tristan's Party Server\r\n").getBytes());
		outs.write(("Content-Length: " + contentlength).getBytes());
		outs.write(("Content-Type: text/html \r\n").getBytes());
		outs.write(fileContent); 
		
		outs.flush();
		fileIn.close();
		outs.close();
	}
	
	
}
