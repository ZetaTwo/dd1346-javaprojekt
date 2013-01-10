package se.kth.f.carlcarl;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class NewChatRequestView extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7222428308087082953L;

	public NewChatRequestView(String name){
		this.setTitle("Chatförfrågan");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		// Skapar container och komponenter
		
		Container contentPane = this.getContentPane();
	
		JLabel nameLabel = new JLabel(name + " vill chatta med dig");
		JButton yesButton = new JButton("Acceptera");
		JButton noButton = new JButton("Neka");
		
		// Lägger till komponenterna
		contentPane.add(nameLabel);
		contentPane.add(yesButton);
		contentPane.add(noButton);
		
		// Layoutar -- fulhackad layout.
		SpringLayout layout = new SpringLayout();
		
		int buttonWidth = (int) (yesButton.getPreferredSize().getWidth() + noButton.getPreferredSize().getWidth() + 2);
		int labelWidth = (int) nameLabel.getPreferredSize().getWidth();
		int widthDiff  = (buttonWidth - labelWidth)/2;
		
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 20, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, yesButton, 20, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, noButton, 20, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.SOUTH, contentPane, 20, SpringLayout.SOUTH, noButton);
		
		
		layout.putConstraint(SpringLayout.WEST, nameLabel, 40, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, contentPane, 40, SpringLayout.EAST, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, yesButton, -widthDiff, SpringLayout.WEST, nameLabel);
	//	layout.putConstraint(SpringLayout.WEST, noButton, 2, SpringLayout.EAST, yesButton);
		layout.putConstraint(SpringLayout.EAST, noButton, widthDiff, SpringLayout.EAST, nameLabel);
		
		setResizable(false);
		
		// Packar och visar
		setLayout(layout);
		pack();
		setVisible(true);
		
	}
	
	public NewChatRequestView() {
		this("Någon");
	}

}
	
