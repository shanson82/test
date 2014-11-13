package org.uiowa.cs2820.engine;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class FieldTest2 {

	@Test
	public void test0() {
	  System.out.println("test0");
	  Database D = new LinearFileDatabase();
	  FieldSearch F = new FieldSearch(D);
	  Field F1 = new Field("1",new Integer(45));
	  assertEquals(F.findEquals(F1).size(),0);
	  }
	
	@Test
	public void test1() {
	  System.out.println("test1");
	  Database D = new LinearFileDatabase();
	  FieldSearch F = new FieldSearch(D);
	  Indexer I = new Indexer(D,"abc");
	  Field F1 = new Field("1",new Integer(45));
	  Field F2 = new Field("part","bolt");
	  Field F3 = new Field("part","bolt");
	  I.addField(F1);
	  I.addField(F2);
	  I = new Indexer(D,"def");
	  I.addField(F3);
	  ArrayList<String> S = F.findEquals(F3);
	  assertEquals(S.size(),2);
	  assertEquals(S.get(0),"abc");
	  assertEquals(S.get(1),"def");
	  }
	
	@Test(expected = IllegalArgumentException.class)
	public void test2() {
	  System.out.println("test2");
	  Database D = new LinearFileDatabase();
	  Indexer I = new Indexer(D,"data");
	  Field F = new Field("Iowa",
		"some very long string that should not" +
	    "be allowed as part of a lookup value" + 
		"because there is a size limit in the" +
	    "code for creating a Field");
		I.addField(F); 
	  }
    }
