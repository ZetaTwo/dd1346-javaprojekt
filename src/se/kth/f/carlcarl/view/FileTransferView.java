package se.kth.f.carlcarl.view;

import se.kth.f.carlcarl.model.FileTransferMdl;

import java.awt.Window;
import java.io.File;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileTransferView extends JDialog implements ActionListener {
	private int result;
    private final FileTransferMdl fileTransferMdl;
    private final JProgressBar progressBar;
    private final JButton btnSlutfr;

    public FileTransferView(Window parent, FileTransferMdl fileTransferMdl) {
		super(parent, "Filöverföring");
		
		this.fileTransferMdl = fileTransferMdl;
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblFil = new JLabel("Fil :");
		springLayout.putConstraint(SpringLayout.NORTH, lblFil, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblFil, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblFil);
		
		JLabel lblFilename = new JLabel(fileTransferMdl.getFilePath());
		springLayout.putConstraint(SpringLayout.WEST, lblFilename, 6, SpringLayout.EAST, lblFil);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilename, 0, SpringLayout.SOUTH, lblFil);
		getContentPane().add(lblFilename);

        progressBar = new JProgressBar();
        progressBar.setMaximum((int)fileTransferMdl.getFileSize());
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 36, SpringLayout.SOUTH, lblFil);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(progressBar);
		// TODO Fil�verf�ring
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
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 6
                , SpringLayout.SOUTH, progressBar);
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

        btnSlutfr = new JButton("Slutför");
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
		
		JLabel lblFilesize = new JLabel(getFileSize(fileTransferMdl.getFileSize()));
		springLayout.putConstraint(SpringLayout.WEST, lblFilesize, 6, SpringLayout.EAST, lblFilstorlek);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFilesize, 0, SpringLayout.SOUTH, lblFilstorlek);
		getContentPane().add(lblFilesize);

        Timer timer = new Timer(40, this);
        timer.start();

		setSize(450, 250);
		setResizable(false);	
	}
	
	private static String getFileSize(long bytes) {
		String[] suffixList = new String[]{"B", "kB", "MB", "GB", "TB"};
		int numberOfIterations = 0;
		while(bytes > 1000) {
			bytes = bytes / 1000;
			numberOfIterations += 1;
		}
		return Long.toString(bytes) + " "  + suffixList[numberOfIterations];
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

    //TODO: Do we care?
    public int getResult() {
        return result;
    }

    // TODO: Filöverföring
	private String timeLeft(){
		return "";
	}

    // TODO: Filöverföring
	private String currentSpeed() {
		return "";
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        progressBar.setValue(fileTransferMdl.getBytesProcessed());
        if(fileTransferMdl.getBytesProcessed() == fileTransferMdl.getFileSize()) {
            btnSlutfr.setEnabled(true);
        }
        this.repaint();
    }
}
	