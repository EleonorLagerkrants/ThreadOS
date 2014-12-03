class Testa extends Thread {

    public void run( ) {
	String[] args1 = SysLib.stringToArgs( "TestThreada a 5000 0" );
	String[] args2 = SysLib.stringToArgs( "TestThreada b 1000 0" );
	String[] args3 = SysLib.stringToArgs( "TestThreada c 3000 0" );
	String[] args4 = SysLib.stringToArgs( "TestThreada d 6000 0" );
	String[] args5 = SysLib.stringToArgs( "TestThreada e 500  0" );
	SysLib.exec( args1 );
	SysLib.exec( args2 );
	SysLib.exec( args3 );
	SysLib.exec( args4 );
	SysLib.exec( args5 );
	SysLib.exit( );
    }
}
