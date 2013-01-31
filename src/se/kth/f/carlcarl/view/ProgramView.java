package se.kth.f.carlcarl.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;

import se.kth.f.carlcarl.scrapbook.TestDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ProgramView extends JFrame {

	private static final long serialVersionUID = -7022872006598970444L;
	private JPanel contentPane;
	JTabbedPane tabbedPane;
	MessageComposerView messageComposerView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ProgramView frame = new ProgramView();
		frame.tabbedPane.addTab("Group chat 1", null, new ChatViewGroup());
		frame.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		frame.tabbedPane.addTab("Single chat 1", null, new ChatViewSingle());
		frame.tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		frame.tabbedPane.addTab("Group chat 2", null, new ChatViewGroup());
		frame.tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public ProgramView() {
		setTitle("CoC Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, contentPane);
		contentPane.add(tabbedPane);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Send();
			}
		});
		contentPane.add(sendButton);
		
		JButton sendFileButton = new JButton("Send file");
		sendFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendFile();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, sendButton, 0, SpringLayout.WEST, sendFileButton);
		sl_contentPane.putConstraint(SpringLayout.EAST, sendButton, 0, SpringLayout.EAST, sendFileButton);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, sendFileButton, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, sendFileButton, 0, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, sendButton, -6, SpringLayout.NORTH, sendFileButton);
		contentPane.add(sendFileButton);
		
		messageComposerView = new MessageComposerView();
		sl_contentPane.putConstraint(SpringLayout.WEST, messageComposerView, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, messageComposerView, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, messageComposerView, -6, SpringLayout.WEST, sendFileButton);
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
	
	private void HelpAbout() {
		//TODO: Insert AboutBoxView
		AboutBoxView aboutBox = new AboutBoxView();
		aboutBox.setVisible(true);
	}

	private void EditSettings() {
		//TODO: Insert SettingsView
		/*
		SettingsView settingsDialog = new SettingsView();
		settingsDialog.Display();
		//Process results
		controller.editSettings(...);
		*/
	}

	private void Exit() {
		this.dispose();
	}

	private void NewChat() {
		//TODO: Insert NewChatView
		/*
		NewChatView newChatView = new NewChatView();
		newChatView.Display();
		//Process results
		controller.newChat(...);
		 */
	}
	
	private void Send() {
		//TODO: Invoke controller
		//String text = messageComposerView.getText();
		//String encryption = messageComposerView.getEncryption();
	}
	
	private void SendFile() {
		//TODO: Create send file window
		TestDialog t = new TestDialog(this);
		t.setVisible(true);
		int res = t.getResult();
		if(res > 0) {
			System.out.println(t.getTestValue());
		}
	}
	
	public int IncomingChatRequest(String name) {
		return JOptionPane.showConfirmDialog(null, name + " vill chatta med dig. Vill du?", "Inkommande chatf�rfr�gan", JOptionPane.YES_NO_OPTION);
	}
	
	/* SettingsView v = new SetingsView();
	 * int result = v.show();
	 * if(result > 0) {
	 *      SPARA INST�LLNINGAR;
	 * }
	 * 
	 */
}