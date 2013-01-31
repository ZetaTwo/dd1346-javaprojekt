package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMdl extends Thread {
	
	ChatMdl chat;
	ServerSocket server;
	boolean running = true;

	public ServerMdl(ChatMdl chat, int port) throws IOException {
		this.server = new ServerSocket(port);
		this.chat = chat;
	}
	
	public void shutdown() {
		running = false;
	}
	
	public void run() {
		while(running) {
			try {
				chat.connections.add(server.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
