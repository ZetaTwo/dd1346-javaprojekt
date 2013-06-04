package se.kth.f.carlcarl.view;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

class SettingsView extends JDialog {

	private final JTextField nameField;
	private final JFormattedTextField portField;
	
	private int result;
	
	public SettingsView(Window parent, String name, int port) {
		super(parent, "Inst�llningar", Dialog.ModalityType.APPLICATION_MODAL);
		
		// Titel och exitonclose
		//setTitle("Inst�llningar");
		
		// Skapar komponenter
		JLabel nameLabel = new JLabel("Name :");
		nameField = new JTextField(name, 15);
		
		JLabel portLabel = new JLabel("Lysningsport :");
		
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(false);
		format.setParseIntegerOnly(true);
		format.setMaximumFractionDigits(0);
		format.setMaximumIntegerDigits(5);
		
		portField = new JFormattedTextField(format);
		portField.setValue(port);
		
		JButton yesButton = new JButton("Ok");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ok();
			}
		});
		
		JButton noButton = new JButton("Avbryt");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancel();
			}
		});
		
		// L�gger till komponenterna
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
		
		// Packar
		getContentPane().setLayout(layout);
		pack();
	}
	
	private void Ok() {
		result = 1;
		Close();
	}
	
	private void Cancel() {
		result = 0;
		Close();
	}
	
	private void Close() {
		dispose();
	}
	
	public int getResult() {
		return result;
	}
	
	public int getListeningPort() {
		return Integer.parseInt(portField.getText());
	}
	
	public String getName() {
		return nameField.getText();
	}
	
}