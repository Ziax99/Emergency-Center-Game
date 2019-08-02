package model.disasters;

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

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable {

	private int startCycle;
	private Rescuable target;
	private boolean active;
	JPanel news;
	JButton[][] grid;

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

	public Disaster(int startCycle, Rescuable target) {

		this.startCycle = startCycle;
		this.target = target;

	}
	
	public String toString() {
		String type = "";
		if(target instanceof ResidentialBuilding)
			type = "Building";
		else
			type = ((Citizen)target).getName();
		
		String activity = "";
		if(active)
			activity = "Active Disaster";
		else
			activity = "InActive Disaster";
		
		return "\nStart Cycle: " + startCycle + "\nTarget: " + type + "\n" + activity;
	}public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		
			if(target instanceof Citizen && ((Citizen) target).getState() == CitizenState.DECEASED)
				throw new CitizenAlreadyDeadException(this);
			
			if(target instanceof ResidentialBuilding && ((ResidentialBuilding) target).getStructuralIntegrity() == 0)
				throw new BuildingAlreadyCollapsedException(this);
			
			if(target.getDisaster() != null)
				target.getDisaster().setActive(false);
			
			active = true;
			target.struckBy(this);

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

	public void addp(JLabel label) {
		label.setSize(800,50);
		label.setPreferredSize(new Dimension(800, 50));
		label.setForeground(Color.WHITE);
		label.setFont(new Font(Font.SERIF, 0, 18));
		//System.out.println(news == null);
		if(news != null)
			news.add(label);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStartCycle() {
		return startCycle;
	}

	public Rescuable getTarget() {
		return target;
	}
	
	public JPanel getNews() {
		return news;
	}

	public void setNews(JPanel news) {
		this.news = news;
	}
	
	public static String name() {
		return "Disaster";
	}
}
