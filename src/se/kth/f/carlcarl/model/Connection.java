package se.kth.f.carlcarl.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	public void Update() {
		while(true) {
			
		}
	}
	
	public void Close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public BufferedReader getIn() {
		return in;
	}
}
