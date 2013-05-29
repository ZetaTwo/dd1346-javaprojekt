package se.kth.f.carlcarl.model;

import java.io.*;
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
            int len = (int)file.length();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos = new BufferedOutputStream(connection.getSocket().getOutputStream());
            byte[] byteArray = new byte[1000];
            int i=0;
            while ((i = bis.read(byteArray)) != len){ //in = int; byteArray = byte[]
                bos.write(byteArray, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
