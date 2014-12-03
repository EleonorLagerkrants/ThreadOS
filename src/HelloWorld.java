

public class HelloWorld extends Thread {
  // Any Java class run by ThreadOS must be an extension of the Java Thread class
  // http://download.oracle.com/javase/6/docs/api/java/lang/Thread.html

  public HelloWorld() {
  }
  
  public void run() {
	  SysLib.cout( "Hello, world\n" );  // using SysLib vs System.out
	  SysLib.exit();                    // (see SysLib.java)
	  return;
  }
}
