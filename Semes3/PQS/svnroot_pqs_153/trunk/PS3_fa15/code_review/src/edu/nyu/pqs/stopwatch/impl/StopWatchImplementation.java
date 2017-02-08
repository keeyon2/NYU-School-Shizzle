package edu.nyu.pqs.stopwatch.impl;

import java.util.*;
import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * 
 * @author NIKITA
 * StopWatchImplementation implements IStopwatch interface. It implements thread safe methods.
 * StopWatchImplementation objects are created in StopwatchFactory class. Different threads can 
 * share same stopwatch object and call any method safely.
 *
 */
public class StopWatchImplementation implements IStopwatch{
  private String id="";
  private List<Long> lapTimes = new LinkedList<Long>();
  private Object lock = new Object();
  private enum Status {
    RUN, STOP
  }
  private Status stopwatchstatus;
  private long starttime;
  
  /**
   * StopWatchImplementation constructor
   * @param id which is stopwatch id
   */
  public StopWatchImplementation(String id) {
    this.id = id;
    stopwatchstatus=Status.STOP;
  }
  
  /**
   * returns id of stopwatch
   */
  public String getId(){
    return id;
  }
  
  /** Calculates lap since last lap or since start() was called
   * @throws IllegalStateException if stopwatch is not running when it is called
   */
  public void lap() throws IllegalStateException {
    synchronized (lock) {
      if(stopwatchstatus!=Status.RUN){
        throw new IllegalArgumentException("Error: Stopwatch " + id
            + " is not running.");	
      } else{
            long currentTime=new Date().getTime();
            lapTimes.add(currentTime-starttime);
            starttime=currentTime;	
        }	
    }
  }
  
  /**
   * Starts stopwatch
   * @throws IllegalStateException if stopwatch is already running
   */
  public void start() throws IllegalStateException{
    synchronized (lock) {
      if(stopwatchstatus==Status.RUN){
        throw new IllegalArgumentException("Error: Stopwatch " + id
            + " is already running.");	
      } else {
          starttime=new Date().getTime();
          stopwatchstatus=Status.RUN;	
        }	
    }
  }
  
  /**
   * Stops the stopwatch and calculates one last lap
   * @throws IllegalStateException if stopwatch is not running when it is called
   */
  public void stop() throws IllegalStateException{
    synchronized (lock) {
    	if(stopwatchstatus!=Status.RUN){
            throw new IllegalArgumentException("Error: Stopwatch " + id
                + " is not running.");	
          } else {
              lap();
              stopwatchstatus = Status.STOP;
            }
        }
  }
  
  /**
   * This method resets stopwatch and clears all lap records
   */
  public void reset(){
    synchronized (lock) {
      stopwatchstatus = Status.STOP;
      lapTimes.clear();
    }	
  }
  
  /**
   * This method returns laptimes stored
   */
  public List<Long> getLapTimes(){
    synchronized (lock) {
      return lapTimes;
    }
  }
  
  /**
   * returns true if object o is stopwatch object with the same id as this object
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof StopWatchImplementation)) {
      return false;
    }
    StopWatchImplementation stopwatch = (StopWatchImplementation) o;
    return stopwatch.id.equals(id);
  }
  
  /**
   * returns hashcode for id
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + id.hashCode();
    return result;
  }
  
  /** returns stopwatch object in string format
   * 
   */
  @Override
  public String toString() {
    return "ID:" + id + " State:" + stopwatchstatus + " Current laps:"
        + lapTimes.toString();
  }
}
