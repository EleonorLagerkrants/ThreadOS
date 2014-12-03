
/////package kernel_src;
// 
//  SyncQueue.java
//  A monitor used to put threads to sleep inside this object.
//  This monitor will hold an array of QueueNode objects.
//  
//  Created by John Hildebrant on 2011-11-12.
// 
public class SyncQueue {
  // maintains an array of QueueNode objects, each representing a different 
  // condition and enqueuing all threads that wait for this condition. The size 
  // of the queue array should be determined via the SyncQueue constructor.
  private QueueNode[] queues = null;
  // number of condition/event types (default number is 10)
  private static final int COND_MAX = 10;
  // named constant indicating no parent process
  private static final int NO_PID = -1;
  
  // constructor that creates a queue and allows threads to wait for a condMax
  // number of condition/event types
  public SyncQueue(int condMax) {
    queues = new QueueNode[condMax];
    for (int i = 0; i < condMax; i++)
      queues[i] = new QueueNode();
  }
  
  // no-arg constructor
  public SyncQueue() {
    this(COND_MAX);
  }
  
  // enqueues the calling thread and puts it to sleep until a given
  // condition is satisfied, returning the ID of a child thread that has
  // woken up the calling thread.
  public int enqueueAndSleep(int condition) {
    if ((condition >=0) && (condition < queues.length)) {
      return queues[condition].sleep();
    }
    // return top-most parent pid
    return NO_PID;
  }
  
  // dequeues and wakes up a thread waiting for a given condition. If there
  // are two or more threads waiting for the same condition, only one thread
  // is dequeued and resumed.
  public void dequeueAndWakeup(int condition) {
    if ((condition >= 0) && (condition < queues.length))
      queues[condition].wakeup( queues[condition].size() - 1 );
  }
  
  // the two parameter version of this function can receive the calling thread's
  // ID, (tid) as the 2nd argument, which will be passed to the thread that
  // has been woken up from enqueueAndSleep(). 
  public void dequeueAndWakeup(int condition, int tid) {
    if ((condition >= 0) && (condition < queues.length))
      queues[condition].wakeup( tid );
  }
}