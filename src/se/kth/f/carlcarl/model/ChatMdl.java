package se.kth.f.carlcarl.model;

import java.awt.Color;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import javax.xml.parsers.*;

import se.kth.f.carlcarl.controller.ChatCtrl;

public class ChatMdl extends Thread {
	ArrayList<String> messages = new ArrayList<>();
	ArrayList<String> users = new ArrayList<>();
	protected ArrayList<Connection> connections = new ArrayList<>();
	MessageSettings messageSettings = new MessageSettings();
	protected boolean running = true;
	ChatCtrl owner;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	
	Queue<String> pendingFileRequests = new LinkedList<>();
	
	protected ChatMdl(ChatCtrl ctrl) {
		owner = ctrl;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public ChatMdl(ChatCtrl ctrl, String adress, int port) throws UnknownHostException, IOException {
		owner = ctrl;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
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
					if(conn != null) {
						String data = "";
						while(conn.in.ready()) {
							data += conn.in.readLine();
						}
						if(!data.isEmpty()) {
							ParseMessage(conn, data);
							if(connectionsCopy.size() > 1){
								for(Connection connect: connectionsCopy) {
									if(connect != conn){
										connect.getOut().println(data);
										connect.getOut().flush();
									}
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            try {
                Thread.sleep(50);
            } catch (Exception e) {

            }
		}
	}
	
	public void Connect(String host, int port) throws UnknownHostException, IOException {
		Socket target = new Socket(host, port);
		Connection connection = new Connection(target);
		addConnection(connection);
	}
	
	public void addConnection(Connection connection) {
		connections.add(connection);
	}
	
	public void sendMessage(String htmlMessage, String sender, Color color, EncryptionHandler.Encryption encryption, String key) {
		String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
		
		htmlMessage = htmlMessage.replace("<b>", "<fetstil>");
		htmlMessage = htmlMessage.replace("</b>", "</fetstil>");
		htmlMessage = htmlMessage.replace("<i>", "<kursiv>");
		htmlMessage = htmlMessage.replace("</i>", "</kursiv>");

        String messageTag = "<text color=\"" + colorString + "\">" + htmlMessage + "</text>";
        String messageFinal = EncryptionHandler.Encrypt(encryption, messageTag, key);
		
		String messageData = "<message sender=\"" + sender + "\">" + messageFinal + "</message>";
							  
		postMessage(messageData);
	}
	
	public void sendFile(File file, String message, long fileSize, String sender) {
        String filename = file.getName();
        pendingFileRequests.add(file.getAbsolutePath());
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
		
		ArrayList<Connection> connectionsCopy = new ArrayList<>(connections);
		for(Connection conn : connectionsCopy) {
			if(conn != null) {
				conn.Close();
			}
		}
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
	
	private void ParseMessage(Connection conn, String string) {
		try {
			//Create xml doc from string
			String encoding = "utf-8"; //UTF-8 or latin1
			InputSource inputSource = new InputSource(new ByteArrayInputStream(string.getBytes(encoding)));
			Document xmlDoc = builder.parse(inputSource);
			Node root = xmlDoc.getFirstChild();
			
			//Get sender and root
			String sender = root.getAttributes().getNamedItem("sender").getTextContent();
			Node child = root.getFirstChild();
			
			//Process message
			switch(child.getNodeName()) {
			case "text":
				ParseTextMessage(xmlDoc, sender, child);
				break;

			case "encrypted":
				ParseEncryptedMessage(conn, sender, child);
				break;
				
			case "filerequest":
				//Get file parameters
				int size = Integer.parseInt(child.getAttributes().getNamedItem("size").getNodeValue());
				String name = child.getAttributes().getNamedItem("name").getNodeValue();
				String message = child.getNodeValue();
				
				owner.ProcessFileTransferRequest(sender, name, size, message);
				break;
				
			case "fileresponse":
				//Get response
                String replyStr = child.getAttributes().getNamedItem("reply").getNodeValue();
				boolean reply = replyStr.equals("yes");
				int port = Integer.parseInt(child.getAttributes().getNamedItem("port").getNodeValue());
				
				//TODO: Filöverföring
				if(reply) {
					owner.ProcessFileTransferResponse(conn, reply, port, pendingFileRequests.poll());
				} else {
					owner.ProcessChatMessage(sender + " nekade din filöverföring", "System", Color.black);
				}
				
				break;
			case "request":
				owner.ProcessChatRequest(sender);
				break;
			case "disconnect":
				owner.ProcessDisconnect(sender);
				break;
			case "keyrequest":
				String keyType = child.getAttributes().getNamedItem("type").getNodeValue();
				owner.ProcessKeyRequest(keyType);
				break;
			
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ParseEncryptedMessage(Connection conn, String sender, Node child) {
		//Prepare message and get encryption type
		String decryptedMessage = null;
		String encryption = child.getAttributes().getNamedItem("type").getNodeValue();
		String key = child.getAttributes().getNamedItem("key").getNodeValue();
        String encryptedMessage = child.getFirstChild().getNodeValue();

		switch(encryption) {
		case "aes":
            decryptedMessage = EncryptionHandler.Decrypt(EncryptionHandler.Encryption.AES, encryptedMessage, key);
			break;
		case "caesar":
            decryptedMessage = EncryptionHandler.Decrypt(EncryptionHandler.Encryption.CASEAR, encryptedMessage, key);
			break;
		default:
			decryptedMessage = "<text>unknown encryption</text>";
			break;
		}
        ParseMessage(conn, "<message sender=\"" + sender + "\">" + decryptedMessage + "</message>");
	}

	private void ParseTextMessage(Document xmlDoc, String sender, Node child)
			throws IOException {
		//Serialize node content to text
		Writer out = new StringWriter();
		OutputFormat format = new OutputFormat(xmlDoc);
		format.setOmitXMLDeclaration(true);
		format.setEncoding("UTF-8");
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(child);
		String text = out.toString();
		
		//Convert styling tags
		String colorString;
        try {
            colorString = child.getAttributes().getNamedItem("color").getTextContent();
        } catch (Exception e) {
            colorString = "#000000";
        }
		Color color = Color.decode(colorString);
		text = text.replace("<fetstil>", "<b>");
		text = text.replace("</fetstil>", "</b>");
		text = text.replace("<kursiv>", "<i>");
		text = text.replace("</kursiv>", "</i>");
		
		
		owner.ProcessChatMessage(text, sender, color);
	}    
	
}
