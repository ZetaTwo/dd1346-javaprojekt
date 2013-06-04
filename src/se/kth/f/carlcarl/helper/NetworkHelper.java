package se.kth.f.carlcarl.helper;

import java.io.IOException;
import java.net.ServerSocket;

public class NetworkHelper {
    public static ServerSocket findNextOpenPort(int startPort) throws IOException {
        while(true) {
            if(startPort > 65535) {
                throw new IOException("No port available.");
            }

            try {
                return new ServerSocket(startPort);
            } catch (Exception e) {
                startPort++;
            }
        }
    }
}
