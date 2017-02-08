package edu.nyu.pqs.stopwatch.api;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



/**
*
* A thread-safe object that can be used for timing laps.  The stopwatch
* objects are created in the StopwatchFactory.  Different threads can
* share a single stopwatch object and safely call any of the stopwatch methods.
*
*/
public class Stopwatch implements IStopwatch{
	
	private String id;
	private CopyOnWriteArrayList<Long> lapTimes = new CopyOnWriteArrayList<Long>();
	private boolean running;
	private Object runningLock;
	private long startTime;
	private Object startTimeLock;
	
	/**
	 * Crates a new thread safe Stopwatch and sets the ID
	 * @param id
	 */
	public Stopwatch(String id) {
		this.id = id;
		this.running = false;
		this.startTime = 0;
		this.runningLock = new Object();
		this.startTimeLock = new Object();
	}
	
	/**
	 * Sets private variable running
	 * @param running
	 */
	private void setRunning(boolean running) {
	  synchronized(runningLock) {
	    this.running = running;
	  }
	}
	
	/**
	 * Returns private variable running
	 * @return running
	 */
	private boolean getRunning() {
	  synchronized(runningLock) {
	    return this.running;
	  }
	}
	
	/**
	 * Sets private variable start time
	 * @param time
	 */
	private void setStartTime(long time) {
	  synchronized(startTimeLock) {
	    this.startTime = time;  
	  }
	}
	
	/**
	 * Gets private variable start time
	 * @return
	 */
	private long getStartTime() {
	  synchronized(startTimeLock) {
	    return this.startTime;
	  }
	}

	 /**
   * Returns the Id of this stopwatch
   * Overrides IStopwatch Interface 
   * @return the Id of this stopwatch.  Will never be empty or null.
   */
	@Override
	public String getId() {
		return this.id;
	}

	 /**
   * Starts the stopwatch.
   * Overrides IStopwatch Interface
   * @throws IllegalStateException if called when the stopwatch is already running
   */
	@Override
	public void start() {
	  synchronized(this) {
  	  boolean running = this.getRunning();
  	  if (running) {
  	    throw new IllegalStateException("Stopwatch already running");
  	  }
  	  long startTime = System.currentTimeMillis();
  	  this.setRunning(true);
  	  this.setStartTime(startTime);
	  }
	}

	 /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * Overrides IStopwatch Interface
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
	@Override
	public void lap() {
	  synchronized(this) {
	    if (!this.getRunning()) {
	      throw new IllegalStateException("Called Lap when stopwatch isn't running");
	    }
	    long currentTime = System.currentTimeMillis();
	    if (this.lapTimes.isEmpty()) {
	      this.lapTimes.add(currentTime - this.getStartTime());
	    }
	    else {
	      long allLapTimesCombined = 0;
        for (long lapTime : this.lapTimes) {
          allLapTimesCombined += lapTime;
        }
	      this.lapTimes.add(currentTime - this.getStartTime() - allLapTimesCombined);
	    }
	  }
	}

	 /**
   * Stops the stopwatch (and records one final lap).
   * Overrides IStopwatch Interface
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
	@Override
	public void stop() {
	  synchronized(this) {
      if (this.getRunning() == false) {
        throw new IllegalStateException("Called stop when stopwatch isn't running");
      }
      long currentTime = System.currentTimeMillis();
      this.setRunning(false);
      if (this.lapTimes.isEmpty()) {
        this.lapTimes.add(currentTime - this.getStartTime());
      }
      else {
        long allLapTimesCombined = 0;
        for (long lapTime : this.lapTimes) {
          allLapTimesCombined += lapTime;
        }
        this.lapTimes.add(currentTime - this.getStartTime() - allLapTimesCombined);
      }
    }
	}

	 /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   * Overrides IStopwatch Interface
   */
	@Override
	public void reset() {
		synchronized(this) {
		  this.setRunning(false);
		  this.lapTimes.clear();
		}		
	}

	 /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * Overrides IStopwatch Interface
   * @return a list of recorded lap times or an empty list if no times are recorded.
   */
	@Override
	public List<Long> getLapTimes() {
		synchronized(this) {
		  return this.lapTimes;
		}
	}
	
	/**
	 * Returns the Stopwatch in string.
	 * Returns the following
	 * Thread ID
	 * *******************
	 * Lap 1: time1
	 * Lap 2: time2
	 * 
	 * @return String containing stopwatch ID and times
	 */
	@Override
	public String toString() {
	  synchronized(this) {
	    String StopwatchToString = "Thread " + this.id + "\n" +
	        "**********************************\n";
	    for (int i = 0; i < this.lapTimes.size(); i++) {
	      StopwatchToString += "Lap " + (i + 1) + (": ") + this.lapTimes.get(i) + "\n";
	    }	    
	    return StopwatchToString;
	  }
	}
	
	/**
	 * Override Equals to compare objects
	 * Compares on id and Lap Times
	 * @param Object to check equality with
	 * @return boolean containing if the param object is equal
	 */
	@Override
  public boolean equals(Object o) {
    synchronized (this) {
  	  if (o == this) {
        return true;
      }
      
      if (!(o instanceof Stopwatch)) {
        return false;
      }
      
      Stopwatch sw = (Stopwatch)o;
      
      boolean ComparingObjAndThisObjVarsConsitant   = 
          sw.getRunning() == this.getRunning()
          && sw.id == this.id
          && sw.getLapTimes().equals(this.getLapTimes());
      return ComparingObjAndThisObjVarsConsitant;
    }
  }
	
	/**
	 * Return hashCode for Stopwatched
	 * @return integer representing the hashCode for this Stopwatch
	 */
	@Override
	public int hashCode() {
	  synchronized(this) {
	    int result = 17;
	    result = 31 * result + (int)(this.getStartTime() ^ (this.getStartTime() >>> 32));
	    result = 31 * result + (this.id != null ? this.id.hashCode() : 0);
	    for (long currentLapTime : this.getLapTimes()) {
	      result = 31 * result + (int)(currentLapTime ^ (currentLapTime >>> 32));
	    }
	    result = 31 * (this.getRunning() ? 1 : 0);
	    return result;
	  }
	}
}
