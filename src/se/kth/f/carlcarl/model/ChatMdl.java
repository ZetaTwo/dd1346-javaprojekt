package se.kth.f.carlcarl.model;

import java.awt.Color;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import javax.xml.parsers.*;

import se.kth.f.carlcarl.controller.ChatCtrl;
import se.kth.f.carlcarl.helper.EncryptionHelper;

public class ChatMdl extends Thread {
	private final ArrayList<Connection> connections = new ArrayList<>();
	private MessageSettings messageSettings = new MessageSettings();
    private final ChatCtrl owner;
    private DocumentBuilder builder;
	
	private final Queue<PendingFileTransfer> pendingFileRequests = new LinkedList<>();
    private boolean running = true;

    public static ChatMdl ConnectChat(ChatCtrl owner, Connection conn) {
        ChatMdl result = new ChatMdl(owner);
        result.addConnection(conn);
        return result;
	}

    public static ChatMdl ConnectChat(ChatCtrl owner, String address, int port) {
        ChatMdl result = new ChatMdl(owner);
        if(!result.Connect(address, port)) {
            return null;
        }

        return result;
    }

    ChatMdl(ChatCtrl owner) {
        this.owner = owner;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
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
                        }
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}

    private void relayMessage(ArrayList<Connection> connectionsCopy, Connection conn, String data) {
        if(connectionsCopy.size() > 1){
            for(Connection connect: connectionsCopy) {
                if(connect != conn){
                    connect.getOut().println(data);
                    connect.getOut().flush();
                }
            }
        }
    }

    boolean Connect(String host, int port) {
        Connection connection;
        try {
		    Socket target = new Socket(host, port);
		    connection = new Connection(target);
        } catch (Exception e) {
            return false;
        }
		connection.getOut().println("<message sender=\"" + getUserName() + "\"> <request>" + "</request> </message>");
		addConnection(connection);

        return true;
	}
	
	public void addConnection(Connection connection) {
		connections.add(connection);
        owner.Update();
	}
	
	public void sendMessage(String htmlMessage, String sender, Color color, EncryptionHelper.Encryption encryption, String key) {
		String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
		
		htmlMessage = htmlMessage.replace("<b>", "<fetstil>");
		htmlMessage = htmlMessage.replace("</b>", "</fetstil>");
		htmlMessage = htmlMessage.replace("<i>", "<kursiv>");
		htmlMessage = htmlMessage.replace("</i>", "</kursiv>");

        String messageTag = "<text color=\"" + colorString + "\">" + htmlMessage + "</text>";
        String messageFinal = EncryptionHelper.Encrypt(encryption, messageTag, key);
		
		String messageData = "<message sender=\"" + sender + "\">" + messageFinal + "</message>";
							  
		postMessage(messageData);
	}
	
	public void sendFile(File file, String message, long fileSize, String sender, Connection connection) {
        String filename = file.getName();

        PendingFileTransfer pendingFileTransfer = new PendingFileTransfer(this, connection);
        pendingFileRequests.add(pendingFileTransfer);
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<filerequest name=\"" + filename + "\" size=\"" + fileSize + "\">" + message + "</filerequest>" +
				 "</message>";
				  
		postMessage(messageData, connection);
	}
	
	public void sendFileResponse(boolean response, String message, int port, String sender, Connection connection) {
		String messageData = "<message sender=\"" + sender + "\">" + 
				  "<fileresponse reply=\"" + ((response)?"yes":"no") + "\" port=\"" + port + "\">" + message + "</fileresponse>" +
				 "</message>";
				  
		postMessage(messageData, connection);
	}

    //TODO: Send key request?
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
        running = false;
	}
	
	private void postMessage(String xmlData) {
		for(Connection connection : connections) {
			connection.getOut().println(xmlData);
		}
	}

    private void postMessage(String xmlData, Connection connection) {
        connection.getOut().println(xmlData);
    }

	public MessageSettings getSettings() {
		return messageSettings;
	}
	
	String getUserName() {
		return owner.getUserName();
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
            conn.setUsername(sender);
            owner.Update();
			Node child = root.getFirstChild();

            String message;
			
			//Process message
			switch(child.getNodeName()) {
			case "text":
                relayMessage(connections, conn, string);
				ParseTextMessage(xmlDoc, sender, child);
				break;

			case "encrypted":
                relayMessage(connections, conn, string);
				ParseEncryptedMessage(conn, sender, child);
				break;
				
			case "filerequest":
				//Get file parameters
				int size = Integer.parseInt(child.getAttributes().getNamedItem("size").getNodeValue());
				String name = child.getAttributes().getNamedItem("name").getNodeValue();
                message = child.getTextContent();

				owner.ProcessFileTransferRequest(sender, name, size, message, conn);
				break;
				
			case "fileresponse":
				//Get response
                String replyStr = child.getAttributes().getNamedItem("reply").getNodeValue();
				boolean reply = replyStr.equals("yes");
				int port = Integer.parseInt(child.getAttributes().getNamedItem("port").getNodeValue());

                message = child.getTextContent();
                owner.ProcessChatMessage(sender + " svarade " + ((reply)?"ja":"nej") + " på din filöverföringsförfrågan med meddelandet: " + message, "System", Color.BLACK);
                ParseFileResponse(conn, sender, reply, port, false);
				
				break;
			case "request":
				String requestStr = child.getAttributes().getNamedItem("reply").getNodeValue();
				boolean requestReply = requestStr.equals("yes");
				if(!requestReply){
					owner.ProcessChatMessage(sender + " nekade din anslutning", "System", Color.black);
					break;
				}
				owner.ProcessChatMessage(sender + " är nu ansluten.", "System", Color.black);
				break;
			case "disconnect":
                relayMessage(connections, conn, string);
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

    void ParseFileResponse(Connection conn, String sender, boolean reply, int port, boolean timeOut) {
        PendingFileTransfer pendingFileTransfer = pendingFileRequests.peek();

        if (!timeOut) {
            pendingFileTransfer.responseReceived();
            pendingFileRequests.remove();

            if(pendingFileTransfer.isTimedOut()) {
                return;
            }
        }

        if(reply) {
            owner.ProcessFileTransferResponse(conn, port, pendingFileTransfer.getFilePath());
        } else {
            if(timeOut) {
                owner.ProcessChatMessage(sender + " svarade inte på din filöverföringsförfrågan.", "System", Color.black);
            } else {
                owner.ProcessChatMessage(sender + " nekade din filöverföring.", "System", Color.black);
            }
        }
    }

    private void ParseEncryptedMessage(Connection conn, String sender, Node child) {
		//Prepare message and get encryption type
		String decryptedMessage;
		String encryption = child.getAttributes().getNamedItem("type").getNodeValue();
		String key = child.getAttributes().getNamedItem("key").getNodeValue();
        String encryptedMessage = child.getFirstChild().getNodeValue();

		switch(encryption) {
		case "aes":
            decryptedMessage = EncryptionHelper.Decrypt(EncryptionHelper.Encryption.AES, encryptedMessage, key);
			break;

            case "caesar":
            decryptedMessage = EncryptionHelper.Decrypt(EncryptionHelper.Encryption.CAESAR, encryptedMessage, key);
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

	public int GetLocalPort() {
		return connections.get(0).GetLocalPort();
	}    

    public Connection[] getUsers() {
        return connections.toArray(new Connection[connections.size()]);
    }
	
}
