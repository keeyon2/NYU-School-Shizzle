package nyu.heuristics;

public class Person {
	
	private int x;
	private int y;
	private int timeRemaining;
	private int id;
    public int currentTimeRemaining;
	
	public Person(int x, int y, int timeRemaining, int id) {
		this.x = x;
		this.y = y;
		this.timeRemaining = timeRemaining;
		this.id = id;
        this.currentTimeRemaining = timeRemaining;
	}
	
	public Person(Person person) {
		// TODO Auto-generated constructor stub
		this.x = person.x;
		this.y = person.y;
		this.timeRemaining = person.timeRemaining;
		this.id = person.id;
        this.currentTimeRemaining = person.currentTimeRemaining;
	}

	public int distanceToPerson(Person p) {
		int distance = 0;
		distance += Math.abs(p.getX() - this.x);
		distance += Math.abs(p.getY() - this.y);
				
		return distance;
	}
	
	public int distanceToHospital(Hospital h) {
		int distance = 0;
		distance += Math.abs(h.getX() - this.x);
		distance += Math.abs(h.getY() - this.y);
				
		return distance;
	}
	
	@Override 
	public String toString() {
		return Integer.toString(this.id);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getTimeRemaining() {
		return this.timeRemaining;
	}

    public void setTimeRemaining(int time) {
        timeRemaining = time;   
    }
}
