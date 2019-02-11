import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Encryption {

	// returns a string of the decoded message
	public static String decodeFromRoom(SocketChannel selectableChannel) throws IOException {
	
		Socket socket = selectableChannel.socket();
		InputStream ins = socket.getInputStream();
		
		// getting length of the message... but only if it's 125 or less
		byte[] header = new byte[2];
		if(ins.read(header) != 2) {
			return "browser exit";
		};
		
		//checking for the opCode of a closed socket
		//String opCode = new String(header[0]);
		System.out.println("---OpCode: " + (header[0] & 0xF) + "----");
		
		if((header[0] & 0xF) == 0x8) { //checking if opCode is equal to 8
			return "browser exit";
		}

		// length of message
		int len = (header[1] & 0x7F);

		// getting mask
		byte[] mask = new byte[4];
		ins.read(mask);

		// getting encoded message
		byte[] encoded = new byte[len];
		ins.read(encoded);

		// decoding the encoded bytes and storing them in byte array
		byte[] decoded = new byte[len];
		for (int i = 0; i < len; i++) {
			decoded[i] = (byte) (encoded[i] ^ mask[i % 4]);
		}

		// converting the decoded bytes to a string
		String decode = new String(decoded);
		// System.out.println("From Decoder: " + decode);
		
		return decode;

	}
	
	// encodes a string and sends the message out the socket
	public static void encodeFromRoom(String toBrowser, SocketChannel channel) throws IOException {
		
		Socket socket = channel.socket();
		OutputStream outs = socket.getOutputStream();
		
		byte[] stringBytes = toBrowser.getBytes();

		int len = stringBytes.length;

		int header = 0x81;

		outs.write((byte) (header));
		outs.write((byte) (len));
		outs.write(stringBytes);
	}
	
	
	
}
