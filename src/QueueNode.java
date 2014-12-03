
//package kernel_src;
// 
//  QueueNode.java
//  A container for holding threads to be put to sleep or woken up.
//  
//  Created by John Hildebrant on 2011-11-12.
// 
import java.util.Vector;

public class QueueNode {
  private Vector<Integer> queue;
  
  //constructor
  public QueueNode() {
    queue = new Vector<Integer>();
  }
  
  // sleep until notified if there are no waiting threads
  // return a tid that is waiting
  public synchronized int sleep() {
    if (size() == 0)
      try {
        wait();
      } catch (InterruptedException iex) {}
    return queue.remove( size() - 1 );
  }
  
  // add tid to queue and notify
  public synchronized void wakeup(int tid) {
    queue.add(tid);
    notify();
  }
  
  // getter for number of tids in queue
  public synchronized int size() {
    return queue.size();
  }
}