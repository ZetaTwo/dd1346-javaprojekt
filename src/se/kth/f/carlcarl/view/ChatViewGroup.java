package se.kth.f.carlcarl.view;
import se.kth.f.carlcarl.model.Connection;

import javax.swing.*;

public class ChatViewGroup extends ChatView {
    private final JList<Connection> list;
    private final int port;

    /**
	 * Create the panel.
	 */
	public ChatViewGroup(int port) {
        this.port = port;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		springLayout.putConstraint(SpringLayout.NORTH, editorPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, editorPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, editorPane, 0, SpringLayout.SOUTH, this);
		add(editorPane);

        list = new JList<>();
		springLayout.putConstraint(SpringLayout.EAST, editorPane, -6, SpringLayout.WEST, list);
		springLayout.putConstraint(SpringLayout.NORTH, list, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, list, -150, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, list, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, list, 0, SpringLayout.EAST, this);
		add(list);
	}

    public void Update(Connection[] connections) {
        list.removeAll();
        list.setListData(connections);
    }

    @Override
    public Connection getConnection() {
        return list.getSelectedValue();
    }

    @Override
    public int getPort() {
        return port;
    }
}
