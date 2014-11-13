package org.uiowa.cs2820.engine;

import java.util.ArrayList;

public class FieldSearch {
	
  private Database D;
  
  FieldSearch(Database d) {
	this.D = d;  
    }
/*
  public String[] findEquals(Field f) {
	byte[] key = f.toBytes();
	Node p = D.fetch(key);
	if (p == null) return new String[0];
	String[] R = new String[p.Identifiers.size()];
	R = p.Identifiers.toArray(R);
	return R;
    }
*/

  	public ArrayList<String> findEquals(Field f) {
  		ArrayList<String> identifiers = new ArrayList<String>();
  		byte[] key = f.toBytes();
  		KeyNode p = D.fetch(key);
  		if (p == null) return identifiers;
  		ValueStorage vs = new ValueStorage(p.getValue());
  		identifiers = vs.load();
  		return identifiers;
  	}
  }