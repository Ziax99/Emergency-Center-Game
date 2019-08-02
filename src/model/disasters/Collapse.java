package model.disasters;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;

public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {

		super(startCycle, target);

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		ResidentialBuilding b = (ResidentialBuilding)getTarget();
		return "\nDisaster: " + name() + super.toString() + "\nInitial Damage: " + 10 + "\nReducing: Foundation Damage" + "\nDamage per Cycle: " + 10;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		JLabel label = new JLabel("A Building has collapsed .. hurry up to take the citizens out!");
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		if(news != null) {
			addp(label);
			JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\collapse1.png"));
			r.setFoundationDamage(10);
		}
	}

	public void cycleStep() {
		ResidentialBuilding b = (ResidentialBuilding)getTarget();
		b.setFoundationDamage(b.getFoundationDamage()+10);
	}
	public static String name() {
		return "Collapse";
	}
}
