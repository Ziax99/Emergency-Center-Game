package model.disasters;

import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

public class Infection extends Disaster {

	public Infection(int startCycle, Citizen target) {

		super(startCycle, target);

	}

	public String toString() {
		// TODO Auto-generated method stub
		Citizen b = (Citizen)getTarget();
		return "\nDisaster: " + name() + super.toString() + "\nInitial Damage: " + 25 + "\nIncreasing: Toxicity Level" + "\nIncrease per Cycle: " + 15;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		Citizen c = (Citizen)getTarget();
		if(news != null) {
			JLabel label = new JLabel(c.getName() + " has an infection .. you should control it before he dies!");
			addp(label);
			JButton b = grid[c.getLocation().getX()][c.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\disease.png"));
		}
		c.setToxicity(25);
	}
	public void cycleStep() {
		Citizen b = (Citizen)getTarget();

		b.setToxicity(b.getToxicity()+15);
	}

	public static String name() {
		return "Infection";
	}
}
