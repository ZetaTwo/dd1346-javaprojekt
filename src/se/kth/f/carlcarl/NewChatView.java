package se.kth.f.carlcarl;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class NewChatView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2563969029790271758L;

	public NewChatView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		//Skapar container och komponenter
		
		Container contentPane = new Container();
				
		JLabel portLabel = new JLabel("Port :");
		JTextField portTextField = new JTextField(4);
		
		JLabel adressLabel = new JLabel("Adress :");
		JTextField adressTextField = new JTextField(10);
		
		JRadioButton radioGroup = new JRadioButton("Skapa gruppchat");
		JRadioButton radioSingle = new JRadioButton("Anslut till en chat");
		
		JButton yesButton = new JButton("Ok");
		JButton noButton = new JButton("Avbryt");		
		
		
		// Gruppar radio-knapparna
		
		ButtonGroup radioButtons = new ButtonGroup();
		radioButtons.add(radioGroup);
		radioButtons.add(radioSingle);
		
		
		
		// Lägger till componenterna
		
		contentPane.add(portLabel);
		contentPane.add(portTextField);
		contentPane.add(radioGroup);
		contentPane.add(radioSingle);
		contentPane.add(adressLabel);
		contentPane.add(adressTextField);
		contentPane.add(yesButton);
		contentPane.add(noButton);
		

		// Låser dimensionerna
		
		Dimension dimension = new Dimension(200,170);
		contentPane.setPreferredSize(dimension);
		setResizable(false);
		
		
		// Layoutar
		
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.WEST, portLabel, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, portLabel, 5, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, portTextField, -5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, portTextField, 5, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.WEST, radioGroup, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, radioGroup, 20, SpringLayout.SOUTH, portLabel);
		
		layout.putConstraint(SpringLayout.WEST, radioSingle, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, radioSingle, 1, SpringLayout.SOUTH, radioGroup);
		
		layout.putConstraint(SpringLayout.WEST, adressLabel, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, adressLabel, 5, SpringLayout.SOUTH, radioSingle);
		
		layout.putConstraint(SpringLayout.EAST, adressTextField, -5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, adressTextField, 5, SpringLayout.SOUTH, radioSingle);
		
		layout.putConstraint(SpringLayout.EAST, noButton, -35, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, noButton, -10, SpringLayout.SOUTH, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, yesButton, -5, SpringLayout.WEST, noButton);
		layout.putConstraint(SpringLayout.SOUTH, yesButton, -10, SpringLayout.SOUTH, contentPane);
		
		
		// Packar ihop och visar
		
		setContentPane(contentPane);
		setLayout(layout);
		pack();
		setVisible(true);
				
		
	}
	
/*	
	public static void main(String args[]){
		NewChatView frame = new NewChatView();		
	}
*/
	
}
