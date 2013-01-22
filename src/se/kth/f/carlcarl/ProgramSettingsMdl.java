package se.kth.f.carlcarl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public class ProgramSettingsMdl {
	
	int listeningPort;
	String username, filePath;
	Map<String, Object> privateKey = new HashMap<String, Object>();
	
	private ProgramSettingsMdl() {
	}

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
	
	public void save() throws IOException {
		
		Ini ini = new Ini(new File(filePath));
		
        ini.clear();
		ini.add("Data","Username", getUserName());
		ini.add("Data","ListeningPort", getListeningPort());
		ini.add("Data","CeasarKey", getCeasarKey());
		ini.add("Data","RSAKey", getRSAKey());
		ini.store();

	}
	
	public static ProgramSettingsMdl open(String path) throws IOException {
		ProgramSettingsMdl returnMdl = new ProgramSettingsMdl();
		returnMdl.filePath = path;
		File saveFile = new File(path);
		
		
		if(!saveFile.exists()) {
			saveFile.createNewFile();
		}
		Ini ini = new Ini(saveFile);
		
		Preferences prefs = new IniPreferences(ini);
        Preferences data = prefs.node("data");
		
		returnMdl.setListeningPort(data.getInt("ListeningPort", 0));
		returnMdl.setUserName(data.get("Username", ""));
		returnMdl.setCeasarKey(data.getInt("CeasarKey", 0));
		returnMdl.setRSAKey(data.get("RSAKey", ""));		
		
		return returnMdl;
	}
	
	/*
	 public static void main(String[] args) {
	 
		ProgramSettingsMdl model = null;
		
		try {
			model = open("filepath.ini");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.setCeasarKey(2002);
		model.setListeningPort(20030);
		model.setUserName("Carl");
		model.setRSAKey("ksanfdpijanfu9qh318412");
		try {
			model.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
}
