package se.kth.f.carlcarl.controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import se.kth.f.carlcarl.model.ChatMdl;
import se.kth.f.carlcarl.model.Connection;
import se.kth.f.carlcarl.model.EncryptionHandler;
import se.kth.f.carlcarl.model.MessageSettings;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.ChatViewSingle;

public class ChatCtrl {
	ProgramCtrl owner;
	ChatMdl model;
	ChatView view;
	
	protected ChatCtrl(ProgramCtrl owner) {
		this.owner = owner;
	}
	public ChatCtrl(ProgramCtrl owner, Connection conn) {
		this.owner = owner;
		model = new ChatMdl(this, conn);
		model.start();
		view = new ChatViewSingle();
	}
	
	public ChatCtrl(ProgramCtrl owner, String adress, int port) throws UnknownHostException, IOException {
		this.owner = owner;
		model = new ChatMdl(this, adress, port);
		model.start();
		view = new ChatViewSingle();
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

	public void Send(String text, EncryptionHandler.Encryption encryption, Color color) {
		String username = owner.getSettings().getUserName();
        String key = "";
        switch(encryption) {
            case CASEAR:
                key = Integer.toString(owner.getSettings().getCeasarKey());
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
	
	public void ProcessFileTransferRequest(String username, String fileName, int fileSize, String message) {
		boolean accept = owner.FileTransferRequest(username, fileName, fileSize, message);
		
		// TODO Fil�verf�ring 
		// om ja, skapa en fil�verf�ringstr�d och view, koppla ihop med avs�ndaren.
		
		model.sendFileResponse(accept, "", 50000, owner.getSettings().getUserName());
	}
	
	public void ProcessFileTransferResponse(Connection conn, boolean reply, int port, String file) {
		owner.FileTransferResponse(conn, reply, port, file);
	}
	
	/*
	public void ProcessChatRequest(String username) {
		boolean accept = owner.ChatRequest(true, username);
	}
	*/
	
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
			key = String.valueOf(owner.getSettings().getCeasarKey());
		default:
			break;
		}
		
		if(!key.isEmpty()) {
			model.sendKeyresponse(owner.getSettings().getUserName(), key);
		}
	}

	public void SendFileRequest(String user, File file, long fileSize,
			String message) {
		model.sendFile(file, message, fileSize, user);
	}

	public void Close() {
		String username = owner.getSettings().getUserName();
		model.close(username);
	}
}
