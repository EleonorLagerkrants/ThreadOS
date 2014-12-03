
//package kernel_src;

class Testb extends Thread {

    public void run( ) {
	String[] args1 = SysLib.stringToArgs( "TestThreadb a 5000" );
	String[] args2 = SysLib.stringToArgs( "TestThreadb b 1000" );
	String[] args3 = SysLib.stringToArgs( "TestThreadb c 3000" );
	String[] args4 = SysLib.stringToArgs( "TestThreadb d 6000" );
	String[] args5 = SysLib.stringToArgs( "TestThreadb e 500" );
	
	SysLib.exec( args1 );
	SysLib.exec( args2 );
	SysLib.exec( args3 );
	SysLib.exec( args4 );
	SysLib.exec( args5 );

	for (int i = 0; i < 5; i++ )
	    SysLib.join( );
	SysLib.cout( "Testb finished\n" );
	SysLib.exit( );
    }
}
