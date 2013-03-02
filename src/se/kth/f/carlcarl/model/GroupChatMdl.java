package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;

public class GroupChatMdl extends ChatMdl {
	GroupChatListener listener;
	
	public GroupChatMdl(int port) throws IOException {
		listener = new GroupChatListener(this, port);
		listener.start();
	}
}