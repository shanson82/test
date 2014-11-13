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
		}
		return null;
	}
	
	public void store(byte[] key, String id) {
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			byte[] n = G.readArea(0); // read the first block of the file
			while (true) {
				KeyNode k = (KeyNode) Utility.revert(n); // revert back to KeyNode 
				// either no other keys in file or no matches and now at the end of the list
				if (k.getNext() == -1) {
					int areaToWrite = Allocate.allocate();
					k.setNext(areaToWrite);
					// insert new KeyNode
					KeyStorage ks = new KeyStorage(new KeyNode(key));
					ks.add(areaToWrite); // write new KeyNode to area
					ValueStorage vs = new ValueStorage(id, areaToWrite);
					vs.store();
					return;
				}
				// key stored in node matches the key to be stored
				if (k.getKey() == key) {
					int areaToSearch = k.getValue();
					ValueStorage vs = new ValueStorage(id, -1, areaToSearch);
					vs.store();
					return;
				}
				n = G.readArea(k.getNext());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public void delete(byte[] key, String id) {
		return;
	}
}
