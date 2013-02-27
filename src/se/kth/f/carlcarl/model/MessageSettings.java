package se.kth.f.carlcarl.model;

import java.awt.Color;

public class MessageSettings {
	
	boolean bold, italics;
	Color color;
	String cryptoType, currentMessage;
	
	public MessageSettings(boolean boldBool, boolean italicsBool, Color colorColor, String cryptoString, String currentMessageString) {
		bold = boldBool;
		italics = italicsBool;
		color = colorColor;
		cryptoType = cryptoString;
		currentMessage = currentMessageString;
	}
	
	public MessageSettings() {
		this(false, false, Color.black, "", "");
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
