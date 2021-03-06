package se.kth.f.carlcarl.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class FileTransferListenerMdl extends Thread {
    private final FileTransferMdl transferMdl;
    private ServerSocket server;

    public FileTransferListenerMdl(FileTransferMdl fileTransferMdl) {
        this.transferMdl = fileTransferMdl;
        try {
            server = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = server.accept();
            transferMdl.connect(new Connection(socket));
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getListeningPort() {
        return server.getLocalPort();
    }
}
