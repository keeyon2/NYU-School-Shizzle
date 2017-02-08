package edu.nyu.cs.pqs.ps5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * @author Keeyon
 *  Main GUI unit to control visibles
 */
public class GUI implements Drawable{

  
  private JFrame frame;
  private MultiWindowPanel multiWindowPanel;
  private MultiWindowCanvasModel model;
  private JButton clearButton;
  
  /**
   * Initializes the GUI.  We make public so all can use.
   * This will set the model, add itself as a listener, 
   * and then create the GUI itself
   * @param model
   */
  public GUI(MultiWindowCanvasModel model) {
    this.model = model;
    model.addListener(this);
    setUpGUI();
  }
  
  /**
   * We have button, panel and Jframe logic in the GUI, 
   * this seperates them out and calls them in order
   */
  private void setUpGUI() {
    setUpButton();
    setUpMultiWindowPanel();
    setUpJFrame();
  }
  
  /**
   * We Create the button and give it an action 
   * to request the model to clear the GUI
   */
  private void setUpButton() {
    clearButton = new JButton("Clear");
    clearButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
          model.requestGUIClear();
      }
  });
    
  }

  /**
   * This sets up our custom Panel.  We add size, add the button,
   * make the desired mouse events call our own functions
   *
   */
  private void setUpMultiWindowPanel() {
    multiWindowPanel = new MultiWindowPanel();
    multiWindowPanel.setPreferredSize(new Dimension(500, 500));
    multiWindowPanel.setLayout(new BorderLayout());
    
    multiWindowPanel.add(clearButton, BorderLayout.SOUTH);
    multiWindowPanel.addMouseMotionListener(new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent e) {
        mouseDraggedOnPanel(e);
      }

      @Override
      public void mouseMoved(MouseEvent e) {
      }
    });
    
    multiWindowPanel.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        mouseClickedOnPanel(e);
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseReleased(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      }
    });
  }
  
  /**
   * This will create the JFrame.  We give a random 
   * starting location to make them not overlap, and we 
   * add the multiWindowPanel to the JFrame.
   */
  private void setUpJFrame() {
    Random randomGenerator = new Random();
    int randomXStartLocation = randomGenerator.nextInt(1000);
    int randomYStartLocation = randomGenerator.nextInt(1000);
    
    frame = new JFrame();
    frame.getContentPane().add(multiWindowPanel);
    frame.setResizable(false);
    frame.pack();
    frame.setLocation(randomXStartLocation,randomYStartLocation); 
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  /**
   * Called when mouse is clicked
   * When this occurs, we request a Draw at a point of the event to the
   * model
   * @param e Mouse event that triggered this call
   */
  private void mouseClickedOnPanel(MouseEvent e) {
    model.requestDraw(e.getPoint());
  }
  
  /**
   * Called when mouse is dragged  
   * When this occurs, we request a Draw at a point of the event to the
   * model
   * @param e Mouse event that triggered this call
   */
  private void mouseDraggedOnPanel(MouseEvent e) {
    model.requestDraw(e.getPoint());
  }

  /* (non-Javadoc)
   * @see edu.nyu.cs.pqs.ps5.Drawable#drawAtPoint(java.awt.Point)
   * This function is called when the model indicates that we should 
   * draw at a desired point
   * We add the point to the panels points to draw
   */
  @Override
  public void drawAtPoint(Point p) {
    multiWindowPanel.addPointToDraw(p);
  }

  /* (non-Javadoc)
   * @see edu.nyu.cs.pqs.ps5.Drawable#clearAllPoints()
   * This function is called when the model indicates that we should
   * clear the GUI
   * 
   * We call the funtion to remove all points to draw
   */
  @Override
  public void clearAllPoints() {
    multiWindowPanel.clearAllDrawnPoints();
  }
  
  /** Package protected function to get the multiWindowpanel for testing purposes
   * @return
   */
  MultiWindowPanel getMultiWindowPanel() {
    return this.multiWindowPanel;
  }
}