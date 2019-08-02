package model.infrastructure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Disaster;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable, Simulatable {

	private Address location;
	private int structuralIntegrity;
	private int fireDamage;
	private int gasLevel;
	private int foundationDamage;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	private SOSListener  emergencyService;
	
	JButton[][]grid;
	
	boolean coll;

	JPanel news;
	
	public boolean unitHere;

	public JButton[][] getGrid() {
		return grid;
	}

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

	public ResidentialBuilding(Address location) {

		this.location = location;
		this.structuralIntegrity = 100;
		occupants = new ArrayList<Citizen>();

	}
	
	public void struckBy(Disaster d) {
		disaster = d;
		emergencyService.receiveSOSCall(this);
	}
	
	public void cycleStep() {
		if(foundationDamage>0) 
			setStructuralIntegrity(structuralIntegrity-(int)(Math.random()*6+5));
		if(fireDamage>0 && fireDamage<30) 
			setStructuralIntegrity(structuralIntegrity-3);
		else if(fireDamage>=30 && fireDamage< 70)
			setStructuralIntegrity(structuralIntegrity-5);
		else if (fireDamage>=70)//not sure
			setStructuralIntegrity(structuralIntegrity-7);
	}
	
	public void setNews(JPanel news) {
		this.news = news;
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {
		this.structuralIntegrity = structuralIntegrity;
		

		
		if(structuralIntegrity >= 100)
			this.structuralIntegrity = 100;
		if(structuralIntegrity <= 0) {
			this.structuralIntegrity = 0;
			for(Citizen c : occupants) 
				c.setHp(0);
			if(!coll && news != null) {
				JLabel label = new JLabel("Building has been destroyed .. what a mess here :(");
				label.setSize(800,50);
				label.setPreferredSize(new Dimension(800, 50));
				label.setForeground(Color.WHITE);
				label.setFont(new Font(Font.SERIF, 0, 18));
				news.add(label);
				coll = true;
			}
		}
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) { // we didn't handle fire if 100 and relation between fire and gas
		this.fireDamage = fireDamage;
		if(fireDamage > 100)
			this.fireDamage = 100;
		else if(fireDamage < 0)
			this.fireDamage = 0;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasLevel) {
		this.gasLevel = gasLevel;
		if(gasLevel < 0)
			this.gasLevel = 0;
		else if(gasLevel >= 100) {
			this.gasLevel = 100;
			for(Citizen c : occupants)
				c.setHp(0);
		}
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
		if(foundationDamage >= 100) {
			this.foundationDamage = 100;
			setStructuralIntegrity(0);
		}
	}
	public String toString() {
		return "structuralIntegrity : "+getStructuralIntegrity()+"\nFireDamage : "+getFireDamage()+"\nGasLevel : "+getGasLevel()+"\nFoundationDamage :"
				+getFoundationDamage();
	}
	public Address getLocation() {
		return location;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public Disaster getDisaster() {
		return disaster;
	}
	
	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

}
