class Testc extends Thread {

    public void run( ) {
	String[] args1 = SysLib.stringToArgs( "TestThreada a 5000 0" );
	String[] args2 = SysLib.stringToArgs( "TestThreada b 1000 0" );
	String[] args3 = SysLib.stringToArgs( "TestThreada c 3000 0" );
	String[] args4 = SysLib.stringToArgs( "TestThreada d 6000 0" );
	String[] args5 = SysLib.stringToArgs( "TestThreada e 500  0" );
	SysLib.exec( args1 );
	try {
		Thread.sleep( 300 );
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SysLib.exec( args2 );
	try {
		Thread.sleep( 400 );
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SysLib.exec( args3 );
	
	try {
		Thread.sleep( 300 );
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SysLib.exec( args4 );
	
	try {
		Thread.sleep( 550 );
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SysLib.exec( args5 );
	for (int i = 0; i < 5; i++ )
	    SysLib.join( );
	//SysLib.cout( "Test2 finished\n" );
	SysLib.exit( );
    }
}

