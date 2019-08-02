package model.disasters;

import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;

public class Injury extends Disaster {

	public Injury(int startCycle, Citizen target) {

		super(startCycle, target);

	}

	public String toString() {
		// TODO Auto-generated method stub
		Citizen b = (Citizen)getTarget();
		return "\nDisaster: " + name() + super.toString() + "\nInitial Damage: " + 30 + "\nIncreasing: Blood Loss Level" + "\nIncrease per Cycle: " + 10;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		Citizen c = (Citizen)getTarget();
		if(news != null) {
			JLabel label = new JLabel(c.getName() + " has been injured .. it looks bloody here!");
			addp(label);
			JButton b = grid[c.getLocation().getX()][c.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\injury.png"));
		}
		c.setBloodLoss(30);
	}

	public void cycleStep() {
		Citizen b = (Citizen)getTarget();
		b.setBloodLoss(b.getBloodLoss()+10);
	}

	public static String name() {
		return "Injury";
	}
}
