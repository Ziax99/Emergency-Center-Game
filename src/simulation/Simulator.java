package simulation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.BuildingPressed;
import controller.CommandCenter;
import controller.citizenpressed;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;

public class Simulator implements WorldListener {

	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;
	JPanel news;
	JButton[][]grid;
	CommandCenter command;
	
	public void setCommand(CommandCenter command) {
		this.command = command;
	}

	public JButton[][] getGrid() {
		return grid;
	}

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

	public JPanel getNews() {
		return news;
	}

	public void setNews(JPanel news) {
		this.news = news;
	}

	public Simulator(SOSListener emergencyService) throws Exception {

		buildings = new ArrayList<ResidentialBuilding>();
		citizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		plannedDisasters = new ArrayList<Disaster>();
		executedDisasters = new ArrayList<Disaster>();
		this.emergencyService = emergencyService;
		world = new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
		}

		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");

		for (int i = 0; i < buildings.size(); i++) {

			ResidentialBuilding building = buildings.get(i);
			for (int j = 0; j < citizens.size(); j++) {

				Citizen citizen = citizens.get(j);
				if (citizen.getLocation() == building.getLocation())
					building.getOccupants().add(citizen);

			}
		}

	}

	public void setIcons() {
		for(Citizen c : citizens) {
			JButton b = grid[c.getLocation().getX()][c.getLocation().getY()];
			
			boolean flag = true;
			for(ResidentialBuilding r : buildings)
				if(r.getLocation() == c.getLocation())
					flag = false;
			
			if(flag) {
				b.addActionListener(new citizenpressed(command, c));
				c.setGrid(grid);
				b.addActionListener(command);
				b.putClientProperty("Citizen", c);
				b.setIcon(createImage("C:\\gui\\citizen.png"));
				b.setToolTipText("<html>" + c.toString().replaceAll("\n", "<br>") + "</html>");
			}

		}
		for(ResidentialBuilding r : buildings) {
			JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];
			b.setIcon(createImage("C:\\gui\\building.png"));
			b.addActionListener(new BuildingPressed(command, r));
			r.setGrid(grid);
//			b.addActionListener(command);
//			b.putClientProperty("Building",r);
			b.setToolTipText("<html>" + r.toString().replaceAll("\n", "<br>") + "</html>");

		}
	}
	
	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

	public ImageIcon createImage(String path) {
		BufferedImage bf;
		try {
			bf = ImageIO.read(new File(path));
			return new ImageIcon(bf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	public boolean checkGameOver() {
		if (!plannedDisasters.isEmpty())
			return false;
		for (Disaster d : executedDisasters)
			if (d.isActive()) {
				if(d.getTarget() instanceof Citizen) {
					Citizen c = (Citizen)d.getTarget();
					if(c.getState() != CitizenState.DECEASED)
						return false;
				}else if(d.getTarget() instanceof ResidentialBuilding) {
					ResidentialBuilding r = (ResidentialBuilding)d.getTarget();
					if(r.getStructuralIntegrity() != 0)
						return false;
				}
			}

		for (Unit u : emergencyUnits)
			if (u.getState() != UnitState.IDLE)
				return false;
		return true;
	}
	
	public void insert(JPanel news) {
		for(Disaster d : plannedDisasters) {
			d.setNews(news);
			d.setGrid(grid);
		}
		for(ResidentialBuilding b : buildings)
			b.setNews(news);
		for(Citizen c : citizens)
			c.setNews(news);
		for(Unit u : emergencyUnits) {
			u.setGrid(grid);
			u.setNews(news);
		}
	}

	public int calculateCasualties() {
		int counter = 0;
		for (Citizen c : citizens)
			if (c.getState() == CitizenState.DECEASED)
				counter++;
		return counter;
	}

	public void nextCycle() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		//		if(checkGameOver())//not sure
		//			return;
		currentCycle++;
		ArrayList<Disaster> removed = new ArrayList<>();
		for (int i = 0; i < plannedDisasters.size(); i++) {
			Disaster d = plannedDisasters.get(i);
			if (d.getStartCycle() == currentCycle) {
				Rescuable r = d.getTarget();
				d.strike();
				removed.add(d);
				if (r.getDisaster() instanceof Collapse) {
					executedDisasters.add(r.getDisaster());
				}else 
					executedDisasters.add(d);
			}
		}
		for(Disaster d : removed)
			for(int i = 0;i < plannedDisasters.size(); i++)
				if(d == plannedDisasters.get(i)) {
					plannedDisasters.remove(i);
					break;
				}

		for (Unit u : emergencyUnits) {
			u.cycleStep();
		}

		for (Disaster d : executedDisasters)
			if (d.getStartCycle() != currentCycle && d.isActive())
				d.cycleStep();

		for (ResidentialBuilding r : buildings) {
			if(r.getFireDamage() == 100) {
				Collapse c = new Collapse(currentCycle, r);
				c.setNews(news);
				c.setGrid(grid);
//				System.out.println();
//				System.out.println("sim   "+(c.getNews()==null));
				r.setFireDamage(0);
				c.strike();
				executedDisasters.add(c); 
			}
			r.cycleStep();
		}

		for (Citizen c : citizens)
			c.cycleStep();
	}

	private void loadUnits(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			String id = info[1];
			int steps = Integer.parseInt(info[2]);

			switch (info[0]) {

			case "AMB":
				emergencyUnits.add(new Ambulance(id, world[0][0], steps, this));
				break;

			case "DCU":
				emergencyUnits.add(new DiseaseControlUnit(id, world[0][0], steps, this));
				break;

			case "EVC":
				emergencyUnits.add(new Evacuator(id, world[0][0], steps, this, Integer.parseInt(info[3])));
				break;

			case "FTK":
				emergencyUnits.add(new FireTruck(id, world[0][0], steps, this));
				break;

			case "GCU":
				emergencyUnits.add(new GasControlUnit(id, world[0][0], steps, this));
				break;

			}

			line = br.readLine();
		}

		br.close();
	}

	private void loadBuildings(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			ResidentialBuilding r = new ResidentialBuilding(world[x][y]);
			r.setEmergencyService(emergencyService);
			buildings.add(r);

			line = br.readLine();

		}
		br.close();
	}

	private void loadCitizens(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			String id = info[2];
			String name = info[3];
			int age = Integer.parseInt(info[4]);
			Citizen c = new Citizen(world[x][y], id, name, age, this);
			c.setEmergencyService(emergencyService);
			citizens.add(c);

			line = br.readLine();

		}
		br.close();
	}

	private void loadDisasters(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			int startCycle = Integer.parseInt(info[0]);
			ResidentialBuilding building = null;
			Citizen citizen = null;

			if (info.length == 3)
				citizen = getCitizenByID(info[2]);
			else {

				int x = Integer.parseInt(info[2]);
				int y = Integer.parseInt(info[3]);
				building = getBuildingByLocation(world[x][y]);

			}

			switch (info[1]) {

			case "INJ":
				plannedDisasters.add(new Injury(startCycle, citizen));
				break;

			case "INF":
				plannedDisasters.add(new Infection(startCycle, citizen));
				break;

			case "FIR":
				plannedDisasters.add(new Fire(startCycle, building));
				break;

			case "GLK":
				plannedDisasters.add(new GasLeak(startCycle, building));
				break;
			}

			line = br.readLine();
		}
		br.close();
	}

	private Citizen getCitizenByID(String id) {

		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID().equals(id))
				return citizens.get(i);
		}

		return null;
	}

	private ResidentialBuilding getBuildingByLocation(Address location) {

		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation() == location)
				return buildings.get(i);
		}

		return null;
	}



	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public void assignAddress(Simulatable sim, int x, int y) {
		if (sim instanceof Citizen)
			((Citizen) sim).setLocation(world[x][y]);
		else if (sim instanceof Unit)
			((Unit) sim).setLocation(world[x][y]);
	}
	
	public int getTotal() {
		return citizens.size();
	}
}
