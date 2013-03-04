package se.kth.f.carlcarl.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import se.kth.f.carlcarl.model.ChatMdl;
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
	
	public void RecieveMessage(String message, String user) {
		view.addMessage(message, user);
	}

	public void Send(String text, String encryption) {
		model.sendMessage(text, "Chat User");
		view.addMessage(text, "Chat User");
	}
	
	public void ProcessChatMessage(String message, String username) {
		view.addMessage(message, username);
	}
	
	public void ProcessFileTransferRequest(String fileName, int fileSize, String message) {
		owner.FileTransferRequest(fileName, fileSize, message);
	}
	
	public void ProcessFileTransferResponse(boolean reply, int port) {
		owner.FileTransferResponse(reply, port);
	}
	
	public void ProcessChatRequest(String username) {
		owner.ChatRequest(username);
	}
	
	public void ProcessDisconnect(String username) {
		view.addMessage(" has disconnected.", username);
		owner.Disconnect(this);
	}
	
	public void ProcessKeyRequest(String type) {
		//get relevant key
		//model.sendkeyreponse
	}
}
