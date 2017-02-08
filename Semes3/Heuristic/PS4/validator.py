#!/usr/bin/env python

import sys, os, re, fileinput
from random import random
import copy

'''
Constraints
xloc between 1 and 500
yloc between 1 and 500
ttl between 1 and 200
'''

def distanceBetweenPlayers(player1,player2):
  return abs(player2.x - player1.x) + abs(player2.y-player1.y)
 
def locationMatch(player1,player2):
  return (player2.x == player1.x) and (player2.y == player1.y)

class Patient(object):
  def __init__(self,x,y,id,time_to_live):
    self.x = x
    self.y = y
    self.id = id
    self.time_to_live = time_to_live
  def __repr__(self):
    return '[Patient {id} at ({x},{y}), time_to_live={time_to_live}]'.format( x=self.x,y=self.y,id=self.id,time_to_live=self.time_to_live )
    
class Hospital(object):
  def __init__(self,x,y,id,ambulances):
    self.x = x
    self.y = y
    self.id = id
    self.ambulances = ambulances
  def __repr__(self):
    return '[Hospital {id} at ({x},{y}), ambulances={ambulances}]'.format( x=self.x,y=self.y,id=self.id,ambulances=self.ambulances )
    
class Ambulance(object):
  def __init__(self,x,y,id,patients,elapsed_time):
    self.x = x
    self.y = y
    self.id = id
    self.patients = patients
    self.elapsed_time = elapsed_time
  def __repr__(self):
    return '[Ambulance {id} at ({x},{y}), patients={patients}, elapsed_time={elapsed_time}]'.format( x=self.x,y=self.y,id=self.id,patients=self.patients,elapsed_time=self.elapsed_time )

# readData
def readData(fname):
  print >>sys.stderr, 'Reading data:', fname
  patients = {}
  hospitals = {}
  ambulances = {}
  mode = 0
  patient_id_counter = 1
  ambulance_id_counter = 1
  hospital_id_counter = 1
  for line in file(fname):
    line = line.strip().lower()
    if line.startswith("person") or line.startswith("people"):
      mode = 1
    elif line.startswith("hospital"):
      mode = 2
    elif line:
      if mode == 1:
        (x,y,ttl) = map(int, line.split(","))
        patients[patient_id_counter] = Patient(x,y,patient_id_counter,ttl)
        patient_id_counter += 1
      elif mode == 2:
        (namb,) = map(int, line.split(","))
        # create n ambulance objects
        # add these ambulance objects to a new hospital object
        tmp_ambs = []
        for n in range(namb):
          amb = Ambulance(-1,-1,ambulance_id_counter,[],0)
          ambulances[ambulance_id_counter] = amb
          tmp_ambs.append(amb)
          ambulance_id_counter += 1
        hospitals[hospital_id_counter] = Hospital(-1,-1,hospital_id_counter,tmp_ambs)
        hospital_id_counter += 1
  #print "Patients: ", patients
  #print "Hospitals: ", hospitals
  return (patients,hospitals,ambulances)


# readResults
def readResults(patients,hospitals,ambulances,fname):
  print >>sys.stderr, 'Reading results...'
  patients_saved = set()
  patients_picked = set()
  for line in file(fname):
    line = line.strip().lower()
    if line.startswith("hospital"):
      # check for validity of hospital locations
      (id_str,coord_str,amb_str) = line.split('|')
      # assert we are getting hospitals in order
      h_id = int(id_str.split(':')[1])
      hosp = hospitals[h_id]
      assert h_id == hosp.id, "h_id does not match hosp_id: {0}".format( [ h_id, hosp.id ] )
      (h_x,h_y,h_namb) = map(int, coord_str.split(","))
      # populate x,y on hospital objects
      hosp.x = h_x
      hosp.y = h_y
      # assert that hospital locations are within range
      assert 1 <= h_x <= 500, "hospital x-coordinate is out of range: {0}".format( h_x )
      assert 1 <= h_y <= 500, "hospital y-coordinate is out of range: {0}".format( h_y )
      # assert ambulance IDs matches expectations
      h_ambs = set( map(int,amb_str.split( ',' ) ) )
      assert h_ambs == set( [ a.id for a in hosp.ambulances ] ), "ambulance IDs do not match between expectations and results: {0}".format( [ h_ambs, set( [ a.id for a in hosp.ambulances ] ) ] )
      # set initial location on hospital's ambulances
      for a in hosp.ambulances:
        a.x = h_x
        a.y = h_y
        #print a
    elif line.startswith("ambulance"):
      # parse the ambulance line: 
      # format: Ambulance:{ambulance_id}|{start_x},{start_y}|{patient_id},{patient_x},{patient_y},{patient_ttl};<more_patients>|{end_x,end_y}
      # example: Ambulance:1|45,32|47,58,41,85;20,62,50,112|45,32
      (id_str,start_str,patients_str,end_str) = line.split('|')
      # assert the current ambulance's starting location matches the hospital
      a_id = int(id_str.split(':')[1])
      (start_x,start_y) = map(int, start_str.split(","))
      (end_x,end_y) = map(int, end_str.split(","))
      amb = ambulances[a_id]
      start_hosp = [ h for h in hospitals.values() if (h.x==start_x and h.y==start_y) ]
      if not start_hosp:
        raise Exception( "No hospital found for starting coordinates: {0}".format( start_str ) )
      else:
        start_hosp = start_hosp[0]
      assert amb in start_hosp.ambulances, "Ambulance not found in hospital: {0}".format( [ amb, start_hosp ] )
      # It takes a minute to go one block either north-south or east-west. 
      # Each hospital has an (x,y) location that you can determine when you see the distribution of victims. 
      # The ambulances need not return to the hospital where they begin. 
      # Each ambulance can carry up to four people. 
      # It takes one minute to load a person and one minute to unload up to four people. 
      pts = patients_str.split(';')
      assert len(pts) <= 4, "Cannot save more than 4 patients at once: {0}".format( line )
      # ok, start saving patients
      # at each new point, update ambulance.elapsed_time
      # first, remove the current ambulance from the start_hosp
      start_hosp.ambulances.remove(amb)
      
      for p_str in pts:
        (patient_id,patient_x,patient_y,patient_ttl) = map(int, p_str.split(","))
        p = patients[patient_id]
        #print "Inspecting patient: {0}".format( p )
        # assert that patient hasn't been picked up or saved 
        assert p not in patients_saved, "Patient has already been saved: {0}".format( p )
        assert p not in patients_picked, "Patient has already picked up: {0}".format( p )
        
        # check ttl versus amb.elapsed_time + current_move_distance + 1 (load time)
        new_elapsed_time = amb.elapsed_time + distanceBetweenPlayers(amb,p) + 1
        assert p.time_to_live >= new_elapsed_time, "new_elapsed_time exceeds patient's time_to_live: {0}".format( [ new_elapsed_time, p.time_to_live ] )
        # we picked up a valid patient
        # update elapsed_time, patients_picked, amb.patients, amb.x/y
        amb.elapsed_time = new_elapsed_time
        patients_picked.add(p)
        amb.patients.append(p)
        amb.x = p.x
        amb.y = p.y
        
      # ok, we've reached the end of the current ambulance trip
      # check endpoint is a valid hospital
      end_hosp = [ h for h in hospitals.values() if (h.x==end_x and h.y==end_y) ]
      assert end_hosp, "No hospital found for ending coordinates: {0}".format( end_str )
      end_hosp = end_hosp[0]
      # ok we have a valid endpoint (hospital)
      # update elapsed_time, patients_picked, amb.patients, amb.x/y
      new_elapsed_time = amb.elapsed_time + distanceBetweenPlayers(amb,end_hosp) + 1
      amb.elapsed_time = new_elapsed_time
      amb.x = end_hosp.x
      amb.y = end_hosp.y
  
      # let's check if anyone's still alive
      for p in amb.patients:
        if p.time_to_live >= amb.elapsed_time:
          # horray, we've saved someone!
          # update patients_saved
          print "Saved! Patient: {0}".format( p )
          patients_saved.add(p)
        else:
          # died en route :(
          print "Patient died en route: {0}".format( [ p, amb.elapsed_time ] )
          
      # ok, gear up for the next trip
      # set ambulance patients to empty list
      # add amb to end_hosp's ambulances
      amb.patients = []
      end_hosp.ambulances.append(amb)
  
  return patients_saved
      
      
# main
if __name__ == "__main__":
  if len(sys.argv) < 2:
    print 'usage: validator.py datafile [resultfile]'
    sys.exit(2)

  # read input file
  (patients, hospitals,ambulances) = readData(sys.argv[1])

  # read results file and validate output
  patients_saved = readResults(patients,hospitals,ambulances,sys.argv[2])
  print "Total Score (Patients Saved): {0}".format( len( patients_saved ) )
