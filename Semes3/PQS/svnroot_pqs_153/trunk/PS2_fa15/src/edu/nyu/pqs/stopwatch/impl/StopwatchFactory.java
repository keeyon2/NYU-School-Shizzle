package edu.nyu.pqs.stopwatch.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;
import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
	
	private static CopyOnWriteArrayList<IStopwatch> watchList = new CopyOnWriteArrayList<IStopwatch>();
	private static CopyOnWriteArrayList<String> idList = new CopyOnWriteArrayList<String>();
	private static Object CreateNewWatchAndReturnWatchListLock = new Object();
	
	
	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) {
	  if (id == null) {
	    throw new IllegalArgumentException("id is null");
	  }
	  else if (id.equals("")) {
	    throw new IllegalArgumentException("id is empty");
	  }
	  else if (idList.contains(id)) {
	    throw new IllegalArgumentException("id already taken"); 
	  }

		synchronized(CreateNewWatchAndReturnWatchListLock) {
			Stopwatch createdWatch = new Stopwatch(id);
			watchList.add(createdWatch);
			return createdWatch;	
		}
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of all created IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
		synchronized(CreateNewWatchAndReturnWatchListLock) {
			return watchList;
		}
	}
}
