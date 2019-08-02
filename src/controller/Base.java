package controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import model.people.Citizen;
import model.units.Unit;

public class Base implements ActionListener{
	
	ArrayList<Unit>units;

	CommandCenter command;
	
	ArrayList<Citizen>citizens;
	
	public Base(ArrayList<Unit> units, CommandCenter command, ArrayList<Citizen>citizens) {
		
		this.units = units;
		this.command = command;
		
		this.citizens = citizens;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		command.info.removeAll();
		command.info.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ArrayList<Unit> temp = new ArrayList<>();
		for(Unit u : units) {
			if(u.getLocation().getX() == 0 && u.getLocation().getY() == 0)
				temp.add(u);
		}
		
		String[]names = new String[temp.size()+1];
		names[0] = "Units";
		for(int i = 1;i < names.length; i++) {
			names[i] = temp.get(i-1).getUnitID();
		}
		
		JComboBox cb = new JComboBox(names);
		cb.setPreferredSize(new Dimension(400, 25));
		cb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(cb.getSelectedIndex()!=0) {
						UnitPressed up = new UnitPressed(command, temp.get(cb.getSelectedIndex()-1));
						up.actionPerformed(null);
				}
					cb.setSelectedIndex(0);
				}
			}
		});
		
		String[]names1 = new String[citizens.size()+1];
		names1[0] = "Citizens";
		for(int i = 1;i < names1.length; i++) {
			names1[i] = citizens.get(i-1).getName();
		}
		
		JComboBox ci = new JComboBox(names1);
		ci.setPreferredSize(new Dimension(400, 25));
		ci.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(ci.getSelectedIndex()!=0) {
						citizenpressed cp = new citizenpressed(command, citizens.get(ci.getSelectedIndex()-1));
						ci.actionPerformed(null);
				}
					ci.setSelectedIndex(0);
				}
			}
		});
		
		command.info.add(cb);
		command.info.add(ci);
		ci.revalidate();
		ci.repaint();
		command.info.repaint();
		command.info.revalidate();
		cb.repaint();
		cb.revalidate();
	}
	
	
	
	
}
