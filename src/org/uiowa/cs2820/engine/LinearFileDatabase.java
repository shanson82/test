package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;


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
			System.out.println("Found head, next area is: " + areaToSearch);
			while (true) {
				n = G.readArea(areaToSearch);
				KeyNode k = (KeyNode) Utility.revert(n);
				Field f1 = (Field) Utility.revert(k.getKey());
				System.out.println("Field: " + f1.getFieldName() + " " + f1.getFieldValue());
				System.out.println("Value area and next area: " + k.getValue() + " " + k.getNext());
				if (Arrays.equals(k.getKey(), key)) {
					System.out.println("Fetch - keys are equal");
					return k;
				}
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
				int areaToWriteKey = Allocate.allocate();
				h.setNext(areaToWriteKey);
				G.writeArea(0, Utility.convert(h));
				KeyStorage ks = new KeyStorage(key);
				ValueStorage vs = new ValueStorage(id, -1);
				ks.getNode().setValue(vs.getArea()); // set pointer of KeyNode to point to IDNode
				ks.putNode(areaToWriteKey);
				vs.store();		
				return;
			}
			int currentArea = h.getNext();
			while (true) {
				n = G.readArea(currentArea);		
				KeyNode k = (KeyNode) Utility.revert(n); // revert back to KeyNode 
				System.out.println("area reading: " + currentArea);
				System.out.println("KeyNode points to next and value: " + k.getNext() + " " + k.getValue());
				
				// key stored in node matches the key to be stored
				if (Arrays.equals(k.getKey(),  key)) {
					System.out.println("Keys are equal");
					int areaToSearch = k.getValue();
					ValueStorage vs = new ValueStorage(id, areaToSearch);
					vs.store();
					return;
				}			
				// either no other keys in file or no matches and now at the end of the list
				if (k.getNext() == -1) {
					int areaToWriteKey = Allocate.allocate();
					KeyStorage ks = new KeyStorage(key);
					ValueStorage vs = new ValueStorage(id, -1);
					ks.getNode().setValue(vs.getArea());
					ks.putNode(areaToWriteKey);
					vs.store();
					k.setNext(areaToWriteKey);
					G.writeArea(currentArea, Utility.convert(k));
					return;
				
				/*	int areaToWrite = Allocate.allocate();
					k.setNext(areaToWrite);
					G.writeArea(nextArea, Utility.convert(k));
					// insert new KeyNode
					KeyStorage ks = new KeyStorage(new KeyNode(key));
					ks.add(areaToWrite); // write new KeyNode to area
					ValueStorage vs = new ValueStorage(id, areaToWrite);
					vs.store();
					return; */
				}

				//n = G.readArea(k.getNext());
				currentArea = k.getNext();
				System.out.println("area to read next: " + currentArea);
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
