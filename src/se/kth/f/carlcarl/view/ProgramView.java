package se.kth.f.carlcarl.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.kth.f.carlcarl.controller.ProgramCtrl;
import se.kth.f.carlcarl.model.Connection;
import se.kth.f.carlcarl.model.ProgramSettingsMdl;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ProgramView extends JFrame {

	private static final long serialVersionUID = -7022872006598970444L;
    private JTabbedPane tabbedPane;
	public final MessageComposerView messageComposerView;
	private final ProgramCtrl ctrl;
	int chatIndex = 1;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public ProgramView(ProgramCtrl owner) {
		this.ctrl = owner;
		setTitle("CoC Chat");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 640);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        while(tabbedPane.getTabCount() > 0) {
	        	CloseChat();
	        }
	      }
	    });
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, contentPane);
		contentPane.add(tabbedPane);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof JTabbedPane) {
					ChatView view = (ChatView) tabbedPane.getSelectedComponent();
					if(view != null) {
						ctrl.setActiveChat(view);
					}
				}
			}
		});
		
		
		Action actionListenerTabbed = new AbstractAction() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        NextTab();
		      }
		 };
		String ACTION_KEY_TABBED = "The Action";
		KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK, false);
		InputMap inputMapTabbed = tabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMapTabbed.put(tab, ACTION_KEY_TABBED);
		ActionMap actionMapTabbed = tabbedPane.getActionMap();
		actionMapTabbed.put(ACTION_KEY_TABBED, actionListenerTabbed);
		tabbedPane.setActionMap(actionMapTabbed);
		
		
		JButton closeButton = new JButton("Close");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, closeButton, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, closeButton, 0, SpringLayout.EAST, contentPane);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CloseChat();
			}
		});
		contentPane.add(closeButton);
		
		JButton sendButton = new JButton("Send");
		sl_contentPane.putConstraint(SpringLayout.WEST, sendButton, 0, SpringLayout.WEST, closeButton);
		sl_contentPane.putConstraint(SpringLayout.EAST, sendButton, 0, SpringLayout.EAST, tabbedPane);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Send();
			}
		});
		
	
		 Action actionListener = new AbstractAction() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        Send();
		      }
		 };
		String ACTION_KEY = "The Action";
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_DOWN_MASK, false);
		InputMap inputMap = sendButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(enter, ACTION_KEY);
		ActionMap actionMap = sendButton.getActionMap();
		actionMap.put(ACTION_KEY, actionListener);
		sendButton.setActionMap(actionMap);
		
		
		
		
		contentPane.add(sendButton);
		
		JButton sendFileButton = new JButton("Send file");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, sendButton, -6, SpringLayout.NORTH, sendFileButton);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, sendFileButton, -6, SpringLayout.NORTH, closeButton);
		sl_contentPane.putConstraint(SpringLayout.EAST, sendFileButton, 0, SpringLayout.EAST, tabbedPane);
		sendFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendFile();
			}
		});
		
		contentPane.add(sendFileButton);
		
		messageComposerView = new MessageComposerView();
		sl_contentPane.putConstraint(SpringLayout.WEST, closeButton, 6, SpringLayout.EAST, messageComposerView);
		sl_contentPane.putConstraint(SpringLayout.WEST, messageComposerView, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, messageComposerView, -6, SpringLayout.WEST, sendFileButton);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, messageComposerView, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tabbedPane, -6, SpringLayout.NORTH, messageComposerView);
		sl_contentPane.putConstraint(SpringLayout.NORTH, messageComposerView, -110, SpringLayout.SOUTH, contentPane);
		contentPane.add(messageComposerView);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem newChatItem = new JMenuItem("New chat...");
		newChatItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewChat();
			}
		});
		fileMenu.add(newChatItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Exit();
			}
		});
		fileMenu.add(exitItem);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem settingsItem = new JMenuItem("Settings");
		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditSettings();
			}
		});
		editMenu.add(settingsItem);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpAbout();
			}
		});
		helpMenu.add(aboutItem);
	}
	
	void NextTab() {
		int tabCount = tabbedPane.getTabCount();
		int currentIndex = tabbedPane.getSelectedIndex();
		if(tabCount - currentIndex == 1){
			tabbedPane.setSelectedIndex(0);
			return;
		}
		tabbedPane.setSelectedIndex(currentIndex + 1);
	}

	private void CloseChat() {
		ChatView closedView = ctrl.closeCurrentChat();
		if(closedView != null) {
			tabbedPane.remove(closedView);	
			if(tabbedPane.getTabCount() > 0) {
				tabbedPane.setSelectedIndex(0);
			}
		}
	}

	private void HelpAbout() {
		AboutBoxView aboutBox = new AboutBoxView();
		aboutBox.setVisible(true);
	}

	private void EditSettings() {
		ProgramSettingsMdl settings = ctrl.getSettings();
		SettingsView dialog = new SettingsView(this, settings.getUserName(), settings.getListeningPort());
		dialog.setVisible(true);
		
		if(dialog.getResult() == 1) {
			int port = dialog.getListeningPort();
			ctrl.ChangeListeningPort(port);
			port = ctrl.getListeningPort();
			ctrl.getSettings().setListeningPort(port);
			String name = dialog.getName();
			ctrl.getSettings().setUserName(name);
			try {
				ctrl.getSettings().save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void Exit() {
		this.dispose();
	}

	private void NewChat() {
		NewChatView newChatView = new NewChatView(this, ctrl.getSettings().getListeningPort() + 1);
		newChatView.setVisible(true);
		int port = newChatView.getListeningPort();
		//Process results
		if(newChatView.result == 1) {
			ChatView newView;
			if(newChatView.getChatType() == 1) {
				// Group chat
				newView = ctrl.newChat(port);
				port = ctrl.getListeningPort();
			} else {
				// Single chat
				newView = ctrl.connectChat(newChatView.getAdress(), port);
			}
			
			addChatView(newView, port);
			
		}
	}
	
	private void Send() {
		String text = messageComposerView.getText();
		if(text.length() == 0){
			return;
		}
		String encryption = messageComposerView.getEncryption();
		Color color = messageComposerView.getActiveColor();
		
		if(ctrl.Send(text, encryption, color)) {
			messageComposerView.Clear();
		}
	}
	
	private void SendFile() {
        ChatView activeChat = (ChatView)this.tabbedPane.getSelectedComponent();
        if(activeChat == null) {
            JOptionPane.showMessageDialog(this, "Du måste välja en chat", "Filöverföringsfel",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection reciever = activeChat.getConnection();
        if(reciever == null) {
            JOptionPane.showMessageDialog(this, "Du måste välja en mottagare", "Filöverföringsfel",JOptionPane.ERROR_MESSAGE);
            return;
        }

		FileTransferStartView dialog = new FileTransferStartView(this);
		dialog.setVisible(true);
		
		if(dialog.getResult() == 1) {
			File file = new File(dialog.getFilePath());
			long fileSize = file.length();
			String message = dialog.getMessage();
			
			ctrl.SendFileTransferRequest(reciever, file, fileSize, message);
		}
	}


	
	public int IncomingChatRequest(String name) {
		return JOptionPane.showConfirmDialog(null, name + " vill chatta med dig. Vill du?", "Inkommande chatförfrågan", JOptionPane.YES_NO_OPTION);
	}
	
	public void addChatView(ChatView view, int port) {
		tabbedPane.add("Chat (port: " + port + ")" , view);
	}
	
	/* SettingsView v = new SetingsView();
	 * int result = v.show();
	 * if(result > 0) {
	 *      SPARA INSTÄLLNINGAR;
	 * }
	 * 
	 */
}
