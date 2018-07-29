// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar ContactTests.java
//    demo$ java  -cp .:junit-cs211.jar ContactTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar ContactTests.java
//    demo$ java  -cp .;junit-cs211.jar ContactTests

import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class ContactTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("ContactTests");
  }
  
  /////////////////////////////////////////////////////////
  
  // Contact tests.
  
  @Test (timeout=3000) public void contact_1(){
    Contact c = new Contact(new Wire("in"), new Wire("out"), true);
    assertEquals(new Wire("in"), c.getIn());
    assertEquals(new Wire("out"), c.getOut());
    assertEquals(true, c.getInbound());
  }
  
  @Test (timeout=3000) public void contact_2(){
    Contact c = new Contact(new Wire("in"), new Wire("out"), true);
    c.setIn(new Wire("in2"));
    
    assertFalse(new Wire("in").equals(c.getIn()));
    assertEquals(new Wire("in2"),c.getIn());
    
    c.setOut(new Wire("out2"));
    assertEquals(new Wire("out2"),c.getOut());
    
    c.setInbound(false);
    assertEquals(false,c.getInbound());
  }
  
  @Test (timeout=3000) public void contact_toString1(){
    assertEquals("A(B):X",new Contact(new Wire("A"), new Wire("B"),true).toString());
  }
  @Test (timeout=3000) public void contact_toString2(){
    assertEquals("(A)B:X",new Contact(new Wire("A"), new Wire("B"),false).toString());
  }
  @Test (timeout=3000) public void contact_toString3(){
    assertEquals("A:X",new Contact(new Wire("A"), new Wire("A"),true).toString());
    assertEquals("A:X",new Contact(new Wire("A"), new Wire("A"),false).toString());
  }
  @Test (timeout=3000) public void contact_toString4(){
    Wire a1 = new Wire("A");
    a1.setSignal(Signal.LO);
    Wire a2 = new Wire("A");
    a2.setSignal(Signal.LO);
    assertEquals("A:0",new Contact(a1, a2,true).toString());
  }
  @Test (timeout=3000) public void contact_toString5(){
    Wire a1 = new Wire("A");
    a1.setSignal(Signal.HI);
    Wire a2 = new Wire("A");
    a2.setSignal(Signal.HI);
    assertEquals("A:1",new Contact(a1, a2,true).toString());
  }
  
  @Test (timeout=3000) public void contact_equals(){
    Contact c1 = new Contact(new Wire("A"), new Wire("B"), true);
    Contact c2 = new Contact(new Wire("A"), new Wire("B"), true);
    Contact c3 = new Contact(new Wire("A"), new Wire("B"), false);
    Contact c4 = new Contact(new Wire("NO"), new Wire("nono!"), true);
    assertEquals(c1, c2);
    assertFalse(c1.equals(c3));
    assertFalse(c2.equals(c4));
    assertFalse(c4.equals(c1));
  }
}
