package se.kth.f.carlcarl.view;

import se.kth.f.carlcarl.model.Connection;

import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

public abstract class ChatView extends JPanel {

	private String bodyText = "";
	final JEditorPane editorPane = new JEditorPane();
	
	/**
	 * Create the panel.
	 */
    ChatView() {
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
	}
	
	public void addMessage(String text, String user, Color color) {
		String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
		bodyText += user + ": <font color=\"" + colorString + "\">" + text + "</font><br>\n";
		editorPane.setText("<html><head></head><body>" + bodyText + "</body></html>");
	}

    public abstract Connection getConnection();
    public abstract int getPort();
}
