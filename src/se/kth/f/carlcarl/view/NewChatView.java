package se.kth.f.carlcarl.view;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class NewChatView extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2563969029790271758L;

	
	private final JFormattedTextField portTextField;
	private final JTextField adressTextField;
	private final JRadioButton radioGroup;
    private final JRadioButton radioSingle;
	int result;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	
	public NewChatView(Window parent, int port) {
		super(parent, "Ny chat", Dialog.ModalityType.APPLICATION_MODAL);
				
		//Skapar container och komponenter		
		Container contentPane = new Container();
		
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(false);
		format.setParseIntegerOnly(true);
		format.setMaximumFractionDigits(0);
		format.setMaximumIntegerDigits(5);
		
		portTextField = new JFormattedTextField(format);
		portTextField.setValue(port);
		
		JLabel portLabel = new JLabel("Port :");
		
		JLabel adressLabel = new JLabel("Adress :");
		adressTextField = new JTextField(10);
		adressTextField.setEditable(false);
		
		radioSingle = new JRadioButton("Anslut till en chat");
		radioGroup = new JRadioButton("Skapa gruppchat");
		radioSingle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(radioSingle.isSelected()) {
					adressTextField.setEditable(true);
				}
				else {
					adressTextField.setEditable(false);
				}
			}
		});
				
		radioGroup.setSelected(true);
		buttonGroup.add(radioGroup);		
		buttonGroup.add(radioSingle);
		
		JButton yesButton = new JButton("Ok");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ok();
			}
		});
		JButton noButton = new JButton("Avbryt");		
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cancel();
			}
		});
		
		// L�gger till componenterna
		contentPane.add(portLabel);
		contentPane.add(portTextField);
		contentPane.add(radioGroup);
		contentPane.add(radioSingle);
		contentPane.add(adressLabel);
		contentPane.add(adressTextField);
		contentPane.add(yesButton);
		contentPane.add(noButton);

		// L�ser dimensionerna
		Dimension dimension = new Dimension(200,170);
		contentPane.setPreferredSize(dimension);
		setResizable(false);
		
		// Layoutar
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.NORTH, adressLabel, 9, SpringLayout.SOUTH, radioSingle);
		layout.putConstraint(SpringLayout.NORTH, adressTextField, -3, SpringLayout.NORTH, adressLabel);
		layout.putConstraint(SpringLayout.WEST, adressTextField, 8, SpringLayout.EAST, adressLabel);
		layout.putConstraint(SpringLayout.SOUTH, portLabel, -145, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, radioGroup, 29, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, portTextField, -3, SpringLayout.NORTH, portLabel);
		layout.putConstraint(SpringLayout.WEST, portTextField, 6, SpringLayout.EAST, portLabel);
		layout.putConstraint(SpringLayout.EAST, portTextField, -109, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, portLabel, 18, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.WEST, adressLabel, 0, SpringLayout.WEST, portLabel);
		layout.putConstraint(SpringLayout.NORTH, radioSingle, 6, SpringLayout.SOUTH, radioGroup);
		layout.putConstraint(SpringLayout.WEST, radioSingle, 0, SpringLayout.WEST, portLabel);
		layout.putConstraint(SpringLayout.WEST, radioGroup, 18, SpringLayout.WEST, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, noButton, -35, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, noButton, -10, SpringLayout.SOUTH, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, yesButton, -5, SpringLayout.WEST, noButton);
		layout.putConstraint(SpringLayout.SOUTH, yesButton, -10, SpringLayout.SOUTH, contentPane);
		
		// Packar ihop och visar
		setContentPane(contentPane);
		getContentPane().setLayout(layout);
		pack();
	}

	private void Ok() {
		result = 1;
		Close();
	}
	
	public int getListeningPort() {
		return Integer.parseInt(portTextField.getText());
	}
	
	public String getAdress() {
		return adressTextField.getText();
	}
	
	private void Cancel() {
		result = 0;
		Close();
	}
	
	private void Close() {
		dispose();
	}
	
	public int getChatType() {
		if(buttonGroup.getSelection() == radioGroup.getModel()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int getResult(){
		return result;
	}
	
	public static void main(String[] args) {
		try {
			NewChatView dialog = new NewChatView(null, 20122);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}
