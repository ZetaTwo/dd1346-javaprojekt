package se.kth.f.carlcarl.model;

import java.io.IOException;

import se.kth.f.carlcarl.controller.ChatCtrl;

public class GroupChatMdl extends ChatMdl {

    public GroupChatMdl(ChatCtrl ctrl, int port) throws IOException {
		super(ctrl);
        GroupChatListener listener = new GroupChatListener(this, port);
		listener.start();
	}
}