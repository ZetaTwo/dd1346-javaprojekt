package se.kth.f.carlcarl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SettingsMdl {
	
	int listeningPort;
	String username;
	Map<String, Object> privateKey;
	
	SettingsMdl() {
		privateKey = new HashMap<String, Object>();	
	}
	
	/*SettingsMdl(int port, String name, int ceasarKey,String RSAKey) {
		listeningPort = port;
		username = name;
		privateKey = new HashMap<String, Object>();
		privateKey.put("ceasar", ceasarKey);
		privateKey.put("RSA", RSAKey);
	}
	*/
	public void setListeningPort(int port) {
		listeningPort = port;
	}
	
	public void setUserName(String name) {
		username = name;
	}
	
	public void setCeasarKey(int ceasarKey) {
		privateKey.put("ceasar", ceasarKey);
	}
	
	public void setRSAKey(String RSAKey) {
		privateKey.put("RSA", RSAKey);
	}
	
	public int getListeningPort() {
		return listeningPort;
	}
	
	public String getUserName() {
		return username;
	}
	
	public int getCeasarKey() {
		return (int)privateKey.get("ceasar");
	}
	
	public String getRSAKey() {
		return (String)privateKey.get("RSA");
	}
	
	public void save() {
		try {
			
			File saveFile = new File("SaveFile.txt");
			if(!saveFile.exists()){
				saveFile.createNewFile();
			}
			FileWriter fwriter = new FileWriter(saveFile.getAbsoluteFile());
			BufferedWriter output = new BufferedWriter(fwriter);
			output.write(getListeningPort()+",");
			output.write(getUserName() + ",");
			output.write(getCeasarKey() + ",");
			output.write(getRSAKey());
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void open() {
		try {
		File saveFile = new File("SaveFile.txt");
		if(!saveFile.exists()){
			saveFile.createNewFile();
			// Can't read from an empty file.
			return;
			}
		Scanner scanner = new Scanner(saveFile);
		scanner.useDelimiter(",");
		setListeningPort(Integer.parseInt(scanner.next()));
		setUserName(scanner.next());
		setCeasarKey(Integer.parseInt(scanner.next()));
		setRSAKey(scanner.next());
		scanner.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
