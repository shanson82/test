package org.uiowa.cs2820.engine;

import java.io.IOException;
import java.io.Serializable;
import java.util.BitSet;

public class KeyNode implements Serializable {
	private byte[] key;
	private int value;
	private int next;
	
	KeyNode(byte[] key) {
		this.key = key;
		this.next = -1;
		this.value = -1; //Allocate.allocate();		
	}
	
	public byte[] getKey() {
		return this.key;
	}
	
	public int getNext() {
		return this.next;
	}
	
	public void setNext(int area) {
		this.next = area;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int area) {
		this.value = area;
	}

}
