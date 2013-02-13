package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;

public class GroupChatMdl extends ChatMdl {
	ServerSocket server;
	
	public GroupChatMdl(int port) throws IOException {
		server = new ServerSocket(port);
	}
	
	public void Start() throws IOException {
		while(running) {
			connections.add(new Connection(server.accept()));
		}
	}
}
