package org.uiowa.cs2820.engine;

import java.io.File;
import java.io.IOException;

public class KeyStorage {
	private KeyNode knode;
	
	KeyStorage(KeyNode knode) {
		this.knode = knode;
	}

	public void add(int area) {
		byte[] key = Utility.convert(this.knode);
		File diskMem = new File("diskSpace.txt");
		DiskSpace G = new DiskSpace(diskMem);
		try {
			G.writeArea(area, key);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
}
