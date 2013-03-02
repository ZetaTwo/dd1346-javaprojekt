package se.kth.f.carlcarl.controller;

import java.io.IOException;

import se.kth.f.carlcarl.model.GroupChatMdl;
import se.kth.f.carlcarl.view.ChatViewGroup;

public class GroupChatCtrl extends ChatCtrl {

	public GroupChatCtrl(int port) throws IOException {
		model = new GroupChatMdl(this, port);
		model.start();
		view = new ChatViewGroup();
	}

}
