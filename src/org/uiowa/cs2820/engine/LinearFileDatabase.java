package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class LinearFileDatabase implements Database {

	LinearFileDatabase() {
		File diskMem = new File("diskSpace.txt");
		if (diskMem.length() == 0) {
			try {
				System.out.println("diskSpace is empty");
				RandomAccessFile disk = new RandomAccessFile(diskMem, "rw");
				int writeArea = Allocate.allocate();
				Head h = new Head(writeArea);				
				disk.write(Utility.convert(h));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public KeyNode fetch(byte[] key) {
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			byte[] n = G.readArea(0); // start from beginning of file
			//KeyNode k = (KeyNode) Utility.revert(n);
			Head h = (Head) Utility.revert(n);
			if (h.getNext() == -1) {
				System.out.println("LinearFileDatabase.fetch - head points to nothing");
				return null; // indicates no keys in file
			}
			int areaToSearch = h.getNext();
			while (true) {
				n = G.readArea(areaToSearch);
				KeyNode k = (KeyNode) Utility.revert(n);
				if (k.getKey() == key)
					return k;
				if (k.getNext() == -1) return null;
				areaToSearch = k.getNext();
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
			Head h = (Head) Utility.revert(n);
			System.out.println("head points to: " + h.getNext());
			if (h.getNext() == -1) {
				int areaToWrite = Allocate.allocate();
				h.setNext(areaToWrite);
				KeyStorage ks = new KeyStorage(new KeyNode(key));
				ks.add(areaToWrite);
				ValueStorage vs = new ValueStorage(id, areaToWrite);
				vs.store();
				G.writeArea(0, Utility.convert(h));
				return;
			}
			int nextArea = h.getNext();
			while (true) {
				n = G.readArea(nextArea);
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
				//n = G.readArea(k.getNext());
				nextArea = k.getNext();
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
