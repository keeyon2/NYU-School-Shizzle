package nyu.heuristics;

import java.util.ArrayList;

public class Hospital {

	private int x;
	private int y;
	private int id;
    public ArrayList<Ambulance> ambulances = new ArrayList<Ambulance>();
    public boolean arranged;
	
	Hospital(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		arranged = false;
	}
	
	public Hospital(Hospital hospital) {
		this.x = hospital.x;
		this.y = hospital.y;
		this.id = hospital.id;
		this.ambulances = hospital.ambulances;
		this.arranged = hospital.arranged;
	}

    public void addAmbulancesWithId(ArrayList<Integer> ambIds) {
        for (int i = 0; i < ambIds.size(); i++) {
            Ambulance amb = new Ambulance(ambIds.get(i), x, y);
            ambulances.add(amb);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Hospital:" + (id + 1) + "|" + x + "," + y + "," + 
                ambulances.size() + "|");
        for (int i = 0; i < ambulances.size(); i++) {
            output.append(ambulances.get(i).id + 1);
            if (i < ambulances.size() - 1) {
                output.append(",");
            }
            else {
                output.append("\n");
            }
        }
        return output.toString();
    }

    public ArrayList<Ambulance> getAmbulances() {
        ArrayList<Ambulance> a = new ArrayList<Ambulance>(ambulances);
        return a;
    }

	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int distanceToHosp(Hospital h) {
		int distance = 0;
		distance += Math.abs(h.getX() - this.x);
		distance += Math.abs(h.getY() - this.y);
				
		return distance;
	}
}
