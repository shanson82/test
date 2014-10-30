package org.uiowa.cs2820.engine;

public class FieldSearch {
	
  private Database D;
  
  FieldSearch(Database d) {
	this.D = d;  
    }
	
  public String[] findEquals(Field f) {
	byte[] key = f.toBytes();
	Node p = D.fetch(key);
	if (p == null) return new String[0];
	String[] R = new String[p.Identifiers.size()];
	R = p.Identifiers.toArray(R);
	return R;
    }
  }