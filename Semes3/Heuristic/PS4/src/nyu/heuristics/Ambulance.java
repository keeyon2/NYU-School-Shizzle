package nyu.heuristics;

import java.util.ArrayList;
 
public class Ambulance {
    // Variables
    public int id;
    public int startX;
    public int startY;
    public StringBuilder output = new StringBuilder();
    public int timeTraveled;
    public boolean freeToMove;

    Ambulance(int id, int startX, int startY) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        timeTraveled = 0;
        freeToMove = true;
    }

    public Ambulance(Ambulance ambulance) {
        this.id = ambulance.id;
        this.startX = ambulance.startX;
        this.startY = ambulance.startY;
        this.output = ambulance.output;
        this.timeTraveled = ambulance.timeTraveled;
        this.freeToMove = ambulance.freeToMove;
    }

    public void AddToOutput(ArrayList<Person> peopleSaved, Hospital endingH, int cost) {
        output.append("Ambulance:" + (id + 1) + "|" + startX + 
            "," + startY + "|");
        for (int i = 0; i < peopleSaved.size(); i ++) {
            Person p = new Person(peopleSaved.get(i));
            output.append((p.getId() + 1) + "," + p.getX() + "," + p.getY() + "," +
                    p.getTimeRemaining());
            
            if (i < peopleSaved.size() - 1) {
            	output.append(";");
            }
            
        }
        output.append("|" + endingH.getX() + "," + endingH.getY() + "\n");
        startX = endingH.getX();
        startY = endingH.getY();
        this.timeTraveled += cost;
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
