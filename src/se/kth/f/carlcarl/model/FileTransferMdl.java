package se.kth.f.carlcarl.model;

import java.io.*;
import java.net.Socket;

public class FileTransferMdl extends Thread {
    Connection connection;
    boolean send = false;
    String filePath;
    int fileSize;
    int bytesProcessed = 0;

    public static FileTransferMdl Host(int port, String filePath, int fileSize) {
        FileTransferMdl result = new FileTransferMdl();
        FileTransferListenerMdl listenerMdl = new FileTransferListenerMdl(result, port);
        result.filePath = filePath;
        result.fileSize = fileSize;
        listenerMdl.start();

        return result;
    }

    public static FileTransferMdl Connect(Connection conn, int port, String filePath) {
        FileTransferMdl result = new FileTransferMdl();

        try {
            Connection newconn = new Connection(new Socket(conn.GetAdress(), port));
            result.connect(newconn);
            result.send = true;
            result.filePath = filePath;
            result.start();
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

    public void run() {
        if(send) {
            File file = new File(filePath);
            FileOutputStream in = null;

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                BufferedInputStream bis = new BufferedInputStream(connection.getSocket().getInputStream());
                byte[] byteArray = new byte[1000];
                int i=0;

                while ((i = bis.read(byteArray)) != fileSize){ //in = int; byteArray = byte[]
                    bos.write(byteArray, 0, i);
                    bytesProcessed += i;
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            File file = new File(filePath);
            FileOutputStream in = null;

            try {
                int len = (int)file.length();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream bos = new BufferedOutputStream(connection.getSocket().getOutputStream());
                byte[] byteArray = new byte[1000];
                int i=0;
                while ((i = bis.read(byteArray)) != len){ //in = int; byteArray = byte[]
                    bos.write(byteArray, 0, i);
                    bytesProcessed += i;
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    public int getFileSize() {
        return fileSize;
    }

    public int getBytesProcessed() {
        return bytesProcessed;
    }

    public String getFilePath() {
        return filePath;
    }
}
