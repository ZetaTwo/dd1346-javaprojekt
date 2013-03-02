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
		model = new ChatMdl(adress, port);
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
}
