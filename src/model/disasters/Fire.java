package model.disasters;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;

public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {

		super(startCycle, target);

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\nDisaster: " + name() + super.toString() + "\nInitial Damage: " + 10 + "\nIncreasing: Fire Damage" + "\nDamage per Cycle: " + 10;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		ResidentialBuilding r = (ResidentialBuilding)getTarget();
		if(news != null) {
			JLabel label = new JLabel("Fire has catched a building .. its like hell here!");
			addp(label);
			JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\fire1.png"));
		}
		if(r.getDisaster() == null){
			super.strike();
			r.setFireDamage(10);
		}
		else if(r.getDisaster() instanceof GasLeak){
			if(r.getGasLevel() == 0){
				super.strike();
				r.setFireDamage(10);
			}
			else if(r.getGasLevel()<70){
				r.getDisaster().setActive(false);
				this.setActive(false);
				Collapse c =new Collapse(this.getStartCycle(),r);
				if(news != null) {
					c.setNews(news);
					c.setGrid(grid);
				}
				//				System.out.println("fire" + c.getNews() == null);
				r.setFireDamage(0);
				c.strike();
			}
			else if(r.getGasLevel() >= 70){
				r.getDisaster().setActive(false);
				this.setActive(false);
				r.setStructuralIntegrity(0);
			}
		}
	}

	public void cycleStep() {

		ResidentialBuilding b = (ResidentialBuilding)getTarget();
		b.setFireDamage(b.getFireDamage()+10);
	}
	public static String name() {
		return "Fire";
	}
}
