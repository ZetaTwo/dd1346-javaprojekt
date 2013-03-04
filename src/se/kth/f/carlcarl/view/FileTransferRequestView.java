package se.kth.f.carlcarl.view;

import javax.swing.JDialog;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileTransferRequestView extends JDialog{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109817122107630020L;
	
	
	int result;

	public FileTransferRequestView(Window parent, String user, String fileName, int fileSize, String message) {
		super(parent, "Filöverföringsförfrågan", Dialog.ModalityType.APPLICATION_MODAL);
		
		setResizable(false);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblUservillChatta = new JLabel(user + " vill skicka en fil till dig.");
		springLayout.putConstraint(SpringLayout.NORTH, lblUservillChatta, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUservillChatta, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblUservillChatta);
		
		JLabel lblFil = new JLabel("Filnamn :"); 
		springLayout.putConstraint(SpringLayout.NORTH, lblFil, 6, SpringLayout.SOUTH, lblUservillChatta);
		springLayout.putConstraint(SpringLayout.WEST, lblFil, 0, SpringLayout.WEST, lblUservillChatta);
		getContentPane().add(lblFil);
		
		JLabel lblfilnamn = new JLabel(fileName);
		springLayout.putConstraint(SpringLayout.NORTH, lblfilnamn, 6, SpringLayout.SOUTH, lblUservillChatta);
		springLayout.putConstraint(SpringLayout.WEST, lblfilnamn, 6, SpringLayout.EAST, lblFil);
		getContentPane().add(lblfilnamn);
		
		JLabel lblMeddelande = new JLabel("Meddelande :");
		springLayout.putConstraint(SpringLayout.NORTH, lblMeddelande, 6, SpringLayout.SOUTH, lblFil);
		springLayout.putConstraint(SpringLayout.WEST, lblMeddelande, 0, SpringLayout.WEST, lblUservillChatta);
		getContentPane().add(lblMeddelande);
		
		JTextPane txtpnHej = new JTextPane();
		txtpnHej.setText(message);
		txtpnHej.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtpnHej, 6, SpringLayout.SOUTH, lblMeddelande);
		springLayout.putConstraint(SpringLayout.WEST, txtpnHej, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtpnHej, 126, SpringLayout.SOUTH, lblMeddelande);
		getContentPane().add(txtpnHej);
		
		JLabel lblVillDuTa = new JLabel("Vill du ta emot filen?");
		springLayout.putConstraint(SpringLayout.NORTH, lblVillDuTa, 6, SpringLayout.SOUTH, txtpnHej);
		springLayout.putConstraint(SpringLayout.WEST, lblVillDuTa, 0, SpringLayout.WEST, lblUservillChatta);
		getContentPane().add(lblVillDuTa);
		
		JButton btnNej = new JButton("Nej");
		springLayout.putConstraint(SpringLayout.EAST, txtpnHej, 0, SpringLayout.EAST, btnNej);
		btnNej.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancel();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnNej, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNej, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnNej);
		
		JButton btnJa = new JButton("Ja");
		btnJa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ok();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnJa, 0, SpringLayout.NORTH, btnNej);
		springLayout.putConstraint(SpringLayout.EAST, btnJa, -6, SpringLayout.WEST, btnNej);
		getContentPane().add(btnJa);
		
		JLabel lblFileSize = new JLabel();
		springLayout.putConstraint(SpringLayout.WEST, lblFileSize, 0, SpringLayout.WEST, btnNej);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFileSize, 0, SpringLayout.SOUTH, lblFil);
		getContentPane().add(lblFileSize);
		
		JLabel lblFilstorlek = new JLabel("Filstorlek :");
		lblFilstorlek.setText(getFileSize(fileSize));
		springLayout.putConstraint(SpringLayout.NORTH, lblFilstorlek, 0, SpringLayout.NORTH, lblFil);
		springLayout.putConstraint(SpringLayout.EAST, lblFilstorlek, 0, SpringLayout.EAST, btnJa);
		getContentPane().add(lblFilstorlek);
		
		
		setSize( 400, 280);
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
	
	public String getFileSize(int fileSize) {
		String[] suffixList = new String[]{"B", "kB", "MB", "GB", "TB"};
		int numberOfItterations = 0;
		while(fileSize > 1000) {
			fileSize = fileSize / 1000;
			numberOfItterations += 1;
		}
		return Long.toString(fileSize) + " "  + suffixList[numberOfItterations];
	}
	

	public static void main(String[] args) {
		try {
			FileTransferRequestView dialog = new FileTransferRequestView(null, "Peter", "file.jpg", 1024, "Hej jag tänkte skicka med den här lilla filen jag hoppas att den är trevlig jag vet inte riktigt vad den innehåller med det är säkert bra i alla fall.");
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
