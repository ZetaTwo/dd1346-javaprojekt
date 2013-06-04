package se.kth.f.carlcarl.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ColorChooserView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7647290776582643746L;
	private final JColorChooser colorChooser = new JColorChooser();
	private int result = 0;

	public ColorChooserView() {
		JButton accept = new JButton("OK");
	    accept.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  Ok();
	      }
	    });

	    JButton cancel = new JButton("Cancel");
	    cancel.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  Cancel();
	        dispose();
	      }

		
	    });

	    JButton none = new JButton("None");
	    none.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  None();
	      }
	    });
	    
	    JPanel buttons = new JPanel();
	    buttons.add(accept);
	    buttons.add(none);
	    buttons.add(cancel);

	    setLayout(new BorderLayout());
	    add(colorChooser, BorderLayout.CENTER);
	    add(buttons, BorderLayout.SOUTH);
	    setModal(true);
	    pack();
	}

	  void None() {
		  result = 1;
		  colorChooser.setColor(0, 0, 0);
		  dispose();
		
	}

	private void Ok() {
		  result = 1;
		  dispose();
	}
	  
	  private void Cancel() {
		  result = 0;
			dispose();
		}

	public int getResult() {
		return result;
	}

	public Color getColor() {
		return colorChooser.getColor();
	}
}
