package model.units;

import javax.swing.JLabel;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {
	
	public String path = "C:\\gui\\gas-truck2.png";

	public String getPath() {
		return path;
	}

	public GasControlUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {

		super(unitID, location, stepsPerCycle, worldListener);

	}
	
	public boolean canTreat(Rescuable r) {
		ResidentialBuilding b = (ResidentialBuilding)r;
		if(b.getGasLevel() == 0 || b.getStructuralIntegrity() == 0)
			return false;
		return true;
	}
	
	public String toString() {
		return "\nGas Control Unit"  + "\nReduces Gas level by 10" + "\n" + super.toString();
	}
	
	public void cycleStep() {
		super.cycleStep();

	}
	
	public void treat() {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		super.treat();
		r.setGasLevel(r.getGasLevel()-10);
		jobsDone();
	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
		ResidentialBuilding res = (ResidentialBuilding)getTarget();
		if(res.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		}
		if(res!=null) {	
			if(res.getGasLevel()!=0) 
				res.getDisaster().setActive(true);
		}
	}
	public void jobsDone() {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		if(r.getGasLevel() == 0 ) {
			JLabel label = new JLabel("Unit " + getUnitID() + " has successfully tended to the gas level of the Building");
			addp(label);
		}
		if(r.getGasLevel() == 0 || r.getStructuralIntegrity() == 0)
			super.jobsDone();
	}
}
	

