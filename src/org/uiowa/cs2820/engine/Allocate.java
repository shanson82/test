package org.uiowa.cs2820.engine;

import java.io.IOException;
import java.util.BitSet;

import org.uiowa.cs2820.engine.Checkpoint;

public class Allocate {
	// not quite sure about this 
	// need a file name to store the bit array
	private BitSet space = new BitSet();
	Checkpoint c = new Checkpoint((Object) space);
	
	
	// allocate finds the next available block that can be allocated for use
	public int allocate() throws IOException {
		// use Checkpoint to restore bit array from the file
		Object o = c.restore();
		space = (BitSet) o;
		int nextAvailable;
		for (nextAvailable = 0; nextAvailable <= space.length(); nextAvailable++) {
			if (space.get(nextAvailable) == false) {
				space.set(nextAvailable);
				// use Checkpoint to save bit array back to file
				c.save();
				return nextAvailable;
			}
		}
		// this should be unreachable
		c.save();
		return nextAvailable + 1;
	}

	public void free(int block) throws IOException {
		// use Checkpoint to restore bit array from the file
		Object o = c.restore();
		space = (BitSet) o;
		space.clear(block); // update bit array
		// use Checkpoint to save bit array back to file
		c.save();
		
	}

}
