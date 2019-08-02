package model.units;

import javax.swing.JLabel;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {
	
	public String getPath() {
		return path;
	}

	public String path = "C:\\gui\\fire-truck1.png";

	public FireTruck(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {

		super(unitID, location, stepsPerCycle, worldListener);

	}
	
	public boolean canTreat(Rescuable r) {
		ResidentialBuilding b = (ResidentialBuilding)r;
		if(b.getFireDamage() == 0 || b.getStructuralIntegrity() == 0)
			return false;
		return true;
	}
	
	public String toString() {
		return "\nFire Truck"  + "\nReduces Fire level by 10" + "\n" + super.toString();
	}
	
	public void cycleStep() {
		super.cycleStep();

	}
	
	public void treat() {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		if(r.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		}
		super.treat();
		r.setFireDamage(r.getFireDamage()-10);
		jobsDone();
	}
	
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
		ResidentialBuilding res = (ResidentialBuilding)getTarget();
		if(res!=null) // malhash lazma
			if(res.getFireDamage()!=0) 
				res.getDisaster().setActive(true);
	}
	
	public void jobsDone() {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		
		if(r.getFireDamage() == 0 ) {
			JLabel label = new JLabel("Unit " + getUnitID() + " has successfully put off the fire in the Building");
			addp(label);
		}
		
		if(r.getFireDamage() == 0 || r.getStructuralIntegrity() == 0)
			super.jobsDone();
	}
}
