package model.units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {

	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;


	private WorldListener worldListener;
	boolean toTarget = true;

	JPanel news;
	JButton[][]grid;
	public String path;

	ArrayList<Citizen>citizens;

	Rescuable oldt = target;

	public void setCitizens(ArrayList<Citizen> citizens) {
		this.citizens = citizens;
	}

	public String getPath() {
		return path;
	}

	public void setNews(JPanel news) {
		this.news = news;
	}

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

	public Unit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {

		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "";
		if(target == null)
			s = "Target: unspecified";
		if(target instanceof ResidentialBuilding)
			s = "Target: Building";
		if(target instanceof Citizen)
			s = "Target: " + (((Citizen)target).getName());
		String tloc = "";
		if(target != null)
			tloc = "Target's Location is at cell " + target.getLocation().getX() + " ," + target.getLocation().getY();

		return "\nUnit ID: " + unitID +"State: " + state + "\n"+ s + "\n" + tloc + "\nUnit's location is at cell " + location.getX() + " ," + location.getY() + "\nDistance To Target: " + distanceToTarget + "\nSteps Per Cycle: " + stepsPerCycle;
	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		//		try {
		if ((this instanceof MedicalUnit && r instanceof ResidentialBuilding)
				|| (!(this instanceof MedicalUnit) && r instanceof Citizen))
			throw new IncompatibleTargetException(this, r,
					"The unit you selected is not suitable for this target, please choose a valid target");
		if (!canTreat(r)) {
			throw new CannotTreatException(this, r,
					"The Unit cannot treat this target, please choose a valid target");
		}

		oldt = target;
		target = r;
		Address a = r.getLocation();
		Address b = getLocation();
		setDistanceToTarget(Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
		setState(UnitState.RESPONDING);
		toTarget = true;

	}

	public boolean canTreat(Rescuable r) {
		return true;
	}
	
	public void addp(JLabel label) {
		label.setSize(800,50);
		label.setPreferredSize(new Dimension(800, 50));
		label.setForeground(Color.WHITE);
		label.setFont(new Font(Font.SERIF, 0, 18));
		//System.out.println(news == null);
		news.add(label);
	}

	public void cycleStep() {
		if (state == UnitState.IDLE) {
			return;
		}

		if (distanceToTarget == 0)
			state = UnitState.TREATING;

		if (getState() == UnitState.TREATING && !(this instanceof Evacuator))
			treat();

		if (state == UnitState.RESPONDING) {

			//			Address old = location;

			setDistanceToTarget(distanceToTarget - stepsPerCycle);

			if(distanceToTarget == 0) {
				if(target instanceof Citizen) {
					Citizen c = (Citizen)target;
					c.unitHere = true;
					if(oldt != null) {
						Citizen old = (Citizen)oldt;
						//					System.out.println(old == null);
						old.unitHere = false;
					}
					JButton b = grid[c.getLocation().getX()][c.getLocation().getY()];
					b.setIcon(createImage(this.getPath()));
					
					JLabel label = new JLabel("Unit " + unitID + " arrived to " + c.getName());
					label.setSize(800,50);
					label.setPreferredSize(new Dimension(800, 50));
					label.setForeground(Color.WHITE);
					label.setFont(new Font(Font.SERIF, 0, 18));
					news.add(label);
				}
				if(target instanceof ResidentialBuilding) {
					ResidentialBuilding r = (ResidentialBuilding)target;
					r.unitHere = true;
					if(oldt != null) {
						ResidentialBuilding old = (ResidentialBuilding)oldt;
						old.unitHere = false;
					}
					JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];
					b.setIcon(createImage(this.getPath()));
					JLabel label = new JLabel("Unit " + unitID + " arrived to its target");
					label.setSize(800,50);
					label.setPreferredSize(new Dimension(800, 50));
					label.setForeground(Color.WHITE);
					label.setFont(new Font(Font.SERIF, 0, 18));
					news.add(label);
				}
			}
		}
		if (this instanceof Evacuator) {
			if (toTarget)
				returnTOTarget();
			else
				toBase();
		}

	}

	private void returnTOTarget() {
		if (state == UnitState.TREATING && toTarget) {
			if (location != target.getLocation())
				setDistanceToTarget(distanceToTarget - stepsPerCycle);
			else
				this.treat();
		}
	}

	private void toBase() {
		if (state == UnitState.TREATING && !toTarget) {
			Evacuator e = (Evacuator) this;
			if (location.getX() == 0 && location.getY() == 0) {
				toTarget = true;
				distanceToTarget = target.getLocation().getX() + target.getLocation().getY();
				for (Citizen c : e.getPassengers()) {
					if (c.getState() != CitizenState.DECEASED)
						c.setState(CitizenState.RESCUED);
					citizens.add(c);
					c.getWorldListener().assignAddress(c, 0, 0);
				}
				e.getPassengers().clear();
			} else
				e.setDistanceToBase(e.getDistanceToBase() - stepsPerCycle);
		}
	}

	public void treat() {
		state = UnitState.TREATING;
		//		System.out.println(target == null);
		target.getDisaster().setActive(false);
	}

	public void heal() {
	}

	public void jobsDone() {
		target = null;
		setState(UnitState.IDLE);
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		if (this.distanceToTarget <= 0) {
			this.distanceToTarget = 0;
			worldListener.assignAddress(this, target.getLocation().getX(), target.getLocation().getY());
		}
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
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

}
