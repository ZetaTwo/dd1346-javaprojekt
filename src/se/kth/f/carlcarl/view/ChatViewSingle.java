package se.kth.f.carlcarl.view;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JEditorPane;


public class ChatViewSingle extends ChatView {
	private static final long serialVersionUID = 7989847204259584555L;

	JEditorPane editorPane;
	
	/**
	 * Create the panel.
	 */
	public ChatViewSingle() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		editorPane = new JEditorPane();
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, 0, SpringLayout.EAST, this);
		add(editorPane);

		/*JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		editorPane = new JEditorPane();
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, 0, SpringLayout.EAST, scrollPane);
		scrollPane.add(editorPane);*/
	}
	
	public void addMessage(String text, String user) {
		editorPane.setText(editorPane.getText() + user + ": " + text);
	}
}
