package se.kth.f.carlcarl.model;

import java.io.IOException;

import se.kth.f.carlcarl.controller.ChatCtrl;

public class GroupChatMdl extends ChatMdl {
	GroupChatListener listener;
	
	public GroupChatMdl(ChatCtrl ctrl, int port) throws IOException {
		super(ctrl);
		listener = new GroupChatListener(this, port);
		listener.start();
	}
}