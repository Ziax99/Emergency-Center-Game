package model.disasters;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;

public class GasLeak extends Disaster {

	public GasLeak(int startCycle, ResidentialBuilding target) {

		super(startCycle, target);

	}

	public String toString() {
		// TODO Auto-generated method stub
		ResidentialBuilding b = (ResidentialBuilding)getTarget();
		return "\nDisaster: " + name() + super.toString() + "\nInitial Damage: " + 10 + "\nIncreasing: Gas Level" + "\nIncrease per Cycle: " + 15;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		if(news != null) {
			JLabel label = new JLabel("A building is now full of gas .. citizens are now at risk of death :(");
			addp(label);
			JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\gas.png"));
		}
		if(r.getDisaster() == null){
			super.strike();
			r.setGasLevel(10);
		}
		else if(r.getDisaster() instanceof Fire) {
			r.getDisaster().setActive(false);
			this.setActive(false);
			Collapse c =new Collapse(this.getStartCycle(),r);
			if(news != null) {
				c.setNews(news);
				c.setGrid(grid);
			}
			//			System.out.println("gas"+(c.getNews() == null));
			r.setFireDamage(0);
			c.strike();
		}
	}
	public void cycleStep() {
		ResidentialBuilding b = (ResidentialBuilding)getTarget();
		b.setGasLevel(b.getGasLevel()+15);
	}
	public static String name() {
		return "Gas Leak";
	}
}
