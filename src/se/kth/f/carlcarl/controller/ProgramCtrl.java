package se.kth.f.carlcarl.controller;

import se.kth.f.carlcarl.helper.EncryptionHelper;
import se.kth.f.carlcarl.model.ConnListenerMdl;
import se.kth.f.carlcarl.model.Connection;
import se.kth.f.carlcarl.model.FileTransferMdl;
import se.kth.f.carlcarl.model.ProgramSettingsMdl;
import se.kth.f.carlcarl.view.ChatView;
import se.kth.f.carlcarl.view.FileTransferRequestView;
import se.kth.f.carlcarl.view.FileTransferView;
import se.kth.f.carlcarl.view.ProgramView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProgramCtrl {
	
	private ProgramSettingsMdl programMdl;
	private ProgramView programView;
	private final ArrayList<ChatCtrl> chatCtrls = new ArrayList<>();
	private ChatCtrl activeChat;
	private ConnListenerMdl connectionListener;
	
	
	private ProgramCtrl() throws IOException {
		programMdl = ProgramSettingsMdl.open("programSettings.ini");
		programView = new ProgramView(this);
		connectionListener = new ConnListenerMdl(this, programMdl.getListeningPort());
		connectionListener.start();
	}
	
	void SaveMessageSettings() {
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
	
	void newChat(Connection conn) {
		ChatCtrl ctrl;
		ctrl = new ChatCtrl(this, conn);
		programView.addChatView(addChat(ctrl), ctrl.GetLocalPort());
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

	public boolean Send(String text, String encryptionString, Color color) {
		boolean result = activeChat != null;
		if(result) {
            EncryptionHelper.Encryption encryption = EncryptionHelper.Encryption.NONE;
            switch (encryptionString) {
                case "Caesar":
                    encryption = EncryptionHelper.Encryption.CAESAR;
                    break;
                case "AES":
                    encryption = EncryptionHelper.Encryption.AES;
                    break;
                default:
                    break;
            }
			activeChat.Send(text, encryption, color);
		}
		
		return result;
	}

	public boolean FileTransferRequest(String user, String fileName, int fileSize, String message) {
		FileTransferRequestView dialog = new FileTransferRequestView(programView, user, fileName, fileSize, message);
		dialog.setVisible(true);
        return dialog.getResult() == 1;
	}

	public void FileTransferResponse(Connection conn, int port, String filePath) {
        FileTransferMdl fileTransferMdl = FileTransferMdl.Connect(conn, port, filePath);
        FileTransferView fileTransferView = new FileTransferView(programView, fileTransferMdl);
        fileTransferView.setVisible(true);
	}
	
	public void ChatRequest(Connection conn) {
		ChatRequest("Någon", conn, "");
	}

	public void ChatRequest(String username, Connection conn, String message) {
		int answer = JOptionPane.showConfirmDialog(programView, username + " vill chatta med dig.", "Chattförfrågan", JOptionPane.YES_NO_OPTION);
		if(answer == JOptionPane.YES_OPTION) {
            //TODO: Invectigate behaviour
			newChat(conn);
			conn.getOut().println("<message sender=\""+ getUserName() + "\"><request reply=\"yes\">" + message + "</request> </message>");
		}
		else{
			conn.getOut().println("<message sender=\""+ getUserName() + "\"><request reply=\"no\">" + message + "</request> </message>");
			conn.Close();
		}
	}

	public void ChangeListeningPort(int port) throws IOException {
		connectionListener.setListeningPort(port);		
	}
	
	public int getListeningPort() {
		return connectionListener.getListeningPort();
	}

	public void SendFileTransferRequest(Connection connection, File file, long fileSize,
			String message) {
		String user = programMdl.getUserName();
		activeChat.SendFileRequest(connection, user, file, fileSize, message);
		
	}

	public ChatView closeCurrentChat() {
		ChatView result = activeChat.getView();
		activeChat.Close();
		
		return result;
	}

    public int CreateFileTransfer(String fileName, int fileSize) {
        FileTransferMdl fileTransfer = FileTransferMdl.Host(fileName, fileSize);
        FileTransferView fileTransferView = new FileTransferView(programView, fileTransfer);
        fileTransferView.setVisible(true);

        return fileTransfer.getPort();
    }

	
    public String getUserName() {
		return getSettings().getUserName();
	}
}

	
