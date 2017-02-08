package edu.nyu.pqs.stopwatch.impl;

import java.util.*;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  static List<IStopwatch> stopwatchList = new ArrayList<IStopwatch>();
  private static Object lock = new Object();
  /**
   * Creates and returns a new IStopwatch object
   * @param id The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
   */
  public static IStopwatch getStopwatch(String id) {
    if(id=="null" || id.equals("")){
      throw new IllegalArgumentException("Empty or null id");
    }
    synchronized (lock){
      StopWatchImplementation stopwatch = new StopWatchImplementation(id);
      if (stopwatchList.contains(stopwatch)) {
        throw new IllegalArgumentException("This stopwatch id already exists");
      }
      stopwatchList.add(stopwatch);
      return stopwatch;  
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of al creates IStopwatch objects.  Returns an empty
   * list if no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {
    synchronized (lock){
      return stopwatchList;
    }
  }
}
