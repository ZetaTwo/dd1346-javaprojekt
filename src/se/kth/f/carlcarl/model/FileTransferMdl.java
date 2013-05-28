package se.kth.f.carlcarl.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class FileTransferMdl {
    Connection connection;

    public static FileTransferMdl Host(int port) {
        FileTransferMdl result = new FileTransferMdl();
        FileTransferListenerMdl listenerMdl = new FileTransferListenerMdl(result, port);
        listenerMdl.start();

        return result;
    }

    public static FileTransferMdl Connect(Connection conn, int port, String filePath) {
        FileTransferMdl result = new FileTransferMdl();

        try {
            Connection newconn = new Connection(new Socket(conn.GetAdress(), port));
            result.connect(newconn);
            result.start(filePath);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    private FileTransferMdl() {

    }

    public void connect(Connection connection) {
        this.connection = connection;
    }

    public void start(String filePath) {
        File file = new File(filePath);
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buf = new byte[1024];
        try {
            while(in.read(buf)) {
                connection.getOut().write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
