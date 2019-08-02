package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {
	
	public String getPath() {
		return path;
	}

	public String path = "C:\\gui\\police-car1.png";

	public Evacuator(String unitID, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {

		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	public boolean canTreat(Rescuable r) {//should check capacity and if the diseaster is collapse?
		ResidentialBuilding b = (ResidentialBuilding) r;
		int n = 0;
		for (int i = 0; i < b.getOccupants().size(); i++)
			if (b.getOccupants().get(i).getState() != CitizenState.DECEASED) {
				n++;
			}
		if ( !(b.getDisaster() instanceof Collapse))
			return false;
		return true;
	}

	public void treat() {
		ResidentialBuilding r = (ResidentialBuilding) getTarget();
		
		if(r.getStructuralIntegrity() == 0 || r.getOccupants().size() == 0) {
			jobsDone();
			return;
		}
		
		int n = 0;
		for (int i = 0; i < r.getOccupants().size(); i++)
			if (r.getOccupants().get(i).getState() != CitizenState.DECEASED) {
				n++;
			}
		int freeSpace = getMaxCapacity() - getPassengers().size();
		int size = Math.min(freeSpace, n);
		for (int i = 0; i < size; i++) {
			Citizen c = r.getOccupants().get(i);
			if (c.getState() != CitizenState.DECEASED) {
				r.getOccupants().remove(i);
				getPassengers().add(c);
				i--;
				size--;
			}
		}

		this.setDistanceToBase((this.getLocation().getX()) + (this.getLocation().getY()));
		jobsDone();
		toTarget = false;
	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}

	public void jobsDone() {
		ResidentialBuilding r = (ResidentialBuilding) getTarget();
		Evacuator e = (Evacuator) this;
		int n = 0;
		for (int i = 0; i < r.getOccupants().size(); i++)
			if (r.getOccupants().get(i).getState() != CitizenState.DECEASED) {
				n++;
			}

		if (n == 0 && getPassengers().size() == 0 && toTarget && getLocation() == r.getLocation())
			super.jobsDone();
	}

}
