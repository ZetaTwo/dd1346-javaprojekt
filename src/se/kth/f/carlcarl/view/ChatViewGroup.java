package se.kth.f.carlcarl.view;
import javax.swing.SpringLayout;
import javax.swing.JEditorPane;
import javax.swing.JList;


public class ChatViewGroup extends ChatView {
	private static final long serialVersionUID = -129893716411983668L;

	JEditorPane editorPane;
	
	/**
	 * Create the panel.
	 */
	public ChatViewGroup() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		editorPane = new JEditorPane();
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		add(editorPane);
		
		JList<String> list = new JList<String>();
		springLayout.putConstraint(SpringLayout.EAST, editorPane, -6, SpringLayout.WEST, list);
		springLayout.putConstraint(SpringLayout.NORTH, list, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, list, -150, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, list, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, list, 0, SpringLayout.EAST, this);
		add(list);

	}
	
	public void addMessage(String text, String user) {
		editorPane.setText(editorPane.getText() + user + ": " + text);
	}
}
