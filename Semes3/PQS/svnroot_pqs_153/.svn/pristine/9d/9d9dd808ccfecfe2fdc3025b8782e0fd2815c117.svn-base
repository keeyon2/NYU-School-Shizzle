package edu.nyu.cs.pqs.ps5;

import static org.junit.Assert.*;

import java.awt.Point;
import org.junit.Before;
import org.junit.Test;

public class MultiWindowCanvasModelTest {
  
  MultiWindowCanvasModel model;
  GUI gui1;
  GUI gui2;
  GUI gui3;
  
  @Before
  public void setUp() {
    model = MultiWindowCanvasModel.getInstance();
    gui1 = new GUI(model);
    gui2 = new GUI(model);
    gui3 = new GUI(model);
  }

  @Test
  public void addPointsFromModel_addsCorrectPointToAllGUIs() {
    Point p1 = new Point (10, 10);
    Point p2 = new Point (100, 100);
    model.requestDraw(p1);
    model.requestDraw(p2);
    
    assertTrue(gui1.getMultiWindowPanel().getPointsToDraw().contains(p1));
    assertTrue(gui1.getMultiWindowPanel().getPointsToDraw().contains(p2));
    assertTrue(gui2.getMultiWindowPanel().getPointsToDraw().contains(p1));
    assertTrue(gui2.getMultiWindowPanel().getPointsToDraw().contains(p2));
    assertTrue(gui3.getMultiWindowPanel().getPointsToDraw().contains(p1));
    assertTrue(gui3.getMultiWindowPanel().getPointsToDraw().contains(p2));
  }
  
  @Test
  public void addPointsFromModel_noAddingIncorrectPoints() {
    Point p1 = new Point (10, 10);
    Point p2 = new Point (100, 100);
    model.requestDraw(p1);
    
    assertFalse(gui1.getMultiWindowPanel().getPointsToDraw().contains(p2));
    assertFalse(gui2.getMultiWindowPanel().getPointsToDraw().contains(p2));
    assertFalse(gui3.getMultiWindowPanel().getPointsToDraw().contains(p2));
  }
  
  @Test
  public void clearPointsFromGUI_removesAllPointsFromAllGUIs() {
    Point p1 = new Point (10, 10);
    Point p2 = new Point (100, 100);
    Point p3 = new Point (150, 150);
    
    model.requestDraw(p1);
    model.requestDraw(p2);
    model.requestDraw(p3);
    model.requestGUIClear();
    
    assertTrue(gui1.getMultiWindowPanel().getPointsToDraw().isEmpty());
    assertTrue(gui2.getMultiWindowPanel().getPointsToDraw().isEmpty());
    assertTrue(gui3.getMultiWindowPanel().getPointsToDraw().isEmpty());
  }
}
