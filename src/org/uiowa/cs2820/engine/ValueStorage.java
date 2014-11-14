package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ValueStorage {
	
	private IDNode idnode; // identifier node
	private int areaToWrite; // this is non-negative if identifier is the first written to a field
	private int areaToSearch; // this is non-negative if not the first identifier to be written to a field
	
	// constructor to use if storing an identifier
	ValueStorage(String id, int areaToSearch) {
		System.out.println(id);
		byte[] idArray = Utility.convert(id);
		this.idnode = new IDNode(idArray);
		this.areaToSearch = areaToSearch;
		// can add a check to make sure idArray < 1024 bytes
		try {
			this.areaToWrite = Allocate.allocate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// constructor to use if searching for identifiers (see FieldSearch)
	ValueStorage(int areaToSearch) {
		this.idnode = null;
		this.areaToWrite = -1;
		this.areaToSearch = areaToSearch;
	}
	
	public int getArea() {
		return this.areaToWrite;
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
				System.out.println(searchArea);
				byte[] i = G.readArea(searchArea);
				System.out.println(i);
				IDNode current = (IDNode) Utility.revert(i);
				System.out.println(current.getNext());
				// convert identifier from byte[] to String
				String id = (String) Utility.revert(current.getid());
				System.out.println(id);
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

	public void store() {
		byte[] identifier = Utility.convert(this.idnode); // identifier node to write to file
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			if (this.areaToSearch == -1) {
				G.writeArea(this.areaToWrite, Utility.convert(this.idnode));
				return;
			}
			int currentArea = this.areaToSearch;
			while (true) {
				byte[] currentArray = G.readArea(currentArea);
				IDNode current = (IDNode) Utility.revert(currentArray);
				if (current.getNext() == -1) {
					G.writeArea(this.areaToWrite, Utility.convert(this.idnode));
					current.setNext(this.areaToWrite);
					G.writeArea(currentArea, Utility.convert(current));
					return;
				}
				currentArea = current.getNext();
			}
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
	}
	
	
}

/*
public void store() {
	byte[] identifier = Utility.convert(this.idnode); // identifier node to write to file
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
					G.writeArea(writeArea, Utility.convert(this.idnode));
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
}*/
