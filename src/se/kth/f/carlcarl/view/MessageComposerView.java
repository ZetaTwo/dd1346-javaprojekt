package se.kth.f.carlcarl.view;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

import se.kth.f.carlcarl.model.MessageSettings;

public class MessageComposerView extends JPanel {
	private static final long serialVersionUID = 3010982094099123535L;

	Color currentColor;
	JButton boldButton, italicsButton, colorButton;
	JTextPane editorPane;
	JComboBox<String> encryptionComboBox;

    /**
	 * Create the panel.
	 */
	public MessageComposerView() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		boldButton = new JButton("B");
		boldButton.setAction(new BoldAction());
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(boldButton);
		springLayout.putConstraint(SpringLayout.NORTH, boldButton, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, boldButton, 0, SpringLayout.WEST, this);
		add(boldButton);
		
		italicsButton = new JButton("I");
		italicsButton.setAction(new ItalicAction());
		buttonGroup.add(italicsButton);
		springLayout.putConstraint(SpringLayout.NORTH, italicsButton, 0, SpringLayout.NORTH, boldButton);
		springLayout.putConstraint(SpringLayout.WEST, italicsButton, 6, SpringLayout.EAST, boldButton);
		springLayout.putConstraint(SpringLayout.SOUTH, italicsButton, 0, SpringLayout.SOUTH, boldButton);
		add(italicsButton);
		
		colorButton = new JButton("Color");
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ColorChooserView dialog = new ColorChooserView();
				dialog.setVisible(true);
				
				if(dialog.getResult() == 1) {
					currentColor = dialog.getColor();
				}
			}
		});
		
		springLayout.putConstraint(SpringLayout.NORTH, colorButton, 0, SpringLayout.NORTH, boldButton);
		springLayout.putConstraint(SpringLayout.WEST, colorButton, 6, SpringLayout.EAST, italicsButton);
		springLayout.putConstraint(SpringLayout.SOUTH, colorButton, 0, SpringLayout.SOUTH, boldButton);
		add(colorButton);
		
		encryptionComboBox = new JComboBox<>();
		encryptionComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Okrypterat", "Caesar", "AES"}));
		springLayout.putConstraint(SpringLayout.NORTH, encryptionComboBox, 0, SpringLayout.NORTH, boldButton);
		springLayout.putConstraint(SpringLayout.WEST, encryptionComboBox, 6, SpringLayout.EAST, colorButton);
		springLayout.putConstraint(SpringLayout.SOUTH, encryptionComboBox, 0, SpringLayout.SOUTH, boldButton);
		add(encryptionComboBox);
		
		
		editorPane = new JTextPane();
		editorPane.setContentType("text/html");
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 6, SpringLayout.SOUTH, boldButton);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, 0, SpringLayout.EAST, this);
		add(editorPane);
	}
	
	public MessageSettings getSettings() {
		MessageSettings result = new MessageSettings();
		
		result.setBold(isBold());
		result.setItalics(isItalics());
		result.setColor(getActiveColor());
		result.setText(getText());
		result.setEncryption(getEncryption());
		
		return result;
	}
	
	public void UpdateSettings(MessageSettings settings) {
		setActiveColor(settings.getColor());
		setText(settings.getText());
		setEncryption(settings.getEncryption());
		if(!isBold() == settings.getBold()){
			setBold();
		}
		if(!isItalics() == settings.getItalics()){
			setItalics();
		}
	}
	
	public String getText() {
		String text = editorPane.getText();
		text = text.replaceAll("</?(?:(?!(?:[bi]|font)\\b)[^>])*>", ""); //Remove everything except <b>, <i>, <font>
		text = text.trim(); //Remove unessecary whitespace
		text = text.replace("\r\n", "");
		
		return text;
	}
	
	public void setText(String htmlString) {
		editorPane.setText(htmlString);
	}
	
	public String getEncryption() {
		return (String)encryptionComboBox.getSelectedItem();
	}
	
	public void setEncryption(String encryption) {
		int s = encryptionComboBox.getComponentCount();
		int i = 0;
		while(i < s) {
			if(encryption.equals(encryptionComboBox.getComponent(i))) {
				encryptionComboBox.setSelectedIndex(i);
			}
			i++;
		}
	}
	
	public boolean isBold() {
		return ((BoldAction) boldButton.getAction()).isActive();
	}
	
	public void setBold() {
		((BoldAction) boldButton.getAction()).activate();
	}
	
	public boolean isItalics() {
		return ((ItalicAction) italicsButton.getAction()).isActive();
	}
	
	public void setItalics() {
		((ItalicAction) italicsButton.getAction()).activate();
	}
	
	public Color getActiveColor() {
		return currentColor;
	}
	
	public void setActiveColor(Color color) {
		currentColor = color;
	}
	
	class BoldAction extends StyledEditorKit.StyledTextAction {
	  private static final long serialVersionUID = 9174670038684056758L;
	  boolean isActive;
	  
	  public BoldAction() {
	    super("font-bold");
	    
	    putValue(NAME, "B");
		putValue(SHORT_DESCRIPTION, "Makes the text bold.");
	  }

	  public String toString() {
	    return "B";
	  }

	  public void actionPerformed(ActionEvent e) {
	    JEditorPane editor = getEditor(e);
	    if (editor != null) {
	      StyledEditorKit kit = getStyledEditorKit(editor);
	      MutableAttributeSet attr = kit.getInputAttributes();
	      boolean bold = (!StyleConstants.isBold(attr));
	      SimpleAttributeSet sas = new SimpleAttributeSet();
	      StyleConstants.setBold(sas, bold);
	      setCharacterAttributes(editor, sas, false);
	      isActive = !isActive;
	    }
	  }
	  
	  public boolean isActive() {
		  return isActive;
	  }
	  
	  public void activate() {
		  if(!isActive) {
			  StyledEditorKit kit = getStyledEditorKit(editorPane);
		      MutableAttributeSet attr = kit.getInputAttributes();
		      boolean bold = (!StyleConstants.isBold(attr));
		      SimpleAttributeSet sas = new SimpleAttributeSet();
		      StyleConstants.setBold(sas, bold);
		      setCharacterAttributes(editorPane, sas, false);
		      isActive = !isActive;
		  }
	  }
	}
	
	class ItalicAction extends StyledEditorKit.StyledTextAction {

		  private static final long serialVersionUID = -1428340091100055456L;
		  boolean isActive;
		  
		  public ItalicAction() {
		    super("font-italic");
		    
		    putValue(NAME, "I");
			putValue(SHORT_DESCRIPTION, "Makes the text italicized.");
		  }

		  public String toString() {
		    return "Italic";
		  }

		  public void actionPerformed(ActionEvent e) {
		    JEditorPane editor = getEditor(e);
		    if (editor != null) {
		      StyledEditorKit kit = getStyledEditorKit(editor);
		      MutableAttributeSet attr = kit.getInputAttributes();
		      boolean italic = (!StyleConstants.isItalic(attr));
		      SimpleAttributeSet sas = new SimpleAttributeSet();
		      StyleConstants.setItalic(sas, italic);
		      setCharacterAttributes(editor, sas, false);
		      isActive = !isActive;
		    }
		  }
		  
		  public boolean isActive() {
			  return isActive;
		  }
		  
		  public void activate() {
		      StyledEditorKit kit = getStyledEditorKit(editorPane);
		      MutableAttributeSet attr = kit.getInputAttributes();
		      boolean italic = (!StyleConstants.isItalic(attr));
		      SimpleAttributeSet sas = new SimpleAttributeSet();
		      StyleConstants.setItalic(sas, italic);
		      setCharacterAttributes(editorPane, sas, false);
		      isActive = !isActive;
		  }
		}
	
	/*class ForegroundAction extends StyledEditorKit.StyledTextAction {

		  private static final long serialVersionUID = 6384632651737400352L;
		 
		  JColorChooser colorChooser = new JColorChooser();

		  JDialog dialog = new JDialog();

		  boolean noChange = false;

		  boolean cancelled = false;

		  public ForegroundAction() {
		    super("foreground");

		    putValue(NAME, "Color");
			putValue(SHORT_DESCRIPTION, "Change the font color.");
		  }

		  public void actionPerformed(ActionEvent e) {
		    JTextPane editor = (JTextPane) getEditor(e);

		    if (editor == null) {
		      JOptionPane.showMessageDialog(null,
		          "You need to select the editor pane before you can change the color.", "Error",
		          JOptionPane.ERROR_MESSAGE);
		      return;
		    }
		    int p0 = editor.getSelectionStart();
		    StyledDocument doc = getStyledDocument(editor);
		    Element paragraph = doc.getCharacterElement(p0);
		    AttributeSet as = paragraph.getAttributes();
		    fg = StyleConstants.getForeground(as);
		    if (fg == null) {
		      fg = Color.BLACK;
		    }
		    colorChooser.setColor(fg);

		    JButton accept = new JButton("OK");
		    accept.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		        fg = colorChooser.getColor();
		        dialog.dispose();
		      }
		    });

		    JButton cancel = new JButton("Cancel");
		    cancel.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		        cancelled = true;
		        dialog.dispose();
		      }
		    });

		    JButton none = new JButton("None");
		    none.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		        noChange = true;
		        dialog.dispose();
		      }
		    });

		    JPanel buttons = new JPanel();
		    buttons.add(accept);
		    buttons.add(none);
		    buttons.add(cancel);

		    dialog.getContentPane().setLayout(new BorderLayout());
		    dialog.getContentPane().add(colorChooser, BorderLayout.CENTER);
		    dialog.getContentPane().add(buttons, BorderLayout.SOUTH);
		    dialog.setModal(true);
		    dialog.pack();
		    dialog.setVisible(true);

		    if (!cancelled) {

		      MutableAttributeSet attr = null;
		      if (editor != null) {
		        if (fg != null && !noChange) {
		          attr = new SimpleAttributeSet();
		          StyleConstants.setForeground(attr, fg);
		          setCharacterAttributes(editor, attr, false);
		        }
		      }
		    }// end if color != null
		    noChange = false;
		    cancelled = false;
		  }

		  private Color fg;
		  
		  public Color getColor() {
			  if(fg != null){
				  return fg;
			  }
			  return Color.black;
		  }
		  
		  public void setColor(Color color) {
			  SimpleAttributeSet attr = new SimpleAttributeSet();
	          StyleConstants.setForeground(attr, color);
	          setCharacterAttributes(editorPane, attr, false);
		  }	  
	}*/
	
	public void Clear() {
		this.editorPane.setText("");
	}
}
