package edu.nyu.cs.pqs.ps5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

/**
 * @author Keeyon
 * Panel that keeps track of all points that have been drawn on.
 * Override paint to draw a circle at every point drawn on
 */
public class MultiWindowPanel extends JPanel {
  /**
   * Point locations of where to draw
   */
  private Set<Point> pointsToDraw;
  
  /**
   * Creates the set of points to draw on
   */
  MultiWindowPanel() {
    pointsToDraw = new HashSet<Point>();
  }
  
  /* (non-Javadoc)
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   * 
   * We override the paint to take all of our points we indicated to draw on
   * and we draw and fill circles on the canvas at those points.
   */
  @Override 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    for (Point point : pointsToDraw) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.setPaint(Color.black);
      g2d.fillOval(point.x,point.y, 20,20);
    }
  }
  
  /**
   * Adds a point to the set of points to draw on.
   * @param p Point to add to pointsToDraw set
   */
  public void addPointToDraw(Point p) {
    pointsToDraw.add(p);
    repaint();
  }
  
  /**
   * Clears all points from the set of pointsToDraw
   */
  public void clearAllDrawnPoints() {
    pointsToDraw.clear();
    repaint();
  }
  
  /** Package protected getter for the point set for testing purposes
   * @return
   */
  Set<Point> getPointsToDraw () {
    return this.pointsToDraw;
  }
}
