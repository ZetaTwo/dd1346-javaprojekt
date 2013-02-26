package se.kth.f.carlcarl.controller;

import java.io.IOException;

import se.kth.f.carlcarl.model.ProgramSettingsMdl;
import se.kth.f.carlcarl.view.NewChatView;
import se.kth.f.carlcarl.view.ProgramView;

public class ProgramCtrl {
	
	ProgramSettingsMdl programMdl;
	ProgramView programView;
	ChatCtrl[] chatCtrls;
	ChatCtrl activeChat;
	
	public ProgramCtrl(ProgramSettingsMdl pMdl, ProgramView view) throws IOException {
		programMdl = ProgramSettingsMdl.open("programSettings.ini");
		programView = new ProgramView();
	}
	
	public void setActiveChat(ChatCtrl chat) {
		activeChat = chat;
	}
	
	public void newChat() {
		NewChatView view = new NewChatView(programView, programMdl.getListeningPort());
		if(view.getResult() == 1) {
			
			
		}
		
		
	}

}
