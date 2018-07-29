// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar ExceptionTests.java
//    demo$ java  -cp .:junit-cs211.jar ExceptionTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar ExceptionTests.java
//    demo$ java  -cp .;junit-cs211.jar ExceptionTests


import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class ExceptionTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("ExceptionTests");
  }
  
  
  /////////////////////////////////////////////////////////
    // ExceptionLogicMalformedSignal tests.
  @Test (timeout=3000) public void malformedSignal1() {
    ExceptionLogicMalformedSignal e = new ExceptionLogicMalformedSignal('c', "I didn't like that character.");
    assertTrue('c'==e.getBad());
  }
  
  @Test (timeout=3000) public void malformedSignal2() {
    ExceptionLogicMalformedSignal e = new ExceptionLogicMalformedSignal('Z', "short");
    e.setBad('q');
    e.setMsg("NEW MSG");
    assertTrue('q'==e.getBad());
    assertEquals("NEW MSG", e.getMsg());
  }
  
  @Test (timeout=3000) public void malformedSignal3() {
    ExceptionLogicMalformedSignal e = new ExceptionLogicMalformedSignal('Z', "short");
    try {
      throw e;
    } catch (ExceptionLogicMalformedSignal ex){ }
  }
  
  /////////////////////////////////////////////////////////
  
  // ExceptionLogicParameters tests.
  @Test (timeout=3000) public void ELParams1() {
    ExceptionLogicParameters e = new ExceptionLogicParameters(true, 2, 4);
    assertEquals(true, e.getInputsRelated());
    assertEquals(2, e.getExpected());
    assertEquals(4, e.getFound());
  }
  
  @Test (timeout=3000) public void ELParams2() {
    ExceptionLogicParameters e = new ExceptionLogicParameters(true, 2, 4);
    e.setInputsRelated(false);
    e.setExpected(5);
    e.setFound(3);
    assertEquals(false, e.getInputsRelated());
    assertEquals(5, e.getExpected());
    assertEquals(3, e.getFound());
  }
  
  @Test (timeout=3000) public void ELParams3() {
    ExceptionLogicParameters e = new ExceptionLogicParameters(true, 2, 4);
    try {
      throw e;
    } catch (ExceptionLogicParameters ex) { }
  }
}

  