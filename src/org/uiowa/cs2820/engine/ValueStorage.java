package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ValueStorage {
	
	private IDNode inode; // identifier node
//	private byte[] key; // key in byte[] format
	private int areaToWrite; // this is non-negative if identifier is the first written to a field
	private int areaToSearch; // this is non-negative if not the first identifier to be written to a field
	
	// constructor to use if the first identifier to be written to a field
	ValueStorage(String id, int areaToWrite) {
		byte[] idArray = Utility.convert(id);
		this.inode = new IDNode(idArray);
//		this.key = null;
		try {
			this.areaToWrite = Allocate.allocate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.areaToSearch = -1;
	}
	
	// constructor to use if not the first identifier to be written to a field
	ValueStorage(String id, int areaToWrite, int areaToSearch) {
		byte[] idArray = Utility.convert(id);
		this.inode = new IDNode(idArray);
//		this.key = null;
		this.areaToWrite = -1;
		this.areaToSearch = areaToSearch;
	}

	// constructor to use if searching for identifiers (see FieldSearch)
	ValueStorage(int areaToSearch) {
		this.inode = null;
//		this.key = null;
		this.areaToWrite = -1;
		this.areaToSearch = areaToSearch;
	}

	public void store() {
		byte[] identifier = Utility.convert(this.inode); // identifier node to write to file
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			System.out.println("Value storage store() area to write: " + this.areaToWrite);
			// the area in the file to write the identifier is known
			if (this.areaToWrite != -1) {
				G.writeArea(this.areaToWrite, identifier);
				return;
			} else { // now have to search for place to insert identifier - traverse to the end of the list
				int searchArea = this.areaToSearch;
				while (true) {
					byte[] i = G.readArea(searchArea); 
					IDNode current = (IDNode) Utility.revert(i);
					// if getNext == -1, then we are at the end of the list - insert the identifier
					if (current.getNext() == -1) {
						// find area to write the identifier
						int writeArea = Allocate.allocate();
						// create link/pointer from old end of list to new identifier
						current.setNext(writeArea);
						// write current back to file
						G.writeArea(searchArea, Utility.convert(current));
						// write new identifier to file
						G.writeArea(writeArea, Utility.convert(this.inode));
						return;
					}
					// increment to area where next identifier is stored
					searchArea = current.getNext();
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public ArrayList<String> load() {
		ArrayList<String> identifiers = new ArrayList<String>();
		if (this.areaToSearch == -1) {
			System.out.println("ValueStorage load - nothing to return");
			return identifiers;
		}
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		int searchArea = this.areaToSearch;
		while (true) {
			try {
				byte[] i = G.readArea(searchArea);
				IDNode current = (IDNode) Utility.revert(i);
				// convert identifier from byte[] to String
				String id = (String) Utility.revert(current.getid());
				identifiers.add(id);
				// if next value == -1, then at end of list of identifiers, return the ArrayList
				if (current.getNext() == -1) return identifiers;
				// increment to next area to search
				searchArea = current.getNext();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	
		
	}

}

