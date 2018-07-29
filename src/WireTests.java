// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar WireTests.java
//    demo$ java  -cp .:junit-cs211.jar WireTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar WireTests.java
//    demo$ java  -cp .;junit-cs211.jar WireTests


import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class WireTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("WireTests");
  }
  
  /////////////////////////////////////////////////////////
  
  // Common definitions
  List<Wire> wires1, wires2, wires3, wires4;
  
  @Before
  public void setupWires(){
   
    wires1 = Arrays.asList(new Wire[]{new Wire("a")});
    wires2 = Arrays.asList(new Wire[]{new Wire("a"), new Wire("b")});
    wires3 = Arrays.asList(new Wire[]{new Wire("a"), new Wire("b"), new Wire("c")});
    wires4 = Arrays.asList(new Wire[]{new Wire("a"), new Wire("b"), new Wire("c"), new Wire("d")});
  }
    
  
  /////////////////////////////////////////////////////////
  
  // Wire tests
  
  @Test (timeout=3000) public void wire1(){
    Wire w1 = new Wire("boringName");
    Wire w2 = new Wire("special_name123");
    assertEquals("boringName",w1.getName());
    assertEquals("special_name123",w2.getName());
    assertEquals(Signal.X, w1.getSignal());
    assertEquals(Signal.X, w2.getSignal());
  }
  
  @Test (timeout=3000) public void wire2(){
    Wire w1 = new Wire("n");
    w1.setSignal(Signal.HI);
    assertEquals(Signal.HI, w1.getSignal());
  }
  
  @Test (timeout=3000) public void wire3(){
    Wire w1 = new Wire("old");
    w1.setName("new");
    assertEquals("new", w1.getName());
  }
  
  @Test (timeout=3000) public void wire4(){
    Wire w1 = new Wire("a");
    Wire w2 = new Wire("a");
    Wire w3 = new Wire("OTHER");
    
    assertTrue(w1.equals(w2));
    assertTrue(w2.equals(w1));
    
    assertFalse(w1.equals(w3));
    assertFalse(w2.equals(w3));
  }
  
  @Test (timeout=3000) public void wire5(){
    Wire w1 = new Wire("w");
    assertEquals("w:X",w1.toString());
  }
  
  @Test (timeout=3000) public void wire6(){
    Wire w1 = new Wire("w");
    w1.setSignal(Signal.LO);
    assertEquals("w:0",w1.toString());
    w1.setSignal(Signal.HI);
    assertEquals("w:1",w1.toString());
  }
  
  
}
