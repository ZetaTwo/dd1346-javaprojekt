package se.kth.f.carlcarl.view;

import java.awt.Window;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileTransferView extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -91035909390392840L;
	int result;

	public FileTransferView(Window parent, File file) {
		super(parent, "Filöverföring");
		
		String filename = file.getName();
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblFil = new JLabel("Fil :");
		springLayout.putConstraint(SpringLayout.NORTH, lblFil, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblFil, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblFil);
		
		JLabel lblFilename = new JLabel(filename);
		springLayout.putConstraint(SpringLayout.WEST, lblFilename, 6, SpringLayout.EAST, lblFil);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilename, 0, SpringLayout.SOUTH, lblFil);
		getContentPane().add(lblFilename);
		
		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 36, SpringLayout.SOUTH, lblFil);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(progressBar);
		// TODO Filöverföring
		// Make observer;
		
		JLabel lblTidKvar = new JLabel("Tid kvar :");
		springLayout.putConstraint(SpringLayout.NORTH, lblTidKvar, 6, SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.WEST, lblTidKvar, 0, SpringLayout.WEST, lblFil);
		getContentPane().add(lblTidKvar);
		
		JLabel label = new JLabel(timeLeft());
		springLayout.putConstraint(SpringLayout.WEST, label, 6, SpringLayout.EAST, lblTidKvar);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.SOUTH, lblTidKvar);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel(currentSpeed());
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 6, SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, progressBar);
		getContentPane().add(label_1);
		
		JLabel lblHastighet = new JLabel("Hastighet :");
		springLayout.putConstraint(SpringLayout.NORTH, lblHastighet, 6, SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, lblHastighet, -6, SpringLayout.WEST, label_1);
		getContentPane().add(lblHastighet);
		
		JButton btnAvbryt = new JButton("Avbryt");
		btnAvbryt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancel();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnAvbryt, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAvbryt, 0, SpringLayout.EAST, progressBar);
		getContentPane().add(btnAvbryt);
		
		JButton btnSlutfr = new JButton("Slutf\u00F6r");
		btnSlutfr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ok();
			}
		});
		btnSlutfr.setEnabled(false);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSlutfr, 0, SpringLayout.SOUTH, btnAvbryt);
		springLayout.putConstraint(SpringLayout.EAST, btnSlutfr, -6, SpringLayout.WEST, btnAvbryt);
		getContentPane().add(btnSlutfr);
		
		JLabel lblFilstorlek = new JLabel("Filstorlek :");
		springLayout.putConstraint(SpringLayout.NORTH, lblFilstorlek, 6, SpringLayout.SOUTH, lblFil);
		springLayout.putConstraint(SpringLayout.WEST, lblFilstorlek, 0, SpringLayout.WEST, lblFil);
		getContentPane().add(lblFilstorlek);
		
		JLabel lblFilesize = new JLabel(getFileSize(file));
		springLayout.putConstraint(SpringLayout.WEST, lblFilesize, 6, SpringLayout.EAST, lblFilstorlek);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilesize, 0, SpringLayout.SOUTH, lblFilstorlek);
		getContentPane().add(lblFilesize);
		
		setSize(450, 250);
		setResizable(false);	
	}
	
	private static String getFileSize(File file) {
		String[] suffixList = new String[]{"B", "kB", "MB", "GB", "TB"};
		long bytes = file.length();
		int numberOfItterations = 0;
		while(bytes > 1000) {
			bytes = bytes / 1000;
			numberOfItterations += 1;
		}
		return Long.toString(bytes) + " "  + suffixList[numberOfItterations];
	}

	private void Cancel(){
		result = 0;
		Close();
	}
	
	private void Ok() {
		result = 1;
		Close();
	}
	
	private void Close() {
		dispose();
	}
	
	private String timeLeft(){
		// TODO Filöverföring
		return "";
	}
	
	private String currentSpeed() {
		// TODO Filöverföring
		return "";
	}
	
	public static void main(String[] args) {
		try {
			FileTransferView dialog = new FileTransferView(null, new File("newFile.txt"));
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	