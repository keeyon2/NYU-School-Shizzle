package edu.nyu.cs.pqs.ps5;

import java.awt.Point;
import java.util.ArrayList;


/**
 * @author Keeyon
 * Singleton model that handles the passing of messages to the GUIs
 * for drawing and clearing points
 */
public class MultiWindowCanvasModel {
  
  /**
   * Create the singleton instance here
   */
  private static MultiWindowCanvasModel instance = new MultiWindowCanvasModel();
 
  private ArrayList<Drawable> drawableListeners;
  
  /**
   * Private constructor which enables the singleton pattern
   * We instantiate the listeners array here
   */
  private MultiWindowCanvasModel() {
    drawableListeners = new ArrayList<Drawable>(); 
  }
  
  /**
   * This is what will be called to get our singleton instance
   * @return
   */
  public static MultiWindowCanvasModel getInstance() {
    return instance;
  }
  
  /**
   * Drawables call this function to add themselves to the set of
   * listeners
   * @param d
   */
  public void addListener(Drawable d) {
    this.drawableListeners.add(d);
  }
  
  /**
   * Drawables will request to draw.  To do so, they will call this 
   * function
   * @param p  Point of where to draw
   */
  public void requestDraw(Point p) {
    for (Drawable listener : drawableListeners) {
      listener.drawAtPoint(p);
    }
  }
  
  /**
   * A Drawable can request to clear the canvas.  To do so, they will call 
   * this function.
   */
  public void requestGUIClear() {
    for (Drawable listener : drawableListeners) {
      listener.clearAllPoints();
    }
  }
}
