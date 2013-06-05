package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.model.GroupChatMdl;
import se.kth.f.carlcarl.view.ChatViewGroup;

import java.io.IOException;

public class GroupChatCtrl extends ChatCtrl {

    public static GroupChatCtrl CreateGroupChat(ProgramCtrl owner, int port) {
        GroupChatCtrl result = new GroupChatCtrl(owner);

        result.model = GroupChatMdl.CreateGroupChat(result, port);
        if(result.model == null) {
            return null;
        }

        result.model.start();
        result.view = new ChatViewGroup(result.model.GetLocalPort());

        return result;
    }

	private GroupChatCtrl(ProgramCtrl owner) {
		super(owner);
	}

    @Override
    public void Update() {
        ((ChatViewGroup)view).Update(model.getUsers());
    }
}
