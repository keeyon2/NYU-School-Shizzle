package edu.nyu.cs.pqs.ps4;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.util.UUID;

/**
 * @author Keeyon
 * Java Connect 4 Gui
 * If multiple are created, need to move them so 
 * windows are not on top of each other
 */
public class Connect4Gui implements ConnectFourPlayer, ActionListener {

  private final String name;
  private final int totalRows;
  private final int totalColumns;
  private Color playerOneColor;
  private Color playerTwoColor;
  private String gameName;
  private JFrame frame;
  private JPanel panel;
  private GridLayout myGrid;
  private int[] rowPlacementAtColumn;
  private Server gameServer;
  private BoardSpace myBoardSpace;
  private UUID myUUID;
  
  /** Private Constructor. Made with Builder
   * @param builder
   */
  private Connect4Gui(GuiBuilder builder) {
    gameName = builder.gameName;
    name = builder.name;
    playerOneColor = builder.playerOneColor;
    playerTwoColor = builder.playerTwoColor;
    totalRows = 6;
    totalColumns = 7;
    
    initializeRowPlacementArray();
    
    if (gameName.isEmpty()) {
      gameName = "Connect 4 Game";
    }
    
    drawGui();
    connectToServer();
  }
  
  /**
   * This is how we connect to server and store the appropriate data
   */
  private void connectToServer() {
    gameServer = Server.getInstance();
    myUUID = gameServer.registerPlayer(this, name);
    myBoardSpace = gameServer.getMyBoardSpace(myUUID);
    gameServer.playerIsReady(myUUID, true);
  }

  /**
   * Creates the place holder for which row to draw on
   */
  private void initializeRowPlacementArray() {
    rowPlacementAtColumn = new int[totalColumns];
    for (int column = 0; column < totalColumns; column++) {
      rowPlacementAtColumn[column] = totalRows - 1;
    }
  }
  
  /**
   *  Draws the GUI
   * 
   */
  private void drawGui() {
    frame = new JFrame(gameName);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myGrid = new GridLayout(7,7);
    panel = new JPanel();
    panel.setLayout(myGrid);
    
    addInitialEmptyCircles();
    
    frame.setContentPane(panel);
    frame.setPreferredSize(new Dimension(1000,400));
    frame.pack();
    frame.setVisible(true);
  }
  
  /**
   * This creates our initial grid with empty circles
   */
  private void addInitialEmptyCircles() {
    for (int row = 0; row < totalRows; row++) {
      for (int column = 0; column < totalColumns; column++) {
        panel.add(new CirclePanel());
      }
    }
    
    for (int column = 0; column < totalColumns; column++) {
      JButton button = new JButton("Column " + column);
      button.setActionCommand(Integer.toString(column));
      button.addActionListener(this);
      panel.add(button);
    }
  }
  
  public void actionPerformed(ActionEvent ae) {
    int moveColumn = Integer.parseInt(ae.getActionCommand());
    gameServer.makeMoveAtZeroIndexedColumn(myUUID, moveColumn);
  }
  
  /** Draw 
   * @param row
   * @param column
   * @param color
   */
  private void drawCircleAtPosition(int row, int column, Color color) {
    int location = getIndexFromPoint(row, column);
    CirclePanel newColoredCircle = (CirclePanel) panel.getComponent(location);
    newColoredCircle.setColor(color);
    panel.add(newColoredCircle, location);
    panel.repaint();
  }
  
  /** How we get from BoardSpace to color
   * @param boardSpace
   * @return Color based on boardSpace
   */
  private Color boardSpaceToColor(BoardSpace boardSpace) {
    if (boardSpace == BoardSpace.PLAYER1) {
      return playerOneColor;
    }
    
    else if (boardSpace == BoardSpace.PLAYER2) {
      return playerTwoColor;
    }
    
    else {
      return Color.white;
    }
  }
  
  /** This is how we will convert point received by server to 
   * Place to Draw in GUI
   * @param row
   * @param column
   * @return Index for us to draw content on
   */
  private int getIndexFromPoint(int row, int column) {
    return (row * totalColumns + column);
  }
  
  @Override
  public void PlayerHasWon(String playerName, BoardSpace winningPlayer) {
    String gameOverString = "Game Over!\n";
    
    if (winningPlayer == BoardSpace.EMPTY) {
      gameOverString += "The Game ended in a Tie!";
    }
    
    else if (winningPlayer == myBoardSpace) {
      gameOverString += "You WON! Great job " + playerName;
      
    }
    
    else {
      gameOverString += "You lost :(.  Maybe you'll beat " + playerName + 
          " next time.";
    }
    JOptionPane.showMessageDialog(frame, gameOverString);
  }

  @Override
  public void PlayerHasMadeMove(String playerName, BoardSpace playerMadeMove,
      int column) {
    placePieceAfterReceivedMoveFromServer(playerMadeMove, column);
  }

  /** This will draw the Piece after successful move reported from Server 
   * @param playerMadeMove
   * @param column
   */
  private void placePieceAfterReceivedMoveFromServer(BoardSpace playerMadeMove,
      int column) {
    Color newColor = boardSpaceToColor(playerMadeMove);
    int rowPlacement = receiveRowPlacementToDraw(column);
    drawCircleAtPosition(rowPlacement, column, newColor);
  }

  /** When given the column to draw, this determines which row to also draw in
   * @param column
   * @return row to draw on
   */
  private int receiveRowPlacementToDraw(int column) {
    int rowPlacementLocation = rowPlacementAtColumn[column];
    rowPlacementAtColumn[column]--;
    return rowPlacementLocation;
  }

  @Override
  public void PlayerTurnToMakeMove(String playerName, BoardSpace playersTurn) {
  }
  
  /**
   * @author Keeyon
   * Builder for the Connect4 Game
   * Optional Params for colors for 
   * Each Player. Also for The Label of the Game
   */
  public static class GuiBuilder {
    // Required Variables
    private final String name;
    
    // Optional Variables
    private Color playerOneColor;
    private Color playerTwoColor;
    private String gameName = "";
    
    /** Construction with required parameter of Name
     * @param name
     */
    public GuiBuilder(String name) {
      this.name = name;
    }
    
    /** Building optional Player 1 Color
     * @param color1
     * @return GuiBuilder
     */
    public GuiBuilder playerOneColor(Color color1) {
      playerOneColor = color1;
      return this;
    }
    
    /** Building optional player 2 color
     * @param color2
     * @return GuiBuilder
     */
    public GuiBuilder playerTwoColor(Color color2) {
      playerTwoColor = color2;
      return this;
    }
    
    /** Building optional name of game
     * @param gameName
     * @return GuiBuilder
     */
    public GuiBuilder gameName(String gameName) {
      this.gameName = gameName;
      return this;
    }
    
    /** This will build and return the Connect4Gui
     * @return new Connect4 Gui
     */
    public Connect4Gui build() {
      return new Connect4Gui(this);
    }
  }
}
