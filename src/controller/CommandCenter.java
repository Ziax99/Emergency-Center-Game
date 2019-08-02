package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import TCP.ChatScreen;
import TCP.Server;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.GUI;
import view.GameOver;
import view.GridMouseListener;
import view.StartMenu;
import view.titleBar;

public class CommandCenter implements SOSListener, GUIListener, ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;

	ArrayList<Citizen> citizens;
	
	int x = 0;

	GUI gui;

	JPanel display;

	JButton[][] grid;

	JPanel panel;
	JPanel right;
	JPanel info;

	JPanel left;

	public NewsFeed feed;
	JPanel news;

	int currentCycle;

	titleBar title;

	UnitPane tapped;
	
	public ChatScreen clientScreen;

//	Server server;
	
	public CommandCenter() throws Exception {

		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		emergencyUnits = engine.getEmergencyUnits();

		citizens = new ArrayList<>();


		engine.setCommand(this);

		for(Unit u : emergencyUnits)
			u.setCitizens(citizens);

		new StartMenu(this);
		
		
		// gui.pack();

//		nextCycle();

	}
	
	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public void GUIStuff() throws IOException {
		display = gui.getDisplay();
		grid = new JButton[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				grid[i][j] = new JButton();
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 2));
				grid[i][j].setBackground(GUI.bg);
				grid[i][j].setFocusable(false);
				grid[i][j].addMouseListener(new GridMouseListener());
				display.add(grid[i][j]);

			}
		grid[0][0].setIcon(createImage("C:\\gui\\base.png"));
		grid[0][0].addActionListener(new Base(emergencyUnits, this, citizens));
		panel = gui.getPanel();
		right = gui.getRight();
		title = gui.gettitle();
		left = gui.getLeft();

		tapped = new UnitPane(emergencyUnits, this);

		right.add(tapped);
		right.add(display);

		info = gui.getInfo();
		info.setLayout(new FlowLayout(FlowLayout.LEFT));
		info.setBorder(BorderFactory.createTitledBorder(null, "Info", 0, 0, new Font(Font.SERIF, 0, 20), Color.WHITE));

		feed = new NewsFeed(this);
		news = feed.panel;
		left.add(feed.container);
//		JPanel temp = new JPanel();
//		temp.setMaximumSize(new Dimension(1000, 20));
//		temp.setBackground(GUI.bg);
//		left.add(temp);
		engine.setNews(news);
		engine.setGrid(grid);
		engine.setIcons();
		engine.insert(news);

		display.repaint();
		news.revalidate();
		title.revalidate();
		panel.revalidate();
		right.revalidate();
		display.revalidate();
		gui.revalidate();
	}
	
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public void nextCycle() throws IOException {

		currentCycle++;

		JLabel label = new JLabel("Cycle " + currentCycle);
		label.setSize(800,50);
		label.setPreferredSize(new Dimension(800, 50));
		label.setForeground(Color.WHITE);
		label.setFont(new Font(Font.SERIF, 0, 18));
		news.add(label);

		label = new JLabel("---------------");
		label.setSize(800,50);
		label.setPreferredSize(new Dimension(800, 50));
		label.setForeground(Color.WHITE);
		label.setFont(new Font(Font.SERIF, 0, 18));
		news.add(label);

		if(currentCycle == 1) {
			label = new JLabel("Game Starts .. Be ready");
			label.setSize(800,50);
			label.setPreferredSize(new Dimension(800, 50));
			label.setForeground(Color.WHITE);
			label.setFont(new Font(Font.SERIF, 0, 18));
			news.add(label);
		}
		
		if (engine.checkGameOver()) {
			gui.dispose();
			new GameOver(engine.calculateCasualties(), engine.getTotal());
			return;

		}
		try {
			engine.nextCycle();
		} catch (BuildingAlreadyCollapsedException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);

		} catch (CitizenAlreadyDeadException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
		}

		if(clientScreen != null && clientScreen.isVisible()) {
			clientScreen.nextCycle();
		}
		
		tapped.Cycle();

		title.nextCycle(currentCycle, engine.calculateCasualties());

		

		for (ResidentialBuilding r : visibleBuildings) {

			JButton b = grid[r.getLocation().getX()][r.getLocation().getY()];

			if(!r.unitHere) {
				if (r.getStructuralIntegrity() == 0)
					b.setIcon(createImage("C:\\gui\\broken.png"));
				if (r.getFireDamage() == 0 && r.getGasLevel() == 0 && r.getFoundationDamage() == 0)
					b.setIcon(createImage("C:\\gui\\building.png"));
			}
			b.setToolTipText("<html>" + r.toString().replaceAll("\n", "<br>") + "</html>");
		}

		for (Citizen c : visibleCitizens) {
			JButton b = grid[c.getLocation().getX()][c.getLocation().getY()];
			if(!c.unitHere) {
				if (c.getState() == CitizenState.RESCUED)
					b.setIcon(createImage("C:\\gui\\citizen.png"));
				if (c.getState() == CitizenState.DECEASED) {
					b.setIcon(createImage("C:\\gui\\dead.png"));
				}
			}
		}

		title.revalidate();
		info.revalidate();
		panel.revalidate();
		display.revalidate();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (tapped.getChoosed() == null)
			return;

		JButton b = (JButton) e.getSource();

		try {
			if (b.getClientProperty("Building") == null) {
				Citizen c = (Citizen) b.getClientProperty("Citizen");
				tapped.getChoosed().respond(c);
			} else {
				ResidentialBuilding r = (ResidentialBuilding) b.getClientProperty("Building");
				tapped.getChoosed().respond(r);
			}
			tapped.setChoosed(null);
			tapped.prev.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			// b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		} catch (CannotTreatException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
			tapped.prev.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			tapped.setChoosed(null);
		} catch (IncompatibleTargetException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "WARNING", JOptionPane.ERROR_MESSAGE);
			tapped.prev.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			tapped.setChoosed(null);
		}

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

	public static void playMusic(String path) {
		try {
			File music = new File(path);
			if (music.exists()) {

				AudioInputStream audio = AudioSystem.getAudioInputStream(music);
				clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			} else {
			}

		} catch (Exception e) {

		}
	}
	static public Clip clip;


	public GUI getGui() {
		return gui;
	}

	public static void main(String[] args) {
		try {
			playMusic("C:\\gui\\audio\\audio.wav");
			new Server();
			new CommandCenter();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
