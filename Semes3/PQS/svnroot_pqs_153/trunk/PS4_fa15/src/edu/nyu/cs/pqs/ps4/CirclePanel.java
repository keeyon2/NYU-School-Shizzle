package edu.nyu.cs.pqs.ps4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Keeyon
 * These are the circles we draw
 */
public class CirclePanel extends JPanel {
  
  private Color myColor;
  
  /**
   * Constructor initializes size and color
   */
  public CirclePanel() {
    setPreferredSize(new Dimension(100, 100));
    myColor = Color.white;
    setBackground(Color.white);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawCircle(g, 70, 30, 20);
  }

  /** This will fill in our circle based on our color
   * @param cg Graphic to draw
   * @param xCenter xCenter of the circle 
   * @param yCenter yCenter of the circle
   * @param r Radius of circle
   */
  private void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
    cg.setColor(myColor);
    cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
    cg.setColor(Color.black);
    cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
  }
  
  /** This sets our color to a new color
   * Package private so the Connect4Gui can call
   * @param color of inner filled circle
   */
  /**
   * @param color
   */
  void setColor(Color color) {
    myColor = color;    
  }
}