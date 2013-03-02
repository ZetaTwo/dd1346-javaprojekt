package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.model.ChatMdl;
import se.kth.f.carlcarl.model.MessageSettings;
import se.kth.f.carlcarl.view.ChatView;

public class ChatCtrl {
	ChatMdl model;
	ChatView view;
	
	public ChatCtrl() {
		
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
