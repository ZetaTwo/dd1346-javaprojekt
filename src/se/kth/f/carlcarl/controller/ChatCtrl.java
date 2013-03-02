package se.kth.f.carlcarl.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import se.kth.f.carlcarl.model.ChatMdl;
import se.kth.f.carlcarl.model.MessageSettings;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.ChatViewSingle;

public class ChatCtrl {
	ChatMdl model;
	ChatView view;
	
	protected ChatCtrl() {
		
	}
	
	public ChatCtrl(String adress, int port) throws UnknownHostException, IOException {
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
}
