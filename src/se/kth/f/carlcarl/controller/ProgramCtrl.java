package se.kth.f.carlcarl.controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.kth.f.carlcarl.model.ProgramSettingsMdl;
import se.kth.f.carlcarl.view.ChatViewGroup;
import se.kth.f.carlcarl.view.ChatViewSingle;
import se.kth.f.carlcarl.view.NewChatView;
import se.kth.f.carlcarl.view.ProgramView;

public class ProgramCtrl {
	
	ProgramSettingsMdl programMdl;
	ProgramView programView;
	ArrayList<ChatCtrl> chatCtrls;
	ChatCtrl activeChat;
	
	
	public ProgramCtrl() throws IOException {
		programMdl = ProgramSettingsMdl.open("programSettings.ini");
		programView = new ProgramView();
		
		
		// lol, blob
		// \begin{BLOB}
		programView.tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof JTabbedPane) {
					/*
					
					SaveMessageSettings();
					JTabbedPane pane = (JTabbedPane) e.getSource();
					Bortkommenterad för att saker ska kompilera
					 
					for(ChatCtrl s: chatCtrls){
						if(s.getView() == pane) {
							setActiveChat(s);
							updateMessageComposer();	
						}
					}
					*/
				}
			}
		});
		
		// \end{BLOB}
	}
	
	protected void SaveMessageSettings() {		
		activeChat.UpdateSettings(programView.messageComposerView.getSettings());
	}
	
	private void updateMessageComposer() {
		programView.messageComposerView.UpdateSEttings(activeChat.getSettings());
	}

	public void setActiveChat(ChatCtrl chat) {
		activeChat = chat;
	}
	
	public void newChat() {
		NewChatView view = new NewChatView(programView, programMdl.getListeningPort());
		if(view.getResult() == 1) {
			ChatCtrl newCtrl = new ChatCtrl();
			chatCtrls.add(newCtrl);	
		}
		
		
	}
	
	public static void main(String[] args) {
		ProgramCtrl.programView.setVisible(true);
		ProgramCtrl.programView.tabbedPane.addTab("Group chat 1", null, new ChatViewGroup());
		ProgramCtrl.programView.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		ProgramCtrl.programView.tabbedPane.addTab("Single chat 1", null, new ChatViewSingle());
		ProgramCtrl.programView.tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		ProgramCtrl.programView.tabbedPane.addTab("Group chat 2", null, new ChatViewGroup());
		ProgramCtrl.programView.tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
	}
	
	

}
