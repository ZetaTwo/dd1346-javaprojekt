package se.kth.f.carlcarl.model;

import java.io.IOException;

import se.kth.f.carlcarl.controller.ChatCtrl;

public class GroupChatMdl extends ChatMdl {

    private static GroupChatListener listener;

    private GroupChatMdl(ChatCtrl owner) {
        super(owner);
    }

    public static GroupChatMdl CreateGroupChat(ChatCtrl owner, int port) {
        GroupChatMdl result = new GroupChatMdl(owner);
        try {
            listener = new GroupChatListener(result, port);
            listener.start();
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    @Override
    public void close(String sender) {
        super.close(sender);
        listener.abort();
    }

    @Override
    public int GetLocalPort() {
        return listener.getPort();
    }


}