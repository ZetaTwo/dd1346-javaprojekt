package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatMdl {
	String[] messages;
	String[] users;
	protected ArrayList<Connection> connections;
	MessageSettings messageSettings;
	protected boolean running = true;
	
	public ChatMdl() {
	}
	
	public void UpdateSettings(MessageSettings settings) {
		messageSettings = settings;
	}
	
	public void Connect(String host, int port) throws UnknownHostException, IOException {
		Socket target = new Socket(host, port);
		Connection conection = new Connection(target);
		
		connections.add(conection);
	}
	
	public void sendMessage(String htmlMessage, String sender) {
		String messageData = "<message sender=\"" + sender + "\">" + 
							  "<text>" + htmlMessage + "</text>" +
							 "</message>";
							  
		postMessage(messageData);
	}
	
	public void sendFile(String filename, String message, String sender) {
		int size = 0;
		
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<filerequest name=\"" + filename + "\" size=\"" + size + "\">" + message + "</filerequest>" +
				 "</message>";
				  
		postMessage(messageData);
	}
	
	public void sendFileResponse(boolean response, String message, int port, String sender) {
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<fileresponse reply=\"" + ((response)?"yes":"no") + "\" port=\"" + port + "\">" + message + "</fileresponse>" +
				 "</message>";
				  
		postMessage(messageData);
	}
	
	public void sendKeyrequest(String sender, String message, String type) {
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<keyrequest type=\"" + type + "\">" + message + "</fileresponse>" +
				 "</message>";
				  
		postMessage(messageData);
	}
	
	public void sendKeyresponse(String sender, String key) {
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<text key=\"" + key + "\"></text>" +
				 "</message>";
				  
		postMessage(messageData);
	}
	
	public void close(String sender) {
		String messageData = "<message sender=\"" + sender + "\"><disconnect /></message>";
		postMessage(messageData);
		connections.clear();
	}
	
	private void postMessage(String xmlData) {
		for(Connection connection : connections) {
			connection.getOut().write(xmlData);
		}
	}

	public MessageSettings getSettings() {
		return messageSettings;
	}
}
