package se.kth.f.carlcarl.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import se.kth.f.carlcarl.model.ProgramSettingsMdl;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.FileTransferRequestView;
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
			ctrl = new GroupChatCtrl(this, port);
		} catch (IOException e) {
			return null;
		}
		return addChat(ctrl);
	}
	
	public ChatView connectChat(String adress, int port) {
		ChatCtrl ctrl;
		try {
			ctrl = new ChatCtrl(this, adress, port);
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
	}

	public boolean Send(String text, String encryption, Color color) {
		boolean result = activeChat != null;
		if(result) {
			activeChat.Send(text, encryption, color);
		}
		
		return result;
	}

	public boolean FileTransferRequest(String user, String fileName, int fileSize, String message) {
		FileTransferRequestView dialog = new FileTransferRequestView(programView, user, fileName, fileSize, message);
		dialog.setVisible(true);
		if(dialog.getResult() == 1) {
			//Accept request
			return true;
		} else {
			//Deny request
			return false;
		}
		
	}

	public void FileTransferResponse(boolean reply, int port) {
		// TODO Auto-generated method stub
		
	}

	public boolean ChatRequest(boolean groupChat, String username) {
		if(username.isEmpty()) {
			username = "Någon";
		}
		int answer = JOptionPane.showConfirmDialog(programView, username + " vill chatta med dig.", "Chattförfrågan", JOptionPane.YES_NO_OPTION);
		return (answer == JOptionPane.YES_OPTION);		
		 
	}

	public void SendFileTransferRequest(String fileName, long fileSize,
			String message) {
		String user = programMdl.getUserName();
		activeChat.SendFileRequest(user, fileName, fileSize, message);
		
	}

	public ChatView closeCurrentChat() {
		ChatView result = activeChat.getView();
		activeChat.Close();
		
		return result;
	}
}
