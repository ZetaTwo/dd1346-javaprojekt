package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.StringReader;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;


>>>>>>> Added xml-parsing to chatMdl

import se.kth.f.carlcarl.controller.ChatCtrl;

public class ChatMdl extends Thread {
	ArrayList<String> messages = new ArrayList<String>();
	ArrayList<String> users = new ArrayList<String>();
	protected ArrayList<Connection> connections = new ArrayList<Connection>();
	MessageSettings messageSettings = new MessageSettings();
	protected boolean running = true;
	ChatCtrl owner;
	
	Queue<String> pendingFileRequests = new LinkedList<String>();
	
	protected ChatMdl(ChatCtrl ctrl) {
		owner = ctrl;
	}
	
	public ChatMdl(ChatCtrl ctrl, String adress, int port) throws UnknownHostException, IOException {
		owner = ctrl;
		Connect(adress, port);
	}
	
	public void UpdateSettings(MessageSettings settings) {
		messageSettings = settings;
	}
	
	public void run() {
		while(running) {
			ArrayList<Connection> connectionsCopy = new ArrayList<>(connections);
			for(Connection conn : connectionsCopy) {
				try {
					if(conn.in.ready()) {
						ParseMessage(conn.in.readLine());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void Connect(String host, int port) throws UnknownHostException, IOException {
		Socket target = new Socket(host, port);
		Connection conection = new Connection(target);
		
		addConnection(conection);
	}
	
	public void addConnection(Connection connection) {
		connections.add(connection);
	}
	
	public void sendMessage(String htmlMessage, String sender) {
		String messageData = "<message sender=\"" + sender + "\">" + 
							  "<text>" + htmlMessage + "</text>" +
							 "</message>";
							  
		postMessage(messageData);
	}
	
	public void sendFile(String filename, String message, long fileSize, String sender) {
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<filerequest name=\"" + filename + "\" size=\"" + fileSize + "\">" + message + "</filerequest>" +
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
			connection.getOut().println(xmlData);
		}
	}

	public MessageSettings getSettings() {
		return messageSettings;
	}
	
	private void ParseMessage(String string) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(string));
			Document XMLDocument = builder.parse(is);
			
			Node root = XMLDocument.getFirstChild();
			
			String sender = root.getAttributes().getNamedItem("sender").getNodeValue();
			Node child = root.getFirstChild();
			
			switch(child.getNodeName()) {
			
			case "text": 
				owner.ProcessChatMessage(child.getNodeValue(), sender);
				break;
			case "encrypted":
				String decryptedMessage = null;
				String encryption = child.getAttributes().getNamedItem("type").getNodeValue();
				switch(encryption) {
				case "RSA":
					break;
				case "ceasar":
					break;
				default:
					decryptedMessage = "unknown encryption";
					break;
				}
				owner.ProcessChatMessage(decryptedMessage, sender);
				break;
			case "filerequest":
				owner.ProcessFileTransferRequest(sender, child.getAttributes().getNamedItem("name").getNodeValue(), Integer.parseInt(child.getAttributes().getNamedItem("size").getNodeValue()), child.getNodeValue());
				break;
			case "fileresponse":
				owner.ProcessFileTransferResponse(Boolean.parseBoolean(child.getAttributes().getNamedItem("reply").getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem("port").getNodeValue()));
				break;
			case "request":
				owner.ProcessChatRequest(sender);
				break;
			case "disconnect":
				owner.ProcessDisconnect(sender);
				break;
			case "keyrequest":
				owner.ProcessKeyRequest(child.getAttributes().getNamedItem("type").getNodeValue());
				break;
			
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}    
	
}
