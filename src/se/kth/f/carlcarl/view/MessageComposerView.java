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

	private Color currentColor;
	private final JButton boldButton;
    private final JButton italicsButton;
    private final JTextPane editorPane;
	private final JComboBox<String> encryptionComboBox;

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

        JButton colorButton = new JButton("Color");
		colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ColorChooserView dialog = new ColorChooserView();
                dialog.setVisible(true);

                if (dialog.getResult() == 1) {
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
	
	void setText(String htmlString) {
		editorPane.setText(htmlString);
	}
	
	public String getEncryption() {
		return (String)encryptionComboBox.getSelectedItem();
	}
	
	void setEncryption(String encryption) {
		int s = encryptionComboBox.getComponentCount();
		int i = 0;
		while(i < s) {
			if(encryption.equals(encryptionComboBox.getComponent(i).toString())) {
				encryptionComboBox.setSelectedIndex(i);
			}
			i++;
		}
	}
	
	boolean isBold() {
		return ((BoldAction) boldButton.getAction()).isActive();
	}
	
	void setBold() {
		((BoldAction) boldButton.getAction()).activate();
	}
	
	boolean isItalics() {
		return ((ItalicAction) italicsButton.getAction()).isActive();
	}
	
	void setItalics() {
		((ItalicAction) italicsButton.getAction()).activate();
	}
	
	public Color getActiveColor() {
		return currentColor;
	}
	
	void setActiveColor(Color color) {
		currentColor = color;
	}
	
	class BoldAction extends StyledEditorKit.StyledTextAction {
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

	public void Clear() {
		this.editorPane.setText("");
	}
}
