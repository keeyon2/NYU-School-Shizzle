package nyu.heuristics;

import java.util.ArrayList;
import java.util.Random;

public class AmbulanceProblem {
	ArrayList<Person> allPeople;
	ArrayList<Integer> ambulanceCount;
	
	ArrayList<Hospital> allHospitals;
	ArrayList<Hospital> kMeansHospitals;
	ArrayList<Hospital> endingHospitals;
    
	ArrayList<ArrayList<Person>> peopleClustered;
	public final int HOSPITAL_NUMBER = 5;
	public final int HOSPITAL_CHANGE_MAX = 0;
	Random randomGenerator = new Random();
	
	AmbulanceProblem(ArrayList<Person> people, ArrayList<Integer> ambulances) {
		allPeople = new ArrayList<Person>(people);
        ambulanceCount = new ArrayList<Integer>(ambulances);
		
		kMeansHospitals = new ArrayList<Hospital>(calculateKmeansLocations(allPeople));
        allHospitals = new ArrayList<Hospital>(kMeansHospitals);

        // PASSED IN WILL HAVE DIFFERENCE A.K.A kMeansHosp
        AssignAmbulancesToHospital(allHospitals, peopleClustered, ambulances);
        
        // Reassign Hosp ID, because design team was bad
        ReassignHospitalID(allHospitals, ambulanceCount);

        // Create Threads
        ArrayList<Thread> allThreads = new ArrayList<Thread>();
        for (int threadCount = 0; threadCount < HOSPITAL_NUMBER; threadCount++) {
            HospitalPathFinderThread pf = new HospitalPathFinderThread(allHospitals.
                    get(threadCount), peopleClustered.get(threadCount));
            Thread t = new Thread(pf);
            t.start();
            allThreads.add(t);
        }
        
        //Check if all threads are done
        while (true) {
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            boolean anyAlive = false;
            for (int ii = 0; ii < allThreads.size(); ii++) {
                anyAlive = anyAlive | allThreads.get(ii).isAlive();
            }
            if (anyAlive == false) {
                break;
            }
        }

        printResults();
        // If all complete, call printResults()
		//System.out.println("We have all of the cities");
        /*
        for (int i = 0; i < allHospitals.size(); i++) {
        	System.out.print(allHospitals.get(i));
        }
        */
	}
	
	private void ReassignHospitalID(ArrayList<Hospital>allHospitals, ArrayList<Integer>ambulanceCount) {
		ArrayList<Hospital> localHosp = new ArrayList<Hospital>(allHospitals);
		ArrayList<Integer> localAmbCount = new ArrayList<Integer>(ambulanceCount);
		int nextId = 0;
		
		for (int ambInd = 0; ambInd < localAmbCount.size(); ambInd++) {
			for (int hospInd = 0; hospInd < localHosp.size(); hospInd++) {
				int ambNumber = localAmbCount.get(ambInd);
				int tempHospAmbNumber = localHosp.get(hospInd).ambulances.size();
				if (ambNumber == tempHospAmbNumber) {
					if (!localHosp.get(hospInd).arranged) {
						localHosp.get(hospInd).arranged = true;
						localHosp.get(hospInd).setId(ambInd);
						
						for (int i = 0; i < ambNumber; i++) {
							localHosp.get(hospInd).ambulances.get(i).id = nextId;
							nextId++;
						}
						break;
					}
				}
			}
		}

		allHospitals = localHosp;
	}
	
	private ArrayList<Hospital> calculateKmeansLocations(ArrayList<Person> peeps) {
		ArrayList<Person> thePeople = new ArrayList<Person>(peeps);
		ArrayList<Hospital> hospitals = new ArrayList<Hospital>();
		ArrayList<Hospital> initialHospitals = new ArrayList<Hospital>();
		
		initialHospitals = calculateInitialHospitals(thePeople);
		hospitals = (ArrayList<Hospital>) initialHospitals.clone();
		
		int hospitalChange = Integer.MAX_VALUE;
		while (hospitalChange > HOSPITAL_CHANGE_MAX) {
		
			// Need to Assign Person to H
			ArrayList<ArrayList<Person>> assignedPeople = new ArrayList<ArrayList<Person>>();
			assignedPeople = AssignPersonToH(thePeople, hospitals);
			peopleClustered = new ArrayList<ArrayList<Person>>(assignedPeople);
			
			// Need to Update H locations
			// We are passing in hospitals and changing hospitals
			hospitalChange = UpdateHospitalLocations(hospitals, assignedPeople);
		}
		
		return hospitals;
	}
    
    // PASSED IN WILL HAVE A CHANGE
    public void AssignAmbulancesToHospital(ArrayList<Hospital> hospitals, 
            ArrayList<ArrayList<Person>> peepsClustered, 
            ArrayList<Integer> ambCount) {
        
        ArrayList<Hospital> localHosp = new ArrayList<Hospital>(hospitals);
        ArrayList<ArrayList<Person>> localPeepsClus = 
            new ArrayList<ArrayList<Person>>(peepsClustered);
        ArrayList<Integer> localAmbCount = new ArrayList<Integer>(ambCount);

        int totalAmbulanceIndex = 0;
        // Find Max Hospital
        // Find Max Ambulance Amount
        // Assign and delete
        // Repeat
        while(!localHosp.isEmpty()) {

            // Find Hotel with Max people left
            int maxPeople = 0;
            int localMaxHotelIndex = 0;
            int localMaxHotelId = 0;
            int maxHotelId = 0;

            for (int localHospIndex = 0; localHospIndex < localHosp.size(); 
                    localHospIndex++) {
                int tempPeople = localPeepsClus.get(localHospIndex).size(); 
                if (tempPeople > maxPeople) {
                    maxPeople = tempPeople;
                    localMaxHotelIndex = localHospIndex;
                    maxHotelId = localHosp.get(localMaxHotelIndex).getId();
                }
            } 

            // Find greatest Ambulance bunch 
            int maxAmbulances = 0;
            int localMaxAmbIndex = 0;
            for (int ambIndex = 0; ambIndex < localAmbCount.size(); ambIndex++) {
                int tempAmb = localAmbCount.get(ambIndex);
                if (tempAmb > maxAmbulances) {
                    maxAmbulances = tempAmb;
                    localMaxAmbIndex = ambIndex;
                }
            }

            Hospital MaxHotel = new Hospital(localHosp.get(localMaxHotelIndex));
            ArrayList<Integer> ambIds = new ArrayList<Integer>();

            // Create ArrayList of Id's to pass to Hospital
            for (int assignAmbIndex = 0; assignAmbIndex < maxAmbulances; assignAmbIndex++) {
                ambIds.add(totalAmbulanceIndex);
                totalAmbulanceIndex++;
            }

            // Update Original Hotel
            for (int hotelFindIndex = 0; hotelFindIndex < hospitals.size(); 
                    hotelFindIndex++) {
                if (hospitals.get(hotelFindIndex).getId() == maxHotelId) {
                    hospitals.get(hotelFindIndex).addAmbulancesWithId(ambIds); 
                }
            }

            // Remove local ambCount and Hotel
            localHosp.remove(localMaxHotelIndex);
            localAmbCount.remove(localMaxAmbIndex);
        }
    }

    public void printResults() {
        /*
    	for (int i = 0; i < allHospitals.size(); i++) {
            System.out.print(allHospitals.get(i));
        }
        */
        
        for (int i = 0; i < allHospitals.size(); i++) {
            for (int j = 0; j < allHospitals.size(); j++) {
            	if (allHospitals.get(j).getId() == i) {
            		System.out.print(allHospitals.get(j));
            		break;
            	}
            }
        }

        System.out.println("");

        for (int i = 0; i < allHospitals.size(); i++) {
            ArrayList<Ambulance> ambulances = 
                new ArrayList<Ambulance>(allHospitals.get(i).getAmbulances());

            for (int j = 0; j < ambulances.size(); j++) {
                System.out.print(ambulances.get(j));
            }
        }
    }
	
	private int UpdateHospitalLocations(ArrayList<Hospital> h, 
			ArrayList<ArrayList<Person>> allP) {
		int totalChange = 0;
		for (int hospIndex = 0; hospIndex < allP.size(); hospIndex++) {
			int totalPeopleAtHosp = allP.get(hospIndex).size();
			
			int totalX = 0;
			int totalY = 0;
			
			for (int personIndex = 0; personIndex < totalPeopleAtHosp; personIndex++)
			{
				totalX += allP.get(hospIndex).get(personIndex).getX();
				totalY += allP.get(hospIndex).get(personIndex).getY();
			}
			
            // If this is zero, we are setting locations to arbitrary.
            int averageX = 250;
            int averageY = 250;

            if (totalPeopleAtHosp != 0) {
			    averageX = totalX/totalPeopleAtHosp;
			    averageY = totalY/totalPeopleAtHosp;
            }

			Hospital replacementHosp = new Hospital(averageX, averageY, h.get(hospIndex).getId());
			
			// Updating
			totalChange += replacementHosp.distanceToHosp(h.get(hospIndex));
			h.set(hospIndex, replacementHosp);
		}
		
		return totalChange;
	}
	
	// We don't want to alter the parameters passed in
	private ArrayList<ArrayList<Person>> AssignPersonToH(ArrayList<Person> peeps, 
			ArrayList<Hospital> hosps) {
		
		ArrayList<Person> localPeeps = new ArrayList<Person>(peeps);
		ArrayList<Hospital> localHosps = new ArrayList<Hospital>(hosps);
	
		// Create 2D array
		ArrayList<ArrayList<Person>> outer = new ArrayList<ArrayList<Person>>();
		
		for (int i = 0; i < HOSPITAL_NUMBER; i++) {
			ArrayList<Person> inner = new ArrayList<Person>();
			outer.add(inner);
		}
		
		for (int pIndex = 0; pIndex < localPeeps.size(); pIndex++) {
			int minDistance = Integer.MAX_VALUE;
			
			Hospital resultHospital = new Hospital(-333, -333, -333);
			for (int hIndex = 0; hIndex < localHosps.size(); hIndex++) {
				int tempDistance = localPeeps.get(pIndex).distanceToHospital(
						localHosps.get(hIndex));
				
				if (tempDistance < minDistance) {
					resultHospital = new Hospital(localHosps.get(hIndex));
					minDistance = tempDistance;
				}				
			}
			
			int resHospId = resultHospital.getId();
			Person resultPerson = new Person(localPeeps.get(pIndex));
			outer.get(resHospId).add(resultPerson);			
		}
		return outer;		
	}
	
	// We are just going at 5 random people locations
	private ArrayList<Hospital> calculateInitialHospitals(ArrayList<Person> peeps) {
		ArrayList<Person> thePeople = new ArrayList<Person>(peeps);
		ArrayList<Hospital> hospitals = new ArrayList<Hospital>();
		
		for (int i = 0; i < HOSPITAL_NUMBER; i++) {
			int randomIndex = randomGenerator.nextInt(thePeople.size());
			int x = thePeople.get(randomIndex).getX();
			int y = thePeople.get(randomIndex).getY();
			Hospital h = new Hospital(x, y, i);
			hospitals.add(h);
		}
		return hospitals;
	}
}
