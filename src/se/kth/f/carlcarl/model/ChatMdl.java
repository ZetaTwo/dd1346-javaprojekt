package se.kth.f.carlcarl.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatMdl {
	String[] messages;
	String[] users;
	ArrayList<Socket> connections;
	MessageSettings messageSettings;
	
	private ChatMdl() {
	}
	
	public ChatMdl Connect() {
		
	}
	
	public ChatMdl Host(int port) {
		ChatMdl host = new ChatMdl();
		host.connections.add(new ServerSocket(port));
	}
	
	public void sendMessage() {
	}
	
	public void sendFile() {
	}
	
	public void close() {	
	}
	
	
	
}
