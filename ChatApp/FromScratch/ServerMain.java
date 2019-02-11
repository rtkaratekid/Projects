import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("POWERING UP THE SERVER");
		
	//keeping track of current rooms
		HashMap<String, Room> rooms = new HashMap<String, Room>();

		try {
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(8080));

			while (true) {
				SocketChannel clientSocketChannel = serverChannel.accept();

				clientSocketChannel.configureBlocking(true); // to allow reading in with the socket

				Socket clSocket = clientSocketChannel.socket();

			// start a new thread for each client
				Thread clientThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							InputStream ins = clSocket.getInputStream();
							OutputStream outs = clSocket.getOutputStream();
							Scanner s = new Scanner(ins);

						// reading in the request line
							String command="";
							String file= "";
							String protocol = "";
							String empty = ""; // get rid of \r\n to prepare for reading into the hash map
							
							if (s.hasNext()) {
								
							command = s.next();
							file = s.next();
							protocol = s.next();
							empty = s.nextLine(); // get rid of \r\n to prepare for reading into the hash map
							
							}
						// reading all the header information into a hash map for future reference
							HashMap<String, String> headers = new HashMap<String, String>(50);
							while (true) {
								
									String nextLine = s.nextLine();
									if (nextLine.isEmpty()) {
										break;
									}
									String[] headerArray = nextLine.split(": ");
									headers.put(headerArray[0], headerArray[1]);
								
							}

						// handling client requests
							if (command.equals("GET")) {
								if (headers.containsKey("Sec-WebSocket-Key")) {

									CompleteHandshake chs = new CompleteHandshake();
									chs.finishHandshake(headers.get("Sec-WebSocket-Key"), clSocket);

								// need to listen for a websocket message indicating a username and which room to join
									String joinRoom = Encryption.decodeFromRoom(clientSocketChannel);
									System.out.println("From Main: " + joinRoom);
									String[] joinMessage = joinRoom.split(" ");
									
									if(joinMessage[0].equals("join")) {
										System.out.println("Checking hashmap for " + joinMessage[1]);
										// checking for existence of room in HashMap rooms
										if(rooms.containsKey(joinMessage[1])) {
											
											//join the existing room 
											System.out.println("\n---Joining the " + joinMessage[1] + " chat room---");
											System.out.println("Adding " + clientSocketChannel.toString() + " to " + joinMessage[1] + " chat room");
											rooms.get(joinMessage[1]).addClient(clientSocketChannel);
											
										} else {
											// create a new room/thread and add that room to the hashmap
											// a default room for now
											Room newRoom = new Room(joinMessage[1]);
											Thread newRoomThread = new Thread(new Runnable() {
												@Override
												public void run() {
													try {
														System.out.println("\nRoom doesn't exist yet.");
														System.out.println("---Starting the new " + joinMessage[1] + " chat room---");
														System.out.println("Adding " + clientSocketChannel.toString() + " to " + joinMessage[1] + " chat room");
														rooms.put(joinMessage[1], newRoom);
														newRoom.addClient(clientSocketChannel);
														newRoom.runRoom();
														
														
														
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
											});
											newRoomThread.start();

										}

									}

								} else {
									HttpResponses.sendFile(command, file, clSocket);
								}
							}
						} catch (IOException e) {
							System.err.println("\nIOException\n");
							e.printStackTrace();

						} catch (NoSuchAlgorithmException e) {
							System.out.println("No such Algorithm exception");
							e.printStackTrace();
						}
					}
				});
				clientThread.start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: 8080.");
			System.exit(1);
		}
	}
}
