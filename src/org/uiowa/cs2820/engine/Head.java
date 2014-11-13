package org.uiowa.cs2820.engine;

import java.io.Serializable;

public class Head implements Serializable {
	
	private int area;
	private int next;
	
	Head(int area) {
		this.area = area;
		this.next = -1;
	}
	
	public int getArea() {
		return this.area;
	}
	
	public int getNext() {
		return this.next;
	}
	
	public void setNext(int area) {
		this.next = area;
	}
}
