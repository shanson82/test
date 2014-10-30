package org.uiowa.cs2820.engine;

import java.util.ArrayList;
import java.util.Arrays;

public class LinearMemoryDatabase implements Database {
  private ArrayList<Node> Memory = null;
  
  LinearMemoryDatabase() {
	this.Memory = new ArrayList<Node>();  // empty list
    }
  
  public Node fetch(byte[] key) {
	for (Node e: Memory)
	  if (Arrays.equals(e.Key,key)) return e;
	return null;
    }
  
  public void store(byte[] key, String id) {
	for (Node e: Memory) 
	  if (Arrays.equals(e.Key,key)) {
		e.add(id);
		return;
	    }
    Node p = new Node(key,id);
    Memory.add(p);
    }
  
  public void delete(byte[] key, String id) {
	for (Node e: Memory)
	  if (Arrays.equals(e.Key, key)) {
		e.Identifiers.remove(id);
		return;
	    }
    }
  }
	  
