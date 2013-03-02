package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.model.ChatMdl;
import se.kth.f.carlcarl.model.MessageSettings;

public class ChatCtrl {
	ChatMdl model;
	
	public ChatCtrl() {
		
	}
	
	public void UpdateSettings(MessageSettings settings) {
		model.UpdateSettings(settings);
	}

	public MessageSettings getSettings() {
		return model.getSettings();
	}
}
