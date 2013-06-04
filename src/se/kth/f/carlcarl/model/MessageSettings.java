package se.kth.f.carlcarl.model;

import java.awt.Color;

public class MessageSettings {
	
	private boolean bold = false;
    private boolean italics = false;
	private Color color = Color.black;
	private String cryptoType = "";
    private String currentMessage = "";
	
	public MessageSettings() {
	}
	
	public void setBold(boolean bool) {
		bold = bool;
	}
	
	public void setItalics(boolean bool){
		italics = bool;
	}
	
	public void setColor(Color col) {
		color = col;
	}
	
	public void setEncryption(String string){
		cryptoType = string;
	}
	
	public void setText(String string) {
		currentMessage = string;
	}
	
	public boolean getBold() {
		return bold;
	}
	
	public boolean getItalics() {
		return italics;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getEncryption() {
		return cryptoType;
	}
	
	public String getText() {
		return currentMessage;
	}
}
