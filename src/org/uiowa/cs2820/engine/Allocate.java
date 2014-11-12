package org.uiowa.cs2820.engine;

import java.io.IOException;
import java.util.BitSet;

public class Allocate {

	private BitSet space = new BitSet();
	Checkpoint c = new Checkpoint(this.space);
	

	Allocate(BitSet space) {
		this.space = space;
	}
	


	
	public BitSet getSpace() throws IOException {
		//Checkpoint c = new Checkpoint(this.space);
		this.space = (BitSet) c.restore();
		return this.space;
	}
	
	public void setSpace(int x) {
		this.space.set(x);
	}

	// allocate finds the next available block that can be allocated for use
	public int allocate() throws IOException {
		// use Checkpoint to restore bit array from the file
		Checkpoint c = new Checkpoint(this.space);
		this.space = (BitSet) c.restore();
		int nextAvailable;
		if (this.space == null) {
			System.out.println("returned bitset is null");
			this.space = new BitSet();
			this.space.set(0);
			c = new Checkpoint(this.space);
			c.save();
			return 0;
		} else {
			for (nextAvailable = 0; nextAvailable <= this.space.length(); nextAvailable++) {
				if (this.space.get(nextAvailable) == false) {
					this.space.set(nextAvailable);
					// use Checkpoint to save bit array back to file
					System.out.println("next space set: " + this.space);
					c = new Checkpoint(this.space);
					c.save();
					//StaticCheckpoint.save();
					return nextAvailable;
				}
			}
		}
		// this should be unreachable
		c.save();
		//return nextAvailable + 1;
		return 5;
	}

	public void free(int block) throws IOException {
		// use Checkpoint to restore bit array from the file
		Checkpoint c = new Checkpoint(this.space);
		Object o = c.restore();
		this.space = (BitSet) o;
		this.space.clear(block); // update bit array
		// use Checkpoint to save bit array back to file
		c = new Checkpoint(this.space);
		c.save();
		
	}

}
