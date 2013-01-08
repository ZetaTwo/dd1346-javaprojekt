package se.kth.f.carlcarl;
import javax.swing.SpringLayout;
import javax.swing.JEditorPane;


public class ChatViewSingle extends ChatView {
	private static final long serialVersionUID = 7989847204259584555L;

	/**
	 * Create the panel.
	 */
	public ChatViewSingle() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JEditorPane editorPane = new JEditorPane();
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, 0, SpringLayout.EAST, this);
		add(editorPane);

	}

}
