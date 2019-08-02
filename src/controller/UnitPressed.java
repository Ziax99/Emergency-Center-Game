package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.people.Citizen;
import model.units.Evacuator;
import model.units.Unit;
import view.GUI;

public class UnitPressed implements ActionListener {

	Unit u;
	CommandCenter command;
	
	public UnitPressed(CommandCenter command, Unit u) {
		// TODO Auto-generated constructor stub
		this.u = u;
		this.command = command;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		command.info.removeAll();
		command.info.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel b = new JLabel("<html>" + u.toString().replaceAll("\n", "<br>") + "</html>");
		b.setForeground(Color.white);
		b.setFont(new Font(Font.SERIF, 0, 20));
		JPanel panel = new JPanel();
//		panel.setBackground(GUI.bg);
//		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		panel.setPreferredSize(new Dimension(width, height));
		b.setPreferredSize(new Dimension(1000, 300));
		command.info.add(b);
		command.info.repaint();
		command.info.revalidate();
		
		if(u instanceof Evacuator) {
			JLabel label = new JLabel(" ");
			label.setPreferredSize(new Dimension(1000, 10));
			command.info.add(label);
			Evacuator e = (Evacuator)u;
			ArrayList<Citizen> passengers = e.getPassengers();
			String[]names = new String[passengers.size()+1];
			names[0] = "Passengers";
			for(int i = 1;i < names.length; i++) {
				names[i] = passengers.get(i-1).getName();
			}
			
			JComboBox cb = new JComboBox(names);
			cb.setPreferredSize(new Dimension(350, 25));
			cb.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						if(cb.getSelectedIndex()!=0) {
							citizenpressed cp = new citizenpressed(command, passengers.get(cb.getSelectedIndex()-1));
							cp.actionPerformed(null);
					}
						cb.setSelectedIndex(0);
					}
				}
			});
		//	System.out.println("here" + x.getText());
			command.info.add(cb);
		}
		
	}

}
