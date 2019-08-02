package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public abstract class MedicalUnit extends Unit {

	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {

		super(unitID, location, stepsPerCycle, worldListener);
		healingAmount = 10;
		treatmentAmount = 10;

	}

	public int getHealingAmount() {
		return healingAmount;
	}

	public void setHealingAmount(int healingAmount) {
		this.healingAmount = healingAmount;
	}

	public int getTreatmentAmount() {
		return treatmentAmount;
	}

	public void heal() {
		Citizen c = (Citizen) getTarget();

		c.setHp(c.getHp() + healingAmount);

		if (c.getHp() == 100)
			jobsDone();
//		 if (c.getHp() == 100) {
////			 c.setState(CitizenState.RESCUED);
//			 setState(UnitState.IDLE);
//		 
//		 }
	}

}
