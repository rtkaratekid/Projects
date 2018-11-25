package com.example.demo;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WSHandler extends TextWebSocketHandler {
	// ArrayList<Room> rooms = new ArrayList<Room>();
	HashMap<String, Room> rooms = new HashMap<String, Room>(); // contains a list of existing rooms
	HashMap<WebSocketSession, Room> clientToRoom = new HashMap<WebSocketSession, Room>(); // contains a list of clients
																							// and which rooms they
																							// belong too

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

		System.out.println("\nMessage from client: " + message.getPayload() + "\n");

		String mess = message.getPayload();
		String[] wordsInMessage = mess.split(" ");

//		for(String s: wordsInMessage) {
//			System.out.println("Next word in string array: " + s);
//		}

		if (wordsInMessage[0].equals("join")) {
			String roomRequest = wordsInMessage[1];

			System.out.println("Requested room: " + roomRequest);
			System.out.println("Do list of rooms contain " + roomRequest + ": " + (rooms.containsKey(roomRequest)));
			
			
			
			// if room already exists
			if (rooms.containsKey(roomRequest)) {
				
				System.out.println("____Adding client to pre-existing room_____");
				
				
				//Room room = new Room(roomRequest); // add client to that room
				
				
				clientToRoom.put(session, rooms.get(roomRequest));
				
				
				Room requestedRoom = rooms.get(roomRequest);
				
				
				System.out.println("_________Adding client to: " + requestedRoom);
				
				
				rooms.get(roomRequest).addClient(session); // adding client to room

			} else {
				System.out.println("_____Creating a new room and adding client to it_____");
				Room room = new Room(roomRequest);
				rooms.put(roomRequest, room); // add new room to list of rooms
				clientToRoom.put(session, room); // add client arrayList
				room.addClient(session); // add client to the room
			}
		} else {
			System.out.println("Message from client: " + message);
			System.out.println("_____Sending message to all clients______");
			// get the room the client belongs to, then broadcast to other clients
			Room r = clientToRoom.get(session);
			System.out.println("Gettign room: " + r);

			System.out.println("Now broadcasting the message...");
			r.broadcast(message);

		}

	}

}
