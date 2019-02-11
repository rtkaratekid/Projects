import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class JsResponse {

	public static void sendJsFile(Socket clientSocket) throws IOException {
		
		OutputStream outs = clientSocket.getOutputStream();
		
		File jsFile = new File("Resources/chat.js");
		
		FileInputStream fileIn = new FileInputStream(jsFile);
		 
		byte[] fileContent = new byte[(int)jsFile.length()];
	
		fileIn.read(fileContent);
	
		String contentlength = "Content-Length: " + String.valueOf(fileContent.length) + "\r\n";
						
		outs.write(("HTTP/1.1 200 OK\n").getBytes());
		outs.write(("Server: Tristan's Party Server\r\n").getBytes());
		outs.write(("Content-Length: " + contentlength).getBytes());
		outs.write(("Content-Type: text/javascript \r\n").getBytes());
		outs.write(fileContent); 
		
		outs.flush();
		
		}
	
}
