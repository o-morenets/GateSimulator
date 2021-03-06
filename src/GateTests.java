// to compile/run just this batch of tests:
//
// Mac: compile/run:
//    demo$ javac -cp .:junit-cs211.jar GateTests.java
//    demo$ java  -cp .:junit-cs211.jar GateTests
//
// PC: compile/run:
//    demo$ javac -cp .;junit-cs211.jar GateTests.java
//    demo$ java  -cp .;junit-cs211.jar GateTests

import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class GateTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("GateTests");
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
  
  // Gate Tests

  // Gate is abstract; make an arbitrary child class and test inherited methods that way.
  
  class GateThing extends Gate {
    public GateThing(List<Wire> i, Wire o){ super("gtname", i, o); }
    @Override public boolean propagate(){ throw new RuntimeException("don't call propagate on me."); }
    // just in case inspect isn't available in your Gate implementation...
    @Override public List<Signal> inspect(List<Signal> s) { throw new RuntimeException("I'm just a Gate instance for testing non-inspect methods, please don't call inspect on me!");}
    @Override public String inspect(String s) { throw new RuntimeException("I'm just a Gate instance for testing non-inspect methods, please don't call inspect on me!");}
  };
  
  // Gate tests.
  
  @Test (timeout=3000) public void gate_getInputs(){
    GateThing g = new GateThing(wires2,new Wire("out"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
  }
  
  @Test (timeout=3000) public void gate_getOutput(){
    GateThing g = new GateThing(wires2,new Wire("out"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(new Wire("out"), g.getOutput());
  }
  
  @Test (timeout=3000) public void gate_setInputs(){
    GateThing g = new GateThing(wires2,new Wire("out"));
    g.setInputs(wires3);
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b"), new Wire("c")});
    assertEquals(expected, g.getInputs());
  }
  
  @Test (timeout=3000) public void gate_setOutput(){
    GateThing g = new GateThing(wires2,new Wire("out"));
    g.setOutput(new Wire("newout"));
    assertEquals(new Wire("newout"), g.getOutput());
  }
  
  @Test (timeout=3000) public void gate_name1(){
    GateThing g = new GateThing(wires2, new Wire("out"));
    assertEquals("gtname", g.getName());
  }
  @Test (timeout=3000) public void gate_name2(){
    GateThing g = new GateThing(wires2, new Wire("out"));
    g.setName("newname");
    assertEquals("newname", g.getName());
  }
  
  // check that feeding a List<Signal> updates the input wires' signals.
  @Test (timeout=3000) public void gate_feed1(){
    GateThing g = new GateThing(wires2, new Wire("out"));
    g.feed(sigs01);
    assertEquals(Signal.LO, g.getInputs().get(0).getSignal());
    assertEquals(Signal.HI, g.getInputs().get(1).getSignal());
  }
  
  // given too few parameters, should throw exception.
  @Test (timeout=3000) public void gate_feed2(){
    GateThing g = new GateThing(wires4, new Wire("out"));
    try {
      g.feed(sigs01);
      fail("should have thrown exception, given wrong number of parameters.");
    } catch (ExceptionLogicParameters e){
      assertEquals(4,e.getExpected());
      assertEquals(2,e.getFound());
    }
  }
  // given too few parameters, should throw exception.
  @Test (timeout=3000) public void gate_feed3(){
    GateThing g = new GateThing(wires4, new Wire("out"));
    try {
      g.feed("01");
      fail("should have thrown exception, given wrong number of parameters.");
    } catch (ExceptionLogicParameters e){
      assertEquals(4,e.getExpected());
      assertEquals(2,e.getFound());
    }
  }
  // given too few parameters, constructor should throw exception.
  @Test (timeout=3000) public void gate_feed4(){
    try {
      GateThing g = new GateThing(new ArrayList<Wire>(), new Wire("out"));
      fail("should have thrown exception, given wrong number of parameters.");
    } catch (ExceptionLogicParameters e){ }
  }
  
  @Test (timeout=3000) public void gate_feed5(){
    GateThing g = new GateThing(wires4, new Wire("out"));
    g.feed("0X11");
    assertEquals(Signal.LO, g.getInputs().get(0).getSignal());
    assertEquals(Signal.X , g.getInputs().get(1).getSignal());
    assertEquals(Signal.HI, g.getInputs().get(2).getSignal());
    assertEquals(Signal.HI, g.getInputs().get(3).getSignal());
  }
  
  @Test (timeout=3000) public void gate_read1(){
    GateThing g = new GateThing(wires4, new Wire("out"));
    assertEquals(sigsX,g.read());
  }
  @Test (timeout=3000) public void gate_read2(){
    GateThing g = new GateThing(wires4, new Wire("out"));
    Wire w = new Wire("out");
    w.setSignal(Signal.HI);
    g.setOutput(w);
    assertEquals(sigs1,g.read());
  }
  
  @Test (timeout=3000) public void gate_toString1(){
    GateThing g = new GateThing(wires3, new Wire("out"));
    String expected = "gtname( [a:X, b:X, c:X] | out:X )";
    assertEquals(expected, g.toString());
  }
  
  // after some modifications to the gate, do we still get the right string?
  // relies upon Wire::setSignal(Signal), Gate::setOutput(Wire), Gate::setName(String), and Gate::feed(String).
  @Test (timeout=3000) public void gate_toString2(){
    GateThing g = new GateThing(wires2, new Wire("out"));
    g.setName("NAME");
    Wire w = new Wire("w");
    w.setSignal(Signal.HI);
    g.setOutput(w);
    g.feed("01");
    String expected = "NAME( [a:0, b:1] | w:1 )";
    assertEquals(expected, g.toString());
  }
  
  // equals.should be comparing the names, inputs/output wires (via wires' equals() method).
  @Test (timeout=3000) public void gate_equals(){
    GateThing g1 = new GateThing(wires2, new Wire("out"));
    List<Wire> ws2 = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    GateThing g2 = new GateThing(ws2, new Wire("out"));
    assertEquals(g1,g2);
  }
  
  /////////////////////////////////////////////////////////
  
  // GateAnd tests.
  
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gateand1(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    expected.get(1).setSignal(Signal.HI);
    
    g.feed("01");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gateand_00(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("00");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gateand_01(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("01");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gateand_10(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("10");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  @Test (timeout=3000) public void gateand_11(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("11");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateand_X0(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("X0");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }    
  
  @Test (timeout=3000) public void gateand_1X(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("1X");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gateand_XX(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    g.feed("XX");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  
  @Test (timeout=3000) public void gateand_111(){
    GateAnd g = new GateAnd(wires3, new Wire("outa"));
    g.feed("111");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateand_1101(){
    GateAnd g = new GateAnd(wires4, new Wire("outa"));
    g.feed("1101");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gateand_propagate_results(){
    GateAnd g = new GateAnd(wires2, new Wire("outa"));
    // output : X -> LO
    g.feed("00");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: LO -> LO
    ans = g.propagate();
    assertFalse(ans);
    //output: LO -> HI
    g.feed("11");
    ans = g.propagate();
    assertTrue(ans);
  }
  
  /////////////////////////////////////////////////////////
  
  // GateOr Tests
  
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gateor1(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    expected.get(1).setSignal(Signal.HI);
    
    g.feed("01");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gateor_00(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("00");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gateor_01(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("01");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateor_10(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("10");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  @Test (timeout=3000) public void gateor_11(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("11");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateor_X0(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("X0");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gateor_1X(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("1X");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }    
  
  @Test (timeout=3000) public void gateor_XX(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    g.feed("XX");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  @Test (timeout=3000) public void gateor_111(){
    GateOr g = new GateOr (wires3, new Wire("outa"));
    g.feed("111");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateor_0010(){
    GateOr g = new GateOr (wires4, new Wire("outa"));
    g.feed("0010");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gateor_propagate_results(){
    GateOr g = new GateOr (wires2, new Wire("outa"));
    // output : X -> LO
    g.feed("00");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: LO -> LO
    ans = g.propagate();
    assertFalse(ans);
    //output: LO -> HI
    g.feed("11");
    ans = g.propagate();
    assertTrue(ans);
  }
  
  /////////////////////////////////////////////////////////
  
  // GateNot Tests
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gatexor_1(){
    GateNot g = new GateNot (new Wire("inw"), new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("inw")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    
    g.feed("0");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gatenot_0(){
    GateNot g = new GateNot (new Wire("inw"), new Wire("outa"));
    g.feed("0");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenot_1(){
    GateNot g = new GateNot (new Wire("inw"), new Wire("outa"));
    g.feed("1");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenot_X(){
    GateNot g = new GateNot (new Wire("inw"), new Wire("outa"));
    g.feed("X");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  @Test (timeout=3000) public void gatenot_propagate_results(){
    GateNot g = new GateNot (new Wire("inw"), new Wire("outa"));
    // output : X -> LO
    g.feed("1");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: LO -> LO
    ans = g.propagate();
    assertFalse(ans);
    //output: LO -> HI
    g.feed("0");
    ans = g.propagate();
    assertTrue(ans);
  }
  
  /////////////////////////////////////////////////////////
  
  // GateXor Tests
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gatexor1(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    expected.get(1).setSignal(Signal.HI);
    
    g.feed("01");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gatexor_00(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("00");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_01(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("01");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_10(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("10");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  @Test (timeout=3000) public void gatexor_11(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("11");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_X0(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("X0");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gatexor_1X(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("1X");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gatexor_XX(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    g.feed("XX");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_011(){
    GateXor g = new GateXor (wires3, new Wire("outa"));
    g.feed("011");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_0010(){
    GateXor g = new GateXor (wires4, new Wire("outa"));
    g.feed("0010");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatexor_propagate_results(){
    GateXor g = new GateXor (wires2, new Wire("outa"));
    // output : X -> LO
    g.feed("00");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: LO -> LO
    ans = g.propagate();
    assertFalse(ans);
    //output: LO -> HI
    g.feed("10");
    ans = g.propagate();
    assertTrue(ans);
  }
  
  
  /////////////////////////////////////////////////////////
  
  // GateNand Tests
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gatenand_1(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    expected.get(1).setSignal(Signal.HI);
    
    g.feed("01");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gatenand_00(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("00");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_01(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("01");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_10(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("10");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  @Test (timeout=3000) public void gatenand_11(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("11");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_X0(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("X0");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }    
  
  @Test (timeout=3000) public void gatenand_1X(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("1X");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gatenand_XX(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    g.feed("XX");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_011(){
    GateNand g = new GateNand (wires3, new Wire("outa"));
    g.feed("011");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_0010(){
    GateNand g = new GateNand (wires4, new Wire("outa"));
    g.feed("0010");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenand_propagate_results(){
    GateNand g = new GateNand (wires2, new Wire("outa"));
    // output : X -> LO
    g.feed("11");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: LO -> LO
    ans = g.propagate();
    assertFalse(ans);
    //output: LO -> HI
    g.feed("10");
    ans = g.propagate();
    assertTrue(ans);
  }
  
  
  /////////////////////////////////////////////////////////
  
  // GateNor Tests
  // does your constructor construct? Check a few Gate-inherited methods.
  @Test (timeout=3000) public void gatenor_1(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    List<Wire> expected = Arrays.asList(new Wire[]{new Wire("a"),new Wire("b")});
    assertEquals(expected, g.getInputs());
    
    expected.get(0).setSignal(Signal.LO);
    expected.get(1).setSignal(Signal.HI);
    
    g.feed("01");
    assertEquals(expected, g.getInputs());
  }
  
  
  @Test (timeout=3000) public void gatenor_00(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("00");
    boolean ans = g.propagate();
    assertEquals(sigs1, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_01(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("01");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_10(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("10");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  @Test (timeout=3000) public void gatenor_11(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("11");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_X0(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("X0");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }    
  
  @Test (timeout=3000) public void gatenor_1X(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("1X");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }    
  
  @Test (timeout=3000) public void gatenor_XX(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    g.feed("XX");
    boolean ans = g.propagate();
    assertEquals(sigsX, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_011(){
    GateNor g = new GateNor (wires3, new Wire("outa"));
    g.feed("011");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_0010(){
    GateNor g = new GateNor (wires4, new Wire("outa"));
    g.feed("0010");
    boolean ans = g.propagate();
    assertEquals(sigs0, g.read());
  }
  
  @Test (timeout=3000) public void gatenor_propagate_results(){
    GateNor g = new GateNor (wires2, new Wire("outa"));
    // output : X -> HI
    g.feed("00");
    boolean ans = g.propagate();
    assertTrue(ans);
    // output: HI -> HI
    ans = g.propagate();
    assertFalse(ans);
    //output: HI -> LO
    g.feed("10");
    ans = g.propagate();
    assertTrue(ans);
  }
}