
/** Example of using unit tests for project 4.  This is
  * partially how your code will be graded.  Later in the class we will
  * write our own unit tests.  To run them on the command line, make
  * sure that the junit-cs211.jar is in the project directory.
  * 
  *  demo$ javac -cp .:junit-cs211.jar *.java     # compile everything
  *  demo$ java  -cp .:junit-cs211.jar P4Tests    # run tests
  * 
  * On windows replace : with ; (colon with semicolon)
  *  demo$ javac -cp .;junit-cs211.jar *.java     # compile everything
  *  demo$ java  -cp .;junit-cs211.jar P4Tests    # run tests
  */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

public class P4Tests {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main(
                                    "SignalTests"
                                    ,"WireTests"
                                    ,"ContactTests"
                                    ,"ExceptionTests"
                                    ,"GateTests"
                                    ,"CircuitTests"
                                    ,"OverallTests"
                                    //,"FeedbackCircuitTests"  // HONORS STUDENTS: include these tests when you attempt the honors problem.
                                   );
  }

  /////////////////////////////////////////////////////////
  
  // GateSim can't really be tested... its only effect is to write to System.out,
  // and we can't really monitor that with a unit test.  :-/
//  @Test (timeout=3000) public void gatesim() throws IOException {
//    PrintStream ps = new PrintStream("testing_outfile.txt");
//    PrintStream oldSysout = System.out;
//    System.out = ps; // nope. it's final.
//    GateSim.main(new String[]{"and", "11"});
//    ps.close();
//    System.out = oldSysout;
//    Scanner sc = new Scanner("testing_outfile.txt");
//    String s = sc.next();
//    assertEquals("1", s);
//  }
  
  /////////////////////////////////////////////////////////
}
