package se.kth.f.carlcarl.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import se.kth.f.carlcarl.controller.ProgramCtrl;

public class ConnListenerMdl extends Thread{
	
	ProgramCtrl owner;
	boolean running = true;
	ServerSocket listeningSocket;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	int listeningPort;
	boolean notdone = true;
	
	
	public ConnListenerMdl(ProgramCtrl owner, int port){
		
		this.owner = owner;
		findSocket(listeningPort);
		
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
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void Start(){
		this.running = true;
	}
	
	public void Stop() {
		this.running = false;
	}

	public void setListeningPort(int port) {
		Stop();
		try {
			listeningSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		findSocket(port);
		if(listeningSocket.isClosed()){
			System.out.println("lol");
		}
		Start();
	}
	
	private void findSocket(int port) {
		listeningPort = port;
		notdone = true;
		
		while(notdone) {
			try {
				listeningSocket = new ServerSocket(listeningPort);
				notdone = false;
			} catch (Exception e) {
				e.printStackTrace();
				listeningPort++;
				if(listeningPort > 65536){
				}
			}
		}
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
		owner.ChatRequest(false, sender,  conn, message);
		
	}
}