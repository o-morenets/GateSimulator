// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar FeedbackCircuitTests.java
//    demo$ java  -cp .:junit-cs211.jar FeedbackCircuitTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar FeedbackCircuitTests.java
//    demo$ java  -cp .;junit-cs211.jar FeedbackCircuitTests

import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class FeedbackCircuitTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("FeedbackCircuitTests");
  }
  
  //==========================================================================================
  
  public static void test_feedback_circuit(FeedbackCircuit c, String inputs, String expectedOutputs){
    List<Signal> sigIns = Signal.fromString(inputs);
    List<Signal> sigOuts = c.inspect(sigIns);
    String actualOuts = Signal.toString(sigOuts);
    assertEquals(expectedOutputs, actualOuts);
  }
  public static void test_feedback_circuit(String filename, String inputs, String expectedOutputs){
    try {
      test_feedback_circuit(new FeedbackCircuit(filename), inputs, expectedOutputs);
    } catch (IOException e) { fail("IOException when opening "+filename+" to read a Circuit." + e); }
  }
  
  
  // this is a "not(SR) NAND" latch circuit.
  // http://en.wikipedia.org/wiki/Flip-flop_(electronics)#SR_NAND_latch
  @Test (timeout=3000) public void file_fb_nSnR_nand() {
    test_feedback_circuit("fb_nSnR_nand", "00", "11");// not allowed in regular circuits (they don't like Q and nQ both being 1).
    test_feedback_circuit("fb_nSnR_nand", "01", "10");
    test_feedback_circuit("fb_nSnR_nand", "10", "01");
    test_feedback_circuit("fb_nSnR_nand", "11", "XX");
  }
  @Test (timeout=3000) public void file_fb_nSnR_nand_Xs() {
    test_feedback_circuit("fb_nSnR_nand", "0X", "1X");
    test_feedback_circuit("fb_nSnR_nand", "1X", "XX");
    test_feedback_circuit("fb_nSnR_nand", "X0", "X1");
    test_feedback_circuit("fb_nSnR_nand", "X1", "XX");
    test_feedback_circuit("fb_nSnR_nand", "XX", "XX");
  }
  
  // this is an "SR NOR" latch circuit.
  // It has a "Set" and "Reset" wire. A high reset means reset Q=0 (and thus nQ=1).
  // While reset stays low, if we put "set" to high, it sets Q=1 (and nQ=0). 
  // returning "set" to low, Q stays at its previous value (Q=1 in the current discussion).
  // not until we put "reset" high again will Q=0.
  //
  // http://en.wikipedia.org/wiki/Flip-flop_(electronics)#SR_NOR_latch
  
  @Test (timeout=3000) public void file_fb_SR_nor_v1() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01",Signal.toString(fc.read()));
  }
  
  @Test (timeout=3000) public void file_fb_SR_nor_v2() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01",Signal.toString(fc.read()));
    
    // feed S=0, R=0.
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    
    // should still have Q=0 (and nQ=1).
    assertEquals("01",Signal.toString(fc.read()));
  }
  
  @Test (timeout=3000) public void file_fb_SR_nor_v3() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("fb_SR_nor");
    
    // reset the Q value.
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    // should have Q=0 (and nQ=1).
    assertEquals("01",Signal.toString(fc.read()));
    
    // feed S=0, R=0.
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    
    // should still have Q=0 (and nQ=1).
    assertEquals("01",Signal.toString(fc.read()));
    
    // feed S=1, R=0.
    fc.feed(Signal.fromString("10"));
    fc.propagate();
    assertEquals("10",Signal.toString(fc.read()));
    
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    assertEquals("10",Signal.toString(fc.read()));
    
    fc.feed(Signal.fromString("01"));
    fc.propagate();
    assertEquals("01",Signal.toString(fc.read()));
    
    fc.feed(Signal.fromString("00"));
    fc.propagate();
    assertEquals("01",Signal.toString(fc.read()));
  }
  
  @Test (timeout=3000) public void file_memory1_1() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("1",fc.inspect("11"));
    assertEquals("1",fc.inspect("10"));   // this time, "10" -> "1".
  }
  @Test (timeout=3000) public void file_memory1_2() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("0",fc.inspect("00"));
    assertEquals("0",fc.inspect("10"));   // this time, "10" -> "0".
  }
  @Test (timeout=3000) public void file_memory1_3() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("memory1");
    assertEquals("0",fc.inspect("00"));
    assertEquals("0",fc.inspect("10"));
    assertEquals("1",fc.inspect("11"));
    assertEquals("0",fc.inspect("01"));
    assertEquals("0",fc.inspect("00"));
    assertEquals("1",fc.inspect("11"));
    assertEquals("0",fc.inspect("01"));
  }
  
  @Test (timeout=3000) public void file_memory2_1() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("memory2");
    assertEquals("1",fc.inspect("11"));
    assertEquals("1",fc.inspect("01"));
  }
  @Test (timeout=3000) public void file_memory2_2() throws IOException {
    FeedbackCircuit fc = new FeedbackCircuit("memory2");
    assertEquals("0",fc.inspect("00"));
    assertEquals("0",fc.inspect("01"));
  }
}
