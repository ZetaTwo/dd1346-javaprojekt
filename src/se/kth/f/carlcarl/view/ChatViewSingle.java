package se.kth.f.carlcarl.view;
import se.kth.f.carlcarl.model.Connection;

import javax.swing.SpringLayout;

public class ChatViewSingle extends ChatView {
	private static final long serialVersionUID = 7989847204259584555L;

    private final Connection connection;

	/**
	 * Create the panel.
	 */
	public ChatViewSingle(Connection connection) {
        this.connection = connection;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, editorPane, 0, SpringLayout.EAST, this);
		add(editorPane);
	}

    @Override
    public Connection getConnection() {
        return connection;
    }
}
