package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;

public class GroupChatListener extends Thread {
	ServerSocket server;
	ChatMdl model;
	boolean running = true;
	
	public GroupChatListener(ChatMdl model, int port) throws IOException {
		server = new ServerSocket(port);
		this.model = model;
	}
	
	public void run() {
		while(running) {
			try {
				model.addConnection(new Connection(server.accept()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void abort() {
		running = false;
	}
}
