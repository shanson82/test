package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.IOException;


public class LinearFileDatabase implements Database {

	public KeyNode fetch(byte[] key) {
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			byte[] n = G.readArea(0); // start from beginning of file
			KeyNode k = (KeyNode) Utility.revert(n);
			if (k.getNext() == -1) 
				return null; // indicates no keys in file
			while (true) {
				if (k.getKey() == key)
					return k;
				n = G.readArea(k.getNext());
				k = (KeyNode) Utility.revert(n);
				if (k.getNext() == -1) return null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} // start from beginning of file
		return null;
	}
	
	public void store(byte[] key, String id) {
		return;
	}
	
	public void delete(byte[] key, String id) {
		return;
	}
}
