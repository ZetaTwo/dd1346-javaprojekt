package se.kth.f.carlcarl.view;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

class FileTransferStartView extends JDialog {
	private final JTextField textField;
	private final JTextArea textArea;
	private int result;
	
	public FileTransferStartView(Window parent) {
		super(parent, "Filöverföring", Dialog.ModalityType.APPLICATION_MODAL);
				
		setBounds(100, 100, 450, 300);
		
		SpringLayout springLayout = new SpringLayout();
		
		getContentPane().setLayout(springLayout);
		
		JLabel lblFil = new JLabel("Fil :");
		springLayout.putConstraint(SpringLayout.NORTH, lblFil, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblFil, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblFil);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, lblFil);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnVljFil = new JButton("V\u00E4lj fil...");
		btnVljFil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choosingFile();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnVljFil, 27, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnVljFil);
		springLayout.putConstraint(SpringLayout.EAST, btnVljFil, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnVljFil);
		
		JLabel lblMeddelande = new JLabel("Meddelande :");
		springLayout.putConstraint(SpringLayout.NORTH, lblMeddelande, 27, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, lblMeddelande, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblMeddelande);
		
		textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 6, SpringLayout.SOUTH, lblMeddelande);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, btnVljFil);
		getContentPane().add(textArea);
		
		JButton btnAvbryt = new JButton("Avbryt");
		btnAvbryt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancel();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -6, SpringLayout.NORTH, btnAvbryt);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAvbryt, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAvbryt, 0, SpringLayout.EAST, btnVljFil);
		getContentPane().add(btnAvbryt);
		
		JButton btnSkicka = new JButton("Skicka");
		btnSkicka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ok();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnSkicka, 0, SpringLayout.NORTH, btnAvbryt);
		springLayout.putConstraint(SpringLayout.EAST, btnSkicka, -6, SpringLayout.WEST, btnAvbryt);
		getContentPane().add(btnSkicka);
		
	}
	
	private void choosingFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(FileTransferStartView.this);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             String filePath = fileChooser.getSelectedFile().getAbsolutePath();
             textField.setText(filePath);
        }
	}
	
	private void Ok() {
		result = 1;
		File f = new File(this.getFilePath());
		if(!f.exists() || f.isDirectory()){
			JOptionPane.showMessageDialog(this, "Filen existerar inte", "Filöverföringsfel",JOptionPane.ERROR_MESSAGE);
			return;
		}
		Close();
	}
	
	private void Cancel() {
		result = 0;
		Close();
	}
	
	private void Close() {
		dispose();
	}
	
	public String getFilePath() {
		return textField.getText();
	}
	
	public String getMessage() {
		return textArea.getText();
	}
	
	public int getResult() {
		return result;
	}
}