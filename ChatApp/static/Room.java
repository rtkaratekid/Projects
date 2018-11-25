package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class Room {
	
	private ArrayList<WebSocketSession> clients;
	private ArrayList<TextMessage> textMessages;
	private String roomName;
	
	public Room(String n) {
		System.out.println("Calling room constructor for a new room");
		clients = new ArrayList<WebSocketSession>();
		textMessages = new ArrayList<TextMessage>();
		roomName = n;
	}
	
	//need to broadcast
	public void broadcast(TextMessage m) throws IOException {
		for(WebSocketSession cl: clients) {
			System.out.println("sending " + m + " to " + cl);
			cl.sendMessage(m);
			textMessages.add(m);
		}
	}
	
	public void sendRoomHistory(WebSocketSession wbs) throws IOException {
		System.out.println("___Sending room chat history___");
		for(TextMessage t : textMessages) {
			wbs.sendMessage(t);
		}
	}
	
	
	//add client
	public void addClient(WebSocketSession client) throws IOException {
		System.out.println("___Adding client to " + roomName + "___");
		clients.add(client);
		this.sendRoomHistory(client);
	}
	
	//remove client
	public void removeClient(WebSocketSession client) {
		clients.remove(client);
	}
}
