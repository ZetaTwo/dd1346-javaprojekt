package se.kth.f.carlcarl.view;

import javax.swing.JDialog;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class AboutBoxView extends JDialog{

	public AboutBoxView() {
		setTitle("Om");
		setModalityType(ModalityType.APPLICATION_MODAL);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		setBounds(100, 100, 300, 150);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Close();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 93, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -10, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(btnOk);
		
		JLabel lblProgrammetSkrivetAv = new JLabel("Programmet skrivet av CoC enterprises");
		springLayout.putConstraint(SpringLayout.NORTH, lblProgrammetSkrivetAv, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblProgrammetSkrivetAv, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblProgrammetSkrivetAv);
		
		JLabel lblKontaktCarlsvenmensase = new JLabel("Kontakt: carlsven@mensa.se");
		springLayout.putConstraint(SpringLayout.NORTH, lblKontaktCarlsvenmensase, 6, SpringLayout.SOUTH, lblProgrammetSkrivetAv);
		springLayout.putConstraint(SpringLayout.WEST, lblKontaktCarlsvenmensase, 0, SpringLayout.WEST, lblProgrammetSkrivetAv);
		getContentPane().add(lblKontaktCarlsvenmensase);

	}


	private void Close() {
		dispose();
	}
}
