package se.kth.f.carlcarl.controller;

import java.io.IOException;
import java.util.ArrayList;

import se.kth.f.carlcarl.model.ProgramSettingsMdl;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.ProgramView;

public class ProgramCtrl {
	
	ProgramSettingsMdl programMdl;
	ProgramView programView;
	ArrayList<ChatCtrl> chatCtrls = new ArrayList<>();
	ChatCtrl activeChat;
	
	
	public ProgramCtrl() throws IOException {
		programMdl = ProgramSettingsMdl.open("programSettings.ini");
		programView = new ProgramView(this);
	}
	
	protected void SaveMessageSettings() {
		if(activeChat != null) {
			activeChat.UpdateSettings(programView.messageComposerView.getSettings());
		}
	}
	
	private void updateMessageComposer() {
		programView.messageComposerView.UpdateSettings(activeChat.getSettings());
	}

	public void setActiveChat(ChatView view) {
		SaveMessageSettings();
		for(ChatCtrl s: chatCtrls){
			if(s.getView() == view) {
				activeChat = s;
				updateMessageComposer();	
			}
		}
	}
	
	public ChatView newChat(int port) {
		ChatCtrl ctrl;
		try {
			ctrl = new GroupChatCtrl(port);
		} catch (IOException e) {
			return null;
		}
		return addChat(ctrl);
	}
	
	public ChatView connectChat(String adress, int port) {
		ChatCtrl ctrl;
		try {
			ctrl = new ChatCtrl(adress, port);
		} catch (IOException e) {
			return null;
		}
		return addChat(ctrl);
	}
	
	private ChatView addChat(ChatCtrl ctrl) {
		chatCtrls.add(ctrl);
		return ctrl.getView();
	}
	
	private void Start() {
		programView.setVisible(true);
	}

	public ProgramSettingsMdl getSettings() {
		return programMdl;
	}
	
	public static void main(String[] args) throws IOException {
		ProgramCtrl ctrl = new ProgramCtrl();
		ctrl.Start();
		
		/*ctrl.programView.tabbedPane.addTab("Group chat 1", null, new ChatViewGroup());
		ctrl.programView.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		ctrl.programView.tabbedPane.addTab("Single chat 1", null, new ChatViewSingle());
		ctrl.programView.tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		ctrl.programView.tabbedPane.addTab("Group chat 2", null, new ChatViewGroup());
		ctrl.programView.tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);*/
	}

	public void Send(String text, String encryption) {
		activeChat.Send(text, encryption);
	}
}
