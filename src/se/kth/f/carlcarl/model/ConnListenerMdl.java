package se.kth.f.carlcarl.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import se.kth.f.carlcarl.controller.ProgramCtrl;
import se.kth.f.carlcarl.helper.NetworkHelper;

public class ConnListenerMdl extends Thread{
	
	private final ProgramCtrl owner;
	private boolean running = true;
	private ServerSocket listeningSocket;
    private DocumentBuilder builder;
	Document xmlDoc;
	private int listeningPort;


    public ConnListenerMdl(ProgramCtrl owner, int port) throws IOException {
		
		this.owner = owner;
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}
        setListeningPort(port);
		
	}
	public void run() {
		while(running){
			try {
				Socket connSock = listeningSocket.accept();
				Connection conn = new Connection(connSock);
				Thread.sleep(500);
				String data = "";
				while(conn.in.ready()){
					data += conn.in.readLine();
				}
				if(!data.isEmpty()){
					ParseMessage(data, conn);
				}
				else {
					owner.ChatRequest(conn);
				}
			}
			catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	void Start(){
		running = true;
	}
	
	void Stop() {
		running = false;
	}

	public void setListeningPort(int port) throws IOException {
		Stop();
		try {
			listeningSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

        try {
            listeningSocket = NetworkHelper.findNextOpenPort(port);
        } catch (Exception e) {
            try {
                listeningSocket = NetworkHelper.findNextOpenPort(0);
            } catch (IOException e1) {
                throw new IOException("No port available");
            }
        }
        Start();
	}
	
	public int getListeningPort() {
		return listeningPort;
	}
	
	private void ParseMessage(String string, Connection conn) throws SAXException, IOException {
		
		//Create xml-doc from string
		String encoding = "utf-8"; //UTF-8 or latin1
		InputSource inputSource = new InputSource(new ByteArrayInputStream(string.getBytes(encoding)));
		Document xmlDoc = builder.parse(inputSource);
		Node root = xmlDoc.getFirstChild();
		
		//Get sender and root
		String sender = root.getAttributes().getNamedItem("sender").getTextContent();
		Node child = root.getFirstChild();
		
		//Process message
		String message = child.getNodeValue();
		owner.ChatRequest(false, sender, conn, message);
	}
}