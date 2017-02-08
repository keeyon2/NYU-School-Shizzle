package edu.nyu.cs.pqs.ps4;

import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    Main m = new Main();
    Connect4Gui gui = m.ConnectGui("OJ Simpson", Color.RED, Color.BLUE);
    ComputerPlayer cp = m.ConnectComputer("roger Rabit");
  }
  
  /** Example of how to get the singleton Server
   * @return gameServer
   */
  public Server getServer() {
    Server gameServer = Server.getInstance();
    return gameServer;
  }
  
  /** Example of how to create computer that connects to the Server 
   * @param name
   * @return connected computer player
   */
  public ComputerPlayer ConnectComputer(String name){
    ComputerPlayer comp = ComputerPlayer.newInstance(name);
    comp.connectToServer();
    return comp;
  }
  
  /** Example of how to Create GUI
   * Note, if more than one is made, you will need to move them so they are 
   * not on top of each other
   * @return new connected Gui Player
   */
  public Connect4Gui ConnectGui(String name, Color p1Color, Color p2Color) {
    Connect4Gui gui = new Connect4Gui.GuiBuilder(name).playerOneColor(p1Color)
        .playerTwoColor(p2Color).gameName("GameTime").build();
    return gui;
  }
}
