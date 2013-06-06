package se.kth.f.carlcarl.model;

public class PendingFileTransfer extends Thread {
    ChatMdl owner;
    boolean responderReceived = false;
    boolean timedOut = false;
    String receiver = "";
    String filePath = "";
    Connection connection;

    public PendingFileTransfer(ChatMdl owner, String filePath, Connection connection) {
        this.owner = owner;
        this.connection = connection;
        start();
    }

    public void run() {
        try {
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!responderReceived) {
            owner.ParseFileResponse(connection, false, "svarade inte i tid.", 0, connection.getUsername(), );
            timedOut = true;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void responseReceived() {
        responderReceived = true;
    }

    public boolean isTimedOut() {
        return timedOut;
    }
}

