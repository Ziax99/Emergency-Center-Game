package model.units;

import javax.swing.JLabel;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public String path = "C:\\gui\\ambulance2.png";
	
	public Ambulance(String unitID, Address location, int stepsPerCycle ,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle, worldListener);

	}

	public boolean canTreat(Rescuable r) {
		Citizen c = (Citizen)r;
		if((c.getBloodLoss() == 0 && c.getHp() == 100) || c.getState() == CitizenState.DECEASED)
			return false;
		return true;
	}
	
	public String getPath() {
		return path;
	}

	public String toString() {
		return "\nAmbulance"  + "\nReduces blood loss by " + getTreatmentAmount() + "\nIncreases HP by " + getHealingAmount() + "\n" + super.toString();
	}

	public void cycleStep() {
		super.cycleStep();
	}

	public void treat() {
		
		Citizen c = (Citizen)getTarget();
		if(c.getHp() == 0) {
			jobsDone();
			return;
		}

		super.treat();
		if(c.getBloodLoss() == 0)
			heal();
		else {
			c.setBloodLoss(c.getBloodLoss()-getTreatmentAmount());
			if(c.getBloodLoss() == 0)
				c.setState(CitizenState.RESCUED);
		}
	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
		Citizen c = (Citizen)getTarget();
		if(c!=null) {
			if(c.getBloodLoss()!=0)
				c.getDisaster().setActive(true);
		}
	}
	public void jobsDone() {
		Citizen c = (Citizen)getTarget();
		//		if(c.getName() == "Test1")
//		System.out.println(c.getState());
		if((c.getBloodLoss() == 0 && c.getHp() == 100)) {
			JLabel label = new JLabel("Unit " + getUnitID() + " has successfully treated " + c.getName());
			addp(label);
		}
		if((c.getBloodLoss() == 0 && c.getHp() == 100) || c.getState() == CitizenState.DECEASED) {
			super.jobsDone();
		}
	}
}
