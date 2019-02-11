import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CompleteHandshake {
	private String key;

	public void setKey(String k) {
		key = k;
	}

	// encodes the key
	public void encodeResponse(String k) throws NoSuchAlgorithmException {
		// String ret = new String();
		key = k;

		key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		byte[] keyBytes = key.getBytes();

		MessageDigest md = MessageDigest.getInstance("SHA-1"); // message hashing
		byte[] hashedBytes = md.digest(keyBytes);

		key = Base64.getEncoder().encodeToString(hashedBytes); // base 64 get encoder

		System.out.println("Finishing Web Socket handshake with response key: " + key + "\n");
		// return ret;
	}

	public void finishHandshake(String k, Socket clientSocket) throws IOException, NoSuchAlgorithmException {
		this.encodeResponse(k);
		
		OutputStream outs = clientSocket.getOutputStream();

		outs.write(("HTTP/1.1 101 Switching Protocols\n").getBytes());
		outs.write(("Upgrade: websocket\r\n").getBytes());
		outs.write(("Connection: Upgrade \r\n").getBytes());
		outs.write(("Sec-WebSocket-Accept: " + key + "\r\n\r\n").getBytes());

		outs.flush();
		//outs.close();
	}

}
