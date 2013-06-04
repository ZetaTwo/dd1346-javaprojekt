package se.kth.f.carlcarl.model;

import java.io.*;
import java.net.Socket;

public class FileTransferMdl extends Thread {
    private Connection connection;
    private boolean send = false;
    private String filePath;
    private long fileSize;
    private int bytesProcessed = 0;

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
            result.send = true;
            result.filePath = filePath;
            File file = new File(filePath);
            result.fileSize = file.length();
            result.connect(newconn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private FileTransferMdl() {

    }

    public void connect(Connection connection) {
        this.connection = connection;
        start();
    }

    public void run() {
        File file = new File(filePath);
        BufferedInputStream bis;
        BufferedOutputStream bos;

        try {
            //Setup streams
            if(send) {
                bis = new BufferedInputStream(new FileInputStream(file));
                bos = new BufferedOutputStream(connection.getSocket().getOutputStream());
            } else {
                bis = new BufferedInputStream(connection.getSocket().getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(file));
            }

            //Setup buffer
            byte[] byteArray = new byte[1024];
            int i;

            //Read and write
            while ((i = bis.read(byteArray)) != -1){ //in = int; byteArray = byte[]
                bos.write(byteArray, 0, i);
                bytesProcessed += i;
            }

            //Close
            bos.flush();
            bos.close();
            bis.close();
        } catch (Exception e) {
            if (!send) {
                file.delete();
            }

        }

    }

    public long getFileSize() {
        return fileSize;
    }

    public int getBytesProcessed() {
        return bytesProcessed;
    }

    public String getFilePath() {
        return filePath;
    }
}
