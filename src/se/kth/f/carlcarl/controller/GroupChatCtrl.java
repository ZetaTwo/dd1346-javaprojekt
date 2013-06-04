package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.model.GroupChatMdl;
import se.kth.f.carlcarl.view.ChatViewGroup;

import java.io.IOException;

public class GroupChatCtrl extends ChatCtrl {

	public GroupChatCtrl(ProgramCtrl owner, int port) throws IOException {
		super(owner);
		model = new GroupChatMdl(this, port);
		model.start();
		view = new ChatViewGroup();
	}

    @Override
    public void Update() {
        ((ChatViewGroup)view).Update(model.getUsers());
    }
}
