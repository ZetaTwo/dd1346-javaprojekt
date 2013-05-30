package se.kth.f.carlcarl.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
    String username;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	public void Close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public Socket getSocket() {
        return socket;
    }

	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}

    public InetAddress GetAdress() {
        return socket.getInetAddress();
    }

    public int GetPort(){
        return socket.getPort();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return getUsername();
    }
    
    public int GetLocalPort() {
    	return socket.getLocalPort();
    }
}
