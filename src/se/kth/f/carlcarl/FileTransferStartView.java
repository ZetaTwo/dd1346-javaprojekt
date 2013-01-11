package se.kth.f.carlcarl;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileTransferStartView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -662484651376974878L;
	
	JTextField fileField; 
	JTextArea messageArea;
	private JTextField textField;
	
	public FileTransferStartView() {
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
		
		JTextArea textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 6, SpringLayout.SOUTH, lblMeddelande);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, btnVljFil);
		getContentPane().add(textArea);
		
		JButton btnAvbryt = new JButton("Avbryt");
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -6, SpringLayout.NORTH, btnAvbryt);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAvbryt, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAvbryt, 0, SpringLayout.EAST, btnVljFil);
		getContentPane().add(btnAvbryt);
		
		JButton btnSkicka = new JButton("Skicka");
		springLayout.putConstraint(SpringLayout.NORTH, btnSkicka, 0, SpringLayout.NORTH, btnAvbryt);
		springLayout.putConstraint(SpringLayout.EAST, btnSkicka, -6, SpringLayout.WEST, btnAvbryt);
		getContentPane().add(btnSkicka);
		
		setVisible(true);

		
	}
	
	private void choosingFile() {
		
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(FileTransferStartView.this);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             String filePath = fileChooser.getSelectedFile().getAbsolutePath();
             fileField.setText(filePath);
        }
	}
	
	public static void main(String args[]) {
		FileTransferStartView view = new FileTransferStartView();
	}
}