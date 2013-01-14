package se.kth.f.carlcarl.scrapbook;

import java.io.File;

public class fileSizeTest {
	
	static File filen = new File("resources/Filöverföring (2).png");
	
	public fileSizeTest(){
	}
	
	private static String getFileSize(File file) {
		String[] suffixList = new String[]{"B", "kB", "MB", "GB", "TB"};
		long bytes = file.length();
		int numberOfItterations = 0;
		while(bytes > 1000) {
			bytes = bytes / 1000;
			numberOfItterations += 1;
		}
		return Long.toString(bytes) + " "  + suffixList[numberOfItterations];
	}
	
	public static void main(String[] args) {
		String currentWorkSpace = System.getProperty("user.dir");
		System.out.println(getFileSize(filen));
		System.out.println(currentWorkSpace);
	}

}
