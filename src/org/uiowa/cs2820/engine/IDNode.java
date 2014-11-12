package org.uiowa.cs2820.engine;

import java.io.Serializable;

public class IDNode implements Serializable {
	
	private byte[] id;
	private int next;
	private int size;
	
	IDNode(byte[] id) {
		this.id = id;
		this.size = id.length;
		this.next = -1;
	}

}
