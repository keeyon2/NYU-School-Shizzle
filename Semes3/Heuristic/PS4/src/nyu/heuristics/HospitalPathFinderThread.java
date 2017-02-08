package nyu.heuristics;

import java.util.ArrayList;
import java.util.Collections;

public class HospitalPathFinderThread implements Runnable{

	Hospital hospital;
	ArrayList<Person> people;
	int timeTaken = 0;
	
	public HospitalPathFinderThread(Hospital h, ArrayList<Person> p) {
		hospital = new Hospital(h);
		people = new ArrayList<Person>(p);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
        ArrayList<Person> remainingPeople = new ArrayList<Person>(people);
        //ArrayList<Ambulance> workingAmbulances = new ArrayList<ambulance>(h.ambulances);
        ArrayList<Ambulance> workingAmbulances = hospital.ambulances;

        while (!remainingPeople.isEmpty()) {
            int amountOfAmbRoutesFound = 0;
            for (int currentAmbIndex = 0; currentAmbIndex < workingAmbulances.size(); 
                    currentAmbIndex++) {
                passangerNumberLoop:
                for (int totalPassangers = 4; totalPassangers > 0; totalPassangers--) {

                    // Update Temp
                    ArrayList<Person> timeDecRemainingPeople = 
                        new ArrayList<Person>(remainingPeople);

                    /*
                    Ambulance currentAmb = new Ambulance(
                            workingAmbulances.get(currentAmbIndex));
                    */
                    Ambulance currentAmb = workingAmbulances.get(currentAmbIndex);

                    // Set up Decreased Time
                    for (int i = 0; i < timeDecRemainingPeople.size(); i++) {
                        int newTime = timeDecRemainingPeople.get(i).getTimeRemaining();
                        newTime -= currentAmb.timeTraveled;
                        timeDecRemainingPeople.get(i).currentTimeRemaining -= 
                            currentAmb.timeTraveled;
                    }
                
                    // Find path of survival
                    boolean survive = false;

                    for (int whichMin = 0; whichMin < timeDecRemainingPeople.size();
                            whichMin++) {

                        // Person minDistPerson = findMinimumDistancePerson(
                                //timeDecRemainingPeople);
                        Person minTimePerson = findMinimumTimePerson(timeDecRemainingPeople);
                        ArrayList<Person> closestNeighbors = findClosestNeighbors(
                                minTimePerson, totalPassangers, remainingPeople);
                        ArrayList<Person> bestPath = findBestPath(closestNeighbors);
                        int bestPathCost = findPathDistance(bestPath);
                        survive = doAllSurvive(bestPathCost, bestPath);

                        // if survive, add to ambulance cost
                        if (survive) { 
                            // Update Ambulances
                            currentAmb.AddToOutput(bestPath, hospital, bestPathCost);
                            
                            // Remove from Original List
                            ArrayList<Integer> idsToRemove = new ArrayList<Integer>();
                            for (int bestPathIndex = 0; bestPathIndex < bestPath.size();
                                    bestPathIndex++) {
                                idsToRemove.add(bestPath.get(bestPathIndex).getId());
                            }

                            for (int ri = 0; ri < idsToRemove.size(); ri++) {
                                for (int rii = 0; rii < remainingPeople.size(); rii++) {
                                    if (remainingPeople.get(rii).getId() == 
                                            idsToRemove.get(ri)) {
                                        remainingPeople.remove(rii);
                                    }
                                }
                            }
                            amountOfAmbRoutesFound++;
                            break passangerNumberLoop;
                        }

                        // else remove and try again
                        else
                        {
                            int minTimeId = minTimePerson.getId(); 
                            for (int mi = 0; mi < timeDecRemainingPeople.size(); mi++) {
                                if (timeDecRemainingPeople.get(mi).getId() == minTimeId) {
                                    timeDecRemainingPeople.remove(mi);
                                }
                            }
                        }
                    }
                }
            }
            if (amountOfAmbRoutesFound == 0) {
                break;
            }
        }
        //System.out.println("Thread Complete");
	}

    public boolean doAllSurvive(int time, ArrayList<Person> p) {
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).currentTimeRemaining < time) {
                return false;
            }
        }
        return true;
    }

    public int factorial(int number) {
        return ((number <= 1) ? 1 : number * factorial(number - 1));
    }

    public int findPathDistance (ArrayList<Person> originalList) {
        ArrayList<Person> origList = new ArrayList<Person>(originalList);

        // Start 1 for unload
        int totalTime = 1;

        // Load time
        totalTime += origList.size();

        // H to First patient
        totalTime += origList.get(0).distanceToHospital(hospital);

        // Last Patient to H 
        totalTime += origList.get(origList.size() - 1).distanceToHospital(hospital);

        for (int i = 0; i < origList.size() - 1; i++) {
            totalTime += origList.get(i).distanceToPerson(origList.get(i + 1));
        }
        return totalTime;
    }

    public ArrayList<Person> findBestPath(ArrayList<Person> originalList) {
        ArrayList<Person> origList = new ArrayList<Person>(originalList);
        ArrayList<Person> resultList = new ArrayList<Person>(originalList);

        if (origList.size() == 1) {
            return origList;
        }

        int iterations = factorial(origList.size());
        int currentMin = Integer.MAX_VALUE;

        for (int i = 0; i < iterations; i++) {
            int switchIndex = i % origList.size();
            if (switchIndex == origList.size() - 1) {
                switchIndex = 0;
            }
            
            Collections.swap(origList, switchIndex, switchIndex + 1);
            int tempScore = findPathDistance(origList);
            if (tempScore < currentMin) {
                resultList = new ArrayList<Person>(origList);
                currentMin = tempScore;
            }
        }

        return resultList;
    }

    public Person findMinimumDistancePerson(ArrayList<Person> p) {
        int minDistance = Integer.MAX_VALUE;
        Person minPerson = new Person(-10, -10, -10, -10);
        for (int i = 0; i < p.size(); i++) {
            int tempDistance = p.get(i).distanceToHospital(hospital);
            if (tempDistance < minDistance) {
                minDistance = tempDistance;
                minPerson = new Person(p.get(i));
            }
        }
        return minPerson;
    }

    public Person findMinimumTimePerson(ArrayList<Person> p) {
        int minTime = Integer.MAX_VALUE;
        Person minPerson = new Person(-10, -10, -10, -10);
        for (int i = 0; i < p.size(); i++) {
            int tempTime = p.get(i).currentTimeRemaining;
            if (tempTime < minTime) {
                minTime = tempTime;
                minPerson = new Person(p.get(i));
            }
        }
        return minPerson;
    }

    // Returns self in the return ArrayList
    ArrayList<Person> findClosestNeighbors(Person p, int numberOfNeighbors,
            ArrayList<Person> allPeople) {
        ArrayList<Person> resultingNeighbors = new ArrayList<Person>();
        ArrayList<Integer> resultingNeighborsDistance = new ArrayList<Integer>();

        int actualNeighbors = 0;

        if (allPeople.size() <= numberOfNeighbors) {
            actualNeighbors = allPeople.size() - 1;
        }
        else {
            actualNeighbors = numberOfNeighbors;
        }
        if (actualNeighbors == 0) {
            resultingNeighbors.add(p);
            return resultingNeighbors;
        }

        // Initialize
        for (int i = 0; i < actualNeighbors; i++) {
            Person temp = new Person(p);
            resultingNeighbors.add(temp);
            resultingNeighborsDistance.add(Integer.MAX_VALUE);
        }

        int currentMaxDistance = Collections.max(resultingNeighborsDistance);
        for (int i = 0; i < allPeople.size(); i++) {
            /*
            if (allPeople.get(i).getId() == p.getId()) {
                continue;
            }
            */

            int tempDistance = allPeople.get(i).distanceToHospital(hospital);

            // Need to replace max
            if (tempDistance < currentMaxDistance) {

                // find index of max
                int indexOfMax = -1;
                for (int j = 0; j < actualNeighbors; j++) {
                    int check = resultingNeighborsDistance.get(j);
                    if (check == currentMaxDistance) {
                        indexOfMax = j;
                        break;
                    }
                }

                // replace that index with this tempDistance
                resultingNeighborsDistance.remove(indexOfMax);
                resultingNeighborsDistance.add(indexOfMax, tempDistance);

                // replace resultingNeighbors at same index with person @ i
                Person replacingPerson = new Person(allPeople.get(i));
                resultingNeighbors.remove(indexOfMax);
                resultingNeighbors.add(indexOfMax, replacingPerson);

                // Update currentMax
                currentMaxDistance = Collections.max(resultingNeighborsDistance);
            }
        }

        return resultingNeighbors;
    }
}
