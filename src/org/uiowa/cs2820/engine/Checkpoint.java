package org.uiowa.cs2820.engine;

import java.io.*;
import java.lang.String;

public class Checkpoint {
	
	private Object o;
	private final String filename = "memoryMap.txt";

	
	//parse in the object only
	public Checkpoint(Object o) {//check to make sure if file exist
		this.o = o;
		File file = new File(filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void save() throws IOException{
		FileOutputStream f = new FileOutputStream(filename);
		byte[] bArray = Utility.convert(o);
		int length = bArray.length;
		// next encode length into 2 bytes
		byte [] extra = new byte[2];
		extra[0] = (byte) (length / 256);
		extra[1] = (byte) length;
		// first write the length
		f.write(extra);
		// then write normal bytes
		f.write(bArray);
		f.close();
		return;
	}
	
	public Object restore() throws IOException{
		FileInputStream f = new FileInputStream(filename);
		byte [] extra = new byte[2];
		f.read(extra);
		int length = (int)extra[0];
		if (length < 0) length += 256;
		length = length * 256;
		int length2 = (int)extra[1];
		if (length2 < 0) length2 += 256;
		length = length + length2;
		byte [] bArray = new byte[length];
		f.read(bArray);
		Object obj = Utility.revert(bArray);
		f.close();
		return obj;
	}
}
