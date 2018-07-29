// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar SignalTests.java
//    demo$ java  -cp .:junit-cs211.jar SignalTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar SignalTests.java
//    demo$ java  -cp .;junit-cs211.jar SignalTests

import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class SignalTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("SignalTests");
  }
  

  /////////////////////////////////////////////////////////
  
  // Commonly (re)used definitions.
  
  List<Signal> sigs00, sigs01, sigs10, sigs11, sigs0X, sigsX0, sigs1X, sigsX1, sigsXX, sigs0, sigs1, sigsX;
  
  @Before
  public void setupSignals(){
    sigs00 = Arrays.asList(new Signal[]{Signal.LO, Signal.LO});
    sigs01 = Arrays.asList(new Signal[]{Signal.LO, Signal.HI});
    sigs10 = Arrays.asList(new Signal[]{Signal.HI, Signal.LO});
    sigs11 = Arrays.asList(new Signal[]{Signal.HI, Signal.HI});
    sigs0X = Arrays.asList(new Signal[]{Signal.LO, Signal.X });
    sigsX0 = Arrays.asList(new Signal[]{Signal.X , Signal.LO});
    sigs1X = Arrays.asList(new Signal[]{Signal.HI, Signal.X });
    sigsX1 = Arrays.asList(new Signal[]{Signal.X , Signal.HI});
    sigsXX = Arrays.asList(new Signal[]{Signal.X , Signal.X });
    
    sigs0 = Arrays.asList(new Signal[]{Signal.LO });
    sigs1 = Arrays.asList(new Signal[]{Signal.HI });
    sigsX = Arrays.asList(new Signal[]{Signal.X  });
    
  }
  
  /////////////////////////////////////////////////////////
  
  // Signal tests
  
  @Test (timeout=3000) public void signal1(){
    assertEquals(Signal.HI, Signal.HI);
    assertEquals(Signal.LO, Signal.LO);
    assertEquals(Signal.X,  Signal.X );
  }
  
  @Test (timeout=3000) public void signal2(){ assertEquals("1",Signal.HI.toString()); }
  @Test (timeout=3000) public void signal3(){ assertEquals("0",Signal.LO.toString()); }
  @Test (timeout=3000) public void signal4(){ assertEquals("X",Signal.X .toString()); }
  
  @Test (timeout=3000) public void signal5(){ assertEquals(Signal.HI,Signal.fromString('1')); }
  @Test (timeout=3000) public void signal6(){ assertEquals(Signal.LO,Signal.fromString('0')); }
  @Test (timeout=3000) public void signal7(){ assertEquals(Signal.X ,Signal.fromString('X')); }
  @Test (timeout=3000) public void signal8(){ assertEquals(Signal.X ,Signal.fromString('x')); }
  @Test (timeout=3000) public void signal9(){
    try {
      char c = ' ';
      Signal s = Signal.fromString(c);
      fail(String.format("shouldn't have gotten back Signal %s from char'%s'.",s,c));
    }
    catch (ExceptionLogicMalformedSignal e) { return; }
  }
  @Test (timeout=3000) public void signal10(){
    try {
      char c = 'h';
      Signal s = Signal.fromString(c);
      fail(String.format("shouldn't have gotten back Signal %s from char'%s'.",s,c));
    }
    catch (ExceptionLogicMalformedSignal e) { return; }
  }
  
  @Test (timeout=3000) public void signal11(){
    String inp = "110X";
    List<Signal> actuals = Signal.fromString(inp);
    List<Signal> expecteds = Arrays.asList(new Signal[]{Signal.HI, Signal.HI, Signal.LO, Signal.X});
    assertEquals(expecteds, actuals);
  }
  
  
  @Test (timeout=3000) public void signal12(){
    String inp = "";
    List<Signal> expecteds = Arrays.asList(new Signal[]{});
    List<Signal> actuals = Signal.fromString(inp);
    assertEquals(expecteds, actuals);
  }
  
  // this one is still okay - valid symbols and ignorable whitespace.
  @Test (timeout=3000) public void signal13(){
    String inp = "1 x \tX 00";
    List<Signal> expecteds = Arrays.asList(new Signal[]{Signal.HI, Signal.X, Signal.X, Signal.LO, Signal.LO});
    List<Signal> actuals = Signal.fromString(inp);
    assertEquals(expecteds, actuals);
  }
  
  @Test (timeout=3000) public void signal14(){
    try {
      String inp = "1 x \tX BAD characters ! 00";
      List<Signal> expecteds = Signal.fromString(inp);
      fail("shouldn't have succeeded in reading past any bad characters.");
    }
    catch (ExceptionLogicMalformedSignal e){ return; }
  }
  
  @Test (timeout=3000) public void signal15(){
    List<Signal> originals = sigs0;
    assertEquals("0", Signal.toString(originals));
  }
  
  @Test (timeout=3000) public void signal16(){
    List<Signal> originals = Arrays.asList(new Signal[]{Signal.LO, Signal.HI, Signal.X, Signal.HI});
    assertEquals("01X1", Signal.toString(originals));
  }
  @Test (timeout=3000) public void signal17(){
    List<Signal> originals = Arrays.asList(new Signal[]{Signal.LO, Signal.HI, Signal.X, Signal.HI, Signal.LO, Signal.HI, Signal.X, Signal.HI});
    assertEquals("01X101X1", Signal.toString(originals));
  }
}

