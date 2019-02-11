import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Room {
	private String roomName;

	private Selector sel;

	private ArrayList<SocketChannel> clients = new ArrayList<SocketChannel>();

	private ArrayList<SocketChannel> toRegister = new ArrayList<SocketChannel>();

	public Room(String name) throws IOException {
		roomName = name;
		this.sel = Selector.open();
	}

	public synchronized void addClient(SocketChannel clientSocketChannel) throws IOException {
		clientSocketChannel.configureBlocking(false);
		System.out.println("Adding client");
		System.out.println("Clients: " + clients.size());
		clients.add(clientSocketChannel); // adding to list of clients
		toRegister.add(clientSocketChannel); // adding to list needs registering
		System.out.println("Clients: " + clients.size());
		sel.wakeup();
	}
	
	public void removeClient(SocketChannel clientSocketChannel) {
		clients.remove(clientSocketChannel);
	}

	public void postMessage(String mess) throws IOException {
		
		for (SocketChannel s : clients) {
			
			SelectionKey key = s.keyFor(sel);
			key.cancel();
			key.channel().configureBlocking(true);

			//System.out.println("Sending message to clients: " + s.toString());
			Encryption.encodeFromRoom(mess, s);

			key.channel().configureBlocking(false);
			sel.selectNow();
			key.channel().register(sel, SelectionKey.OP_READ);
			//System.out.println("Re-registering socket channel: " + key.channel().toString());
		}
	}

	public void runRoom() throws IOException {
		System.out.println("POWERING UP THE ROOM");
		while (true) {
//			if(clients.size() == 0) {
//				//close the room thread
//				return;
//			}
			
			System.out.println("listening for socket message");
			sel.select(); // waiting
			Set<SelectionKey> set = sel.selectedKeys(); // gives back all of the channels that are ready

			Iterator<SelectionKey> it = set.iterator(); // an iterator is an interface that lets up loop through
														// collections

			while (it.hasNext()) { // while we’re not done with the collection
				System.out.println("__going through keys in: " + roomName + "___");
				SelectionKey key = it.next();
				if (key.isReadable()) {
					it.remove(); // take this out of the set of ready keys
					key.cancel();
					key.channel().configureBlocking(true);

					String message = Encryption.decodeFromRoom((SocketChannel) key.channel());
					System.out.println("Message from client: " + message);

					if (message.equals("exit room")) { // remove the client
						this.removeClient((SocketChannel) key.channel());
						System.out.println("Removing client");
						continue;
						
					} else if (message.equals("browser exit")) { // remove the client if the browser exits

						this.removeClient((SocketChannel) key.channel());

						System.out.println("\nBrowser exited, removing client from list\n");
						continue;
					}

					key.channel().configureBlocking(false);
					sel.selectNow();
					key.channel().register(sel, SelectionKey.OP_READ);
					this.postMessage(message);
				}
			}
			for (SocketChannel s : toRegister) { // registering sockets
				s.register(sel, SelectionKey.OP_READ);
			}
			toRegister.clear();
		}
	}
}
