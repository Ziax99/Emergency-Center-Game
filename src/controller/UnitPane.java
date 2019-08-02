package controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;

import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import view.GUI;
import view.UnitMouseListener;

public class UnitPane extends JTabbedPane implements ActionListener {

	ArrayList<Unit> emergencyUnits;

	Unit choosed;

	Tap ava;
	JPanel avaP;

	Tap res;
	JPanel resP;

	Tap treat;
	JPanel treatP;

	JButton[] icons;

	CommandCenter command;
	
	JButton prev;

	public UnitPane(ArrayList<Unit> emergencyUnits, CommandCenter command) throws IOException {

		this.emergencyUnits = emergencyUnits;

		this.command = command;

		setPreferredSize(new Dimension(850, 150));
		setMaximumSize(new Dimension(800, 150));

		ava = new Tap();
		avaP = ava.avaP;

		res = new Tap();
		resP = res.avaP;

		treat = new Tap();
		treatP = treat.avaP;

		icons = new JButton[emergencyUnits.size()];
		for (int i = 0; i < emergencyUnits.size(); i++) {
			Unit u = emergencyUnits.get(i);
			JButton b = new JButton();
			b.setToolTipText("<html>" + u.toString().replaceAll("\n", "<br>") + "</html>");
			b.putClientProperty("Unit", u);
			b.setPreferredSize(new Dimension(100, 100));
			//			b.setMaximumSize(new Dimension(100, 100));
			b.setFocusable(false);
			b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			b.setBackground(GUI.bg);
			if (u instanceof Ambulance) {
				b.addMouseListener(new UnitMouseListener("C:\\gui\\ambulance2.png"));
				b.setIcon(
						createImage("C:\\gui\\ambulance2.png"));
			}
			if (u instanceof FireTruck) {
				b.addMouseListener(new UnitMouseListener("C:\\gui\\fire-truck1.png"));
				b.setIcon(
						createImage("C:\\gui\\fire-truck1.png"));
			}
			if (u instanceof GasControlUnit) {
				b.addMouseListener(new UnitMouseListener("C:\\gui\\gas-truck2.png"));
				b.setIcon(
						createImage("C:\\gui\\gas-truck2.png"));
			}
			if (u instanceof Evacuator) {
				b.addMouseListener(new UnitMouseListener("C:\\gui\\police-car1.png"));
				b.setIcon(
						createImage("C:\\gui\\police-car1.png"));
			}
			if (u instanceof DiseaseControlUnit) {
				b.addMouseListener(new UnitMouseListener("C:\\gui\\diseaseUnit1.png"));
				b.setIcon(
						createImage("C:\\gui\\diseaseUnit1.png"));
			}
			b.addActionListener(this);
			b.addActionListener(new UnitPressed(command, u));
			icons[i] = b;
			avaP.add(b);
		}
		revalidate();
		repaint();
		addTab("IDLE Units", ava.avaC);
		addTab("Responding Units", res.avaC);
		addTab("Treating Units", treat.avaC);
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

	public void Cycle() {
		avaP.removeAll();
		resP.removeAll();
		treatP.removeAll();

		for (int i = 0; i < emergencyUnits.size(); i++) {
			Unit u = emergencyUnits.get(i);
			if (u.getState() == UnitState.IDLE)
				avaP.add(icons[i]);
			else if (u.getState() == UnitState.RESPONDING)
				resP.add(icons[i]);
			else
				treatP.add(icons[i]);
		}
		avaP.revalidate();
		resP.revalidate();
		treatP.revalidate();
		avaP.repaint();
		resP.repaint();
		treatP.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton) e.getSource();
		Unit u = (Unit) b.getClientProperty("Unit");

		if(choosed == u) {
			choosed = null;
			b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		else {
			if(prev != null)
				prev.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			b.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			choosed = u;
			prev = b;
		}

	}

		public Unit getChoosed() {
			return choosed;
		}

		public void setChoosed(Unit choosed) {
			this.choosed = choosed;
		}

	}