
//package kernel_src;
import java.util.*;

public class Scheduler_SRTF extends Thread
{
    private Vector<TCB> queue;
    private int timeSlice;
    private static final int DEFAULT_TIME_SLICE = 1000;

    // New data added to p161 
    private boolean[] tids; // Indicate which ids have been used
    private static final int DEFAULT_MAX_THREADS = 10000;

    // A new feature added to p161 
    // Allocate an ID array, each element indicating if that id has been used
    private int nextId = 0;
    private void initTid( int defaultMaxThreads ) {
      tids = new boolean[defaultMaxThreads];
  	  for ( int i = 0; i < defaultMaxThreads; i++ )
        tids[i] = false;
    }

    // A new feature added to p161 
    // Search an available thread ID and provide a new thread with this ID
    private int getNewTid( ) {
	    for ( int i = 0; i < tids.length; i++ ) {
  	    int tentative = ( nextId + i ) % tids.length;
  	    if ( tids[tentative] == false ) {
      		tids[tentative] = true;
      		nextId = ( tentative + 1 ) % tids.length;
      		return tentative;
  	    }
	    }
	    return -1;
    }

    // A new feature added to p161 
    // Return the thread ID and set the corresponding tids element to be unused
    private boolean returnTid( int tid ) {
	    if ( tid >= 0 && tid < tids.length && tids[tid] == true ) {
	      tids[tid] = false;
	      return true;
	    }
	    return false;
    }

    // A new feature added to p161 
    // Retrieve the current thread's TCB from the queue
    public TCB getMyTcb( ) {
      Thread myThread = Thread.currentThread( ); // Get my thread object
	    synchronized( queue ) {
  	    for ( int i = 0; i < queue.size( ); i++ ) {
  		    TCB tcb = ( TCB )queue.elementAt( i );
  		    Thread thread = tcb.getThread( );
  		    if ( thread == myThread ) // if this is my TCB, return it
  		      return tcb;
  	    }
  	  }
	    return null;
    }

    // A new feature added to p161 
    // Return the maximum number of threads to be spawned in the system
    public int getMaxThreads( ) {
	    return tids.length;
    }

    public Scheduler_SRTF( ) {
	    timeSlice = DEFAULT_TIME_SLICE;
	    queue = new Vector<TCB>( );
	    initTid( DEFAULT_MAX_THREADS );
    }

    public Scheduler_SRTF( int quantum ) {
	    timeSlice = quantum;
	    queue = new Vector<TCB>( );
	    initTid( DEFAULT_MAX_THREADS );
    }

    // A new feature added to p161 
    // A constructor to receive the max number of threads to be spawned
    public Scheduler_SRTF( int quantum, int maxThreads ) {
	    timeSlice = quantum;
	    queue = new Vector<TCB>( );
	    initTid( maxThreads );
    }

    private void schedulerSleep( ) {
	    try {
	      Thread.sleep( timeSlice );
	    } catch ( InterruptedException e ) {
	      }
    }

    // A modified addThread of p161 example
    public TCB addThread( Thread t, int cpuBurst ) {
	    t.setPriority( 2 );
	    TCB parentTcb = getMyTcb( ); // get my TCB and find my TID
	    int pid = ( parentTcb != null ) ? parentTcb.getTid( ) : -1;
	    int tid = getNewTid( ); // get a new TID
	    if ( tid == -1)
	      return null;
	    TCB tcb = new TCB( t, tid, pid, cpuBurst ); // create a new TCB
	    queue.add( tcb );
	    return tcb;
    }

    // A new feature added to p161
    // Removing the TCB of a terminating thread
    public boolean deleteThread( ) {
	    TCB tcb = getMyTcb( ); 
	    if ( tcb!= null )
	      return tcb.setTerminated( );
	    else
	      return false;
    }

    public void sleepThread( int milliseconds ) {
	    try {
	      sleep( milliseconds );
	    } catch ( InterruptedException e ) { }
    }
    
    // Method for sorting the Queue after burst time.
    public void sortQueue(Vector<TCB> queue ) {
    	boolean flag = true;
    	if(queue.size() < 4)
    		flag = false;
    	while(flag ) {
    		flag = false;
    		 for ( int i = 0; i < queue.size( ) -1 ; i++ ) {
    			 for(int j = 0; j < i; j++) {
    				if(queue.get(j).getThreadBurst() > queue.get(j+1).getThreadBurst()) {
    					TCB temp = queue.get(j);
    					queue.set(j, queue.get(j+1));
    					queue.set(j+1, temp);
    				}
    			 }
    		 }
    	}
    }
   // SRTF run method
    public void run() {
    	Thread current = null;

	    this.setPriority( 6 );
	
	    while ( true ) {
	    try {
		    // get the next TCB and its thread
		    if ( queue.size( ) == 0 )
		      continue;
		    sortQueue(queue); // Sorting the queue
		    TCB currentTCB = (TCB)queue.firstElement( );
		    if ( currentTCB.getTerminated( ) == true ) {
		    	queue.remove( currentTCB );
		    	returnTid( currentTCB.getTid( ) );
		    	continue;
		    }
		    current = currentTCB.getThread( );
		    if ( current != null ) {
		      if ( current.isAlive( ) )
			      current.setPriority(4);
		      else {
			      // Spawn must be controlled by Scheduler
			      // Scheduler must start a new thread
			      current.start( ); 
			      current.setPriority(4);
		      }
		    }
		
		    schedulerSleep( );
		    // System.out.println("* * * Context Switch * * * ");

		    synchronized ( queue ) {
		      if ( current != null && current.isAlive( ) )
			      current.setPriority(2);
		      queue.remove( currentTCB ); // rotate this TCB to the end
		      queue.add( currentTCB );
		    }
	    } catch ( NullPointerException e3 ) { };
	  }
  }
}