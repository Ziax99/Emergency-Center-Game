package model.units;

import java.util.ArrayList;

import simulation.Address;
import model.events.WorldListener;
import model.people.Citizen;

public abstract class PoliceUnit extends Unit {

	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;

	public PoliceUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {

		super(unitID, location, stepsPerCycle, worldListener);
		passengers = new ArrayList<Citizen>();
		this.maxCapacity = maxCapacity;

	}
	
	public String toString() {
		return  "\nEvacuator" +"\nMax Capacity:" + maxCapacity + "\nDistance To Base" + distanceToBase + "\n" + super.toString();
	}

	public int getDistanceToBase() {
		return distanceToBase;
	}

	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
		if(this.distanceToBase <= 0) {
			this.distanceToBase = 0;
			getWorldListener().assignAddress(this, 0, 0);
		}
	}

	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

}
