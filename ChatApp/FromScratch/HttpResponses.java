import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpResponses {

public static void sendFile(String comm, String fileName, Socket sock) throws IOException {
		
	if (comm.equals("GET")) {
		
		if (fileName.equals("/")) {
	
			IndexResponse.sendIndexFile(sock);
	
		} else if (fileName.equals("/main.css")) {
	
			CssResponse.sendCssFile(sock);
	
		} else if (fileName.equals("/main.js")) {
	
			JsResponse.sendJsFile(sock);

		} else if (fileName.equals("/fight.gif")) {
			
			OutputStream outs = sock.getOutputStream();
			File picFile = new File("Resources/fight.gif");
			FileInputStream fileIn = new FileInputStream(picFile);
			byte[] fileContent = new byte[(int)picFile.length()]; 
			fileIn.read(fileContent);
			String contentlength = "Content-Length: " + String.valueOf(fileContent.length) + "\r\n";
							
			outs.write(("HTTP/1.1 200 OK\n").getBytes());
			outs.write(("Server: Tristan's Party Server\r\n").getBytes());
			outs.write(("Content-Length: " + contentlength).getBytes());
			outs.write(("Content-Type: image/gif \r\n\r\n").getBytes());
			outs.write(fileContent); 
			
			outs.flush();
			
} else if (fileName.equals("/chat.html")) {
			
			OutputStream outs = sock.getOutputStream();
			File picFile = new File("Resources/chat.html");
			FileInputStream fileIn = new FileInputStream(picFile);
			byte[] fileContent = new byte[(int)picFile.length()]; 
			fileIn.read(fileContent);
			String contentlength = "Content-Length: " + String.valueOf(fileContent.length) + "\r\n";
							
			outs.write(("HTTP/1.1 200 OK\n").getBytes());
			outs.write(("Server: Tristan's Party Server\r\n").getBytes());
			outs.write(("Content-Length: " + contentlength).getBytes());
			outs.write(("Content-Type: text/html \r\n\r\n").getBytes());
			outs.write(fileContent); 
			
			outs.flush();
			
		} else {
	
			FileNotFound.sendFileNotFound(sock);
		}

	}
	
	sock.close();

	}
	
	
}
