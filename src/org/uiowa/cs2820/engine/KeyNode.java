package org.uiowa.cs2820.engine;

import java.io.IOException;
import java.io.Serializable;
import java.util.BitSet;

public class KeyNode implements Serializable {
	private byte[] key;
	private int value;
	private int next;
	
	KeyNode(byte[] key) {
		Allocate a = new Allocate(new BitSet());
		this.key = key;
		try {
			this.next = a.allocate();
			this.value = a.allocate();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public byte[] getKey() {
		return this.key;
	}
	
	public int getNext() {
		return this.next;
	}

}
