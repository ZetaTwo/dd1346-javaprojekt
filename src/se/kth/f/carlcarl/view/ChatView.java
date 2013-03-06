package se.kth.f.carlcarl.view;

import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class ChatView extends JPanel {
	private static final long serialVersionUID = 9064849823829997292L;

	String bodyText = "";
	protected JEditorPane editorPane = new JEditorPane();
	
	/**
	 * Create the panel.
	 */
	public ChatView() {
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
	}
	
	public void addMessage(String text, String user, Color color) {
		String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
		bodyText += user + ": <font color=\"" + colorString + "\">" + text + "</font><br>\n";
		editorPane.setText("<html><head></head><body>" + bodyText + "</body></html>");
	}
}
