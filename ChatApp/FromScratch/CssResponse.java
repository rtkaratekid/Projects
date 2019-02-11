import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CssResponse {

	public static void sendCssFile(Socket clientSocket) throws IOException {
		
		OutputStream outs = clientSocket.getOutputStream();
		
		File cssFile = new File("Resources/main.css");
		
		FileInputStream fileIn = new FileInputStream(cssFile);
		 
		byte[] fileContent = new byte[(int)cssFile.length()];
	
		fileIn.read(fileContent);
	
		String contentlength = "Content-Length: " + String.valueOf(fileContent.length) + "\r\n";
						
		outs.write(("HTTP/1.1 200 OK\n").getBytes());
		outs.write(("Server: Tristan's Party Server\r\n").getBytes());
		outs.write(("Content-Length: " + contentlength).getBytes());
		outs.write(("Content-Type: text/css \r\n").getBytes());
		outs.write(fileContent); 
		
		outs.flush();
		//fileIn.close();
		//outs.close();
		
		}
	
	
}
