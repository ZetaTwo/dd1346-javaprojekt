package se.kth.f.carlcarl;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SettingsView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 919092415372014804L;

	public SettingsView() {
		
		// Titel och exitonclose
		setTitle("Inställningar");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		// Skapar komponenter
		JLabel nameLabel = new JLabel("Name :");
		JTextField nameField = new JTextField(15);
		
		JLabel portLabel = new JLabel("Lysningsport :");
		JTextField portField = new JTextField(15);
		
		JButton yesButton = new JButton("Ok");
		JButton noButton = new JButton("Avbryt");
		
		
		// Lägger till komponenterna
		Container contentPane = this.getContentPane();
		
		contentPane.add(nameLabel);
		contentPane.add(nameField);
		contentPane.add(portLabel);
		contentPane.add(portField);
		contentPane.add(yesButton);
		contentPane.add(noButton);
		
		//Layoutar
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, portLabel, 10, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, portField, 10, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, yesButton, 35, SpringLayout.SOUTH, portLabel);
		layout.putConstraint(SpringLayout.NORTH, noButton, 35, SpringLayout.SOUTH, portLabel);
		layout.putConstraint(SpringLayout.SOUTH, contentPane, 10, SpringLayout.SOUTH, noButton);
		
		layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.EAST, nameField, -10, SpringLayout.EAST, contentPane);
		
		layout.putConstraint(SpringLayout.WEST, portLabel, 10, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.WEST, portField, 10, SpringLayout.EAST, portLabel);
		layout.putConstraint(SpringLayout.EAST, portField, -10, SpringLayout.EAST, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, noButton, -10, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.EAST, yesButton, -5, SpringLayout.WEST, noButton);
		
		layout.putConstraint(SpringLayout.EAST, contentPane, 200, SpringLayout.WEST, contentPane);
		
		setResizable(false);
		
		// Packar och visar
		setLayout(layout);
		pack();
		setVisible(true);
	}

}
