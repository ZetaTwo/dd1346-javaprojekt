package se.kth.f.carlcarl.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public class ProgramSettingsMdl {
	
	private int listeningPort;
	private String username;
    private String filePath;
	private final Map<String, Object> privateKey = new HashMap<>();
	
	private ProgramSettingsMdl() {
	}

	public void setListeningPort(int port) {
		listeningPort = port;
	}
	
	public void setUserName(String name) {
		username = name;
	}
	
	void setCaesarKey(int caesarKey) {
		privateKey.put("caesar", caesarKey);
	}
	
	void setRSAKey(String AESKey) {
		privateKey.put("AES", AESKey);
	}
	
	public int getListeningPort() {
		return listeningPort;
	}
	
	public String getUserName() {
		return username;
	}
	
	public int getCaesarKey() {
		return (Integer)privateKey.get("caesar");
	}
	
	public String getAESKey() {
		return (String)privateKey.get("AES");
	}
	
	public void save() throws IOException {
		
		Ini ini = new Ini(new File(filePath));
		
        ini.clear();
		ini.add("Data","Username", getUserName());
		ini.add("Data","ListeningPort", getListeningPort());
		ini.add("Data","CaesarKey", getCaesarKey());
		ini.add("Data","AESKey", getAESKey());
		ini.store();

	}
	
	public static ProgramSettingsMdl open(String path) throws IOException {
		ProgramSettingsMdl returnMdl = new ProgramSettingsMdl();
		returnMdl.filePath = path;
		File saveFile = new File(path);
        boolean fileCreated = saveFile.createNewFile();
		Ini ini = new Ini(saveFile);
		
		Preferences prefs = new IniPreferences(ini);
        Preferences data = prefs.node("Data");
		
		returnMdl.setListeningPort(data.getInt("ListeningPort", 0));
		returnMdl.setUserName(data.get("Username", ""));
		returnMdl.setCaesarKey(data.getInt("CaesarKey", 0));
		returnMdl.setRSAKey(data.get("AESKey", ""));

        if(fileCreated) {
            returnMdl.save();
        }

		return returnMdl;
	}
}
