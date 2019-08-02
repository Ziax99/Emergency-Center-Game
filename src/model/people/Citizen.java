package model.people;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Disaster;
import model.events.SOSListener;
import model.events.WorldListener;

public class Citizen implements Rescuable, Simulatable {

	private CitizenState state;
	private Disaster disaster;
	private String name;
	private String nationalID;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	private Address location;
	private SOSListener emergencyService;
	private WorldListener worldListener;
	
	JButton[][]grid;
	
	JPanel news;
	
	boolean dead;
	
	public boolean unitHere;

	public void setNews(JPanel news) {
		this.news = news;
	}

	public JButton[][] getGrid() {
		return grid;
	}

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

	public Citizen(Address location, String nationalID, String name, int age, WorldListener worldListener) {

		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.worldListener = worldListener;

	}

	public void struckBy(Disaster d) {
		disaster = d;
		state = CitizenState.IN_TROUBLE;
		emergencyService.receiveSOSCall(this);
	}

	public void cycleStep() {
		
		if(bloodLoss > 0 && bloodLoss < 30)
			setHp(hp-5);
		else if(bloodLoss >= 30 && bloodLoss < 70)
			setHp(hp-10);
		else if(bloodLoss >= 70 && bloodLoss < 100)
			setHp(hp-15);
		if(toxicity > 0 && toxicity < 30)
			setHp(hp-5);
		else if(toxicity >= 30 && toxicity < 70)
			setHp(hp-10);
		else if(toxicity >= 70 && toxicity < 100)
			setHp(hp-15);
	}
	
	public ImageIcon createImage(String path) {
		BufferedImage bf;
		try {
			bf = ImageIO.read(new File(path));
			return new ImageIcon(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setDisaster(Disaster disaster) {
		this.disaster = disaster;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;



	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
		

		
		if(hp <= 0) {
			this.hp = 0;
			setState(CitizenState.DECEASED);
			if(!dead && news != null) {
				JLabel label = new JLabel("Unfortunately, Citizen " + name + " died .. we will pray for his spirit");
				label.setSize(800,50);
				label.setPreferredSize(new Dimension(800, 50));
				label.setForeground(Color.WHITE);
				label.setFont(new Font(Font.SERIF, 0, 18));
				news.add(label);
				dead = true;
			}
		}else if(hp >= 100) {
			this.hp = 100;
			setState(CitizenState.RESCUED);
		}
	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		this.bloodLoss = bloodLoss;
		if(bloodLoss <= 0) {
			this.bloodLoss = 0;
			state = CitizenState.RESCUED;//not sure
		}else if(bloodLoss >= 100) {
			this.bloodLoss = 100;
			setHp(0);
			state = CitizenState.DECEASED;
		}
	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		this.toxicity = toxicity;
		if(this.toxicity <= 0) {
			this.toxicity = 0;
			state = CitizenState.RESCUED;//not sure
		}else if(this.toxicity >= 100) {
			this.toxicity = 100;
			setHp(0);
			state = CitizenState.DECEASED;
		}
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}
	
	public String toString() {
		return "name: " + name + "\nstate: " + state + "\nhp: " + hp  + "\nblood loss: " + bloodLoss + "\ntoxicity: " + toxicity;
	}
}
