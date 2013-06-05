package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;

class GroupChatListener extends Thread {
	private ServerSocket server;
	private ChatMdl model;
	private boolean running = true;
	
	public GroupChatListener(ChatMdl model, int port) throws IOException {
		server = new ServerSocket(port);
		this.model = model;
	}
	
	public void run() {
		while(running) {
			try {
				model.addConnection(new Connection(server.accept()));
			} catch (IOException e) {
			}
		}
	}

	public void abort() {
        try {
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		running = false;
	}

    public int getPort() {
        return server.getLocalPort();
    }
}
