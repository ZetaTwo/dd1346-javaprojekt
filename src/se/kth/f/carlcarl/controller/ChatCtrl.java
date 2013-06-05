package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.helper.EncryptionHelper;
import se.kth.f.carlcarl.model.ChatMdl;
import se.kth.f.carlcarl.model.Connection;
import se.kth.f.carlcarl.model.MessageSettings;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.ChatViewSingle;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChatCtrl {
	private final ProgramCtrl owner;
	ChatMdl model;
	ChatView view;

    ChatCtrl(ProgramCtrl owner) {
        this.owner = owner;
    }

    ChatCtrl FinishConstruction(ChatMdl model) {
        if(model == null) {
            return null;
        }

        this.model = model;
        this.model.start();
        this.view = new ChatViewSingle(this.model.getUsers()[0]);

        return this;
    }

    public static ChatCtrl CreateChat(ProgramCtrl owner, Connection conn) {
        ChatCtrl result = new ChatCtrl(owner);
        ChatMdl model = ChatMdl.ConnectChat(result, conn);

        return result.FinishConstruction(model);
    }

    public static ChatCtrl CreateChat(ProgramCtrl owner, String address, int port) {
        ChatCtrl result = new ChatCtrl(owner);
        ChatMdl model = ChatMdl.ConnectChat(result, address, port);

        return result.FinishConstruction(model);
    }
	
	public void UpdateSettings(MessageSettings settings) {
		model.UpdateSettings(settings);
	}

	public MessageSettings getSettings() {
		return model.getSettings();
	}
	
	public ChatView getView() {
		return view;
	}

	public void Send(String text, EncryptionHelper.Encryption encryption, Color color) {
		String username = owner.getSettings().getUserName();
        String key = "";
        switch(encryption) {
            case CAESAR:
                key = Integer.toString(owner.getSettings().getCaesarKey());
                break;
            case AES:
                key = owner.getSettings().getAESKey();
                break;
            default:
                break;
        }

		model.sendMessage(text, username, color, encryption, key);
		view.addMessage(text, username, color);
	}
	
	public void ProcessChatMessage(String message, String username, Color color) {
		view.addMessage(message, username, color);
	}
	
	public void ProcessFileTransferRequest(String username, String fileName, int fileSize, String message, Connection connection) {
		boolean accept = owner.FileTransferRequest(username, fileName, fileSize, message);

        int port = 0;
        if(accept) {
            port = owner.CreateFileTransfer(fileName, fileSize);
        }

        model.sendFileResponse(accept, message, port, owner.getSettings().getUserName(), connection);
	}

    public void ProcessFileTransferResponse(Connection conn, int port, String file) {
		owner.FileTransferResponse(conn, port, file);
	}
	
	public void ProcessDisconnect(String username) {
		view.addMessage(username + " has disconnected.", "System", Color.black);
	}
	
	public void ProcessKeyRequest(String type) {
		String key = "";
		switch(type) {
		case "RSA":
			key = owner.getSettings().getAESKey();
			break;
		case "Caesar":
			key = String.valueOf(owner.getSettings().getCaesarKey());
		default:
			break;
		}
		
		if(!key.isEmpty()) {
			model.sendKeyresponse(owner.getSettings().getUserName(), key);
		}
	}

	public void SendFileRequest(Connection connection, String user, File file, long fileSize,
			String message) {
		model.sendFile(file, message, fileSize, user, connection);
	}

	public void Close() {
		String username = owner.getSettings().getUserName();
		model.close(username);
	}

	public int GetLocalPort() {
		return model.GetLocalPort();
	}
	
   	public void Update() {
   	}
	
	public String getUserName() {
		return owner.getUserName();
	}
}
