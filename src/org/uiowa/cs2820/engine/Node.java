package org.uiowa.cs2820.engine;

import java.util.ArrayList;

public class Node {
  // Node is a basic unit in the database
  byte[] Key;  // Key of this node for lookup
  ArrayList<String> Identifiers;
  int size;    // not yet implemented
  
  Node(byte[] f, String id) {
	this.Key = f;
	this.Identifiers = new ArrayList<String>();
	this.Identifiers.add(id);
    }
  
  public void add(String id) { 
	Identifiers.remove(id);  // does nothing if id not already there
	Identifiers.add(id);
    }
  
  public void del(String id) {
	Identifiers.remove(id);
    }
  
  public int getSize() {
	return size;
    }
  }
