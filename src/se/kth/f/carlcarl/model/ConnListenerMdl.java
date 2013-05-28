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


import se.kth.f.carlcarl.controller.ProgramCtrl;

public class ConnListenerMdl extends Thread{
	
	ProgramCtrl owner;
	boolean running = false;
	ServerSocket listeningSocket;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	
	
	public ConnListenerMdl(ProgramCtrl owner, int port){
		try {
			this.owner = owner;
			listeningSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					owner.ChatRequest(false, "", "", conn);
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void Start(){
		running = true;
	}
	
	public void Stop() {
		running = false;
	}

	public void setListeningPort(int port) {
		this.Stop();
		try {
			listeningSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			listeningSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Start();
	}
	
	private void ParseMessage(String string, Connection conn) {
		
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
		owner.ChatRequest(false, sender, message, conn);
		
	}
}