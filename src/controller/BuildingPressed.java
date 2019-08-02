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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import view.GUI;

public class BuildingPressed implements ActionListener{
	CommandCenter command;
	ResidentialBuilding b;
//	JComboBox cb;
	JButton[] occupants ;
	ArrayList<Citizen> occ;
	public BuildingPressed(CommandCenter command , ResidentialBuilding b) {
		this.command = command;
		this.b = b;
		occ = b.getOccupants();
		occupants = new JButton[occ.size()];
		for(int i = 0 ;i<occupants.length;i++) {
			occupants[i] = new JButton(occ.get(i).getName());
			occupants[i].setBackground(GUI.bg);
			occupants[i].setForeground(Color.WHITE);
			occupants[i].addActionListener(command);
			occupants[i].putClientProperty("Citizen", occ.get(i));
			occupants[i].addActionListener(new citizenpressed(command, occ.get(i)));
			occupants[i].setFocusable(false);
			occupants[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			occupants[i].setPreferredSize(new Dimension(100, 50));
		}

	}
	public void actionPerformed(ActionEvent arg0) {
		command.info.removeAll();
		command.info.setLayout(new BoxLayout(command.info, BoxLayout.X_AXIS));
		JPanel left = new JPanel();
		left.setBackground(GUI.bg);
		left.setLayout(new FlowLayout(FlowLayout.LEFT));
		left.setMaximumSize(new Dimension(200, 500));
		JLabel x = new JLabel("<html>" + b.toString().replaceAll("\n", "<br>") 
				+ "<br>" +(b.getDisaster() == null? "" : b.getDisaster().toString().replaceAll("\n", "<br>")) + "</html>");
		x.setForeground(Color.WHITE);
		int height = b.getDisaster() == null ? 120 : 340;
		x.setPreferredSize(new Dimension(1000, height));
		x.setFont(new Font (Font.SERIF,Font.PLAIN,20));
		left.add(x);
		command.info.add(left);
		
		JPanel right = new JPanel();
		right.setLayout(new FlowLayout());
		right.setBackground(GUI.bg);
		right.setMaximumSize(new Dimension(300, 500));
		
		JPanel temp = new JPanel();
		temp.setBackground(new Color(0, 0, 0, 0));
		temp.setMaximumSize(new Dimension(600, 250));
		JButton respond = new JButton("Save Building");
		respond.setBackground(GUI.bg);
		respond.setForeground(Color.white);
		respond.setPreferredSize(new Dimension(200, 70));
		respond.addActionListener(command);
		respond.putClientProperty("Building",b);
		respond.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		respond.setFocusable(false);
		respond.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		temp.add(respond);
		right.add(temp);
		
		CitizensPane cBts = new CitizensPane();
		JPanel panel = cBts.avaP;
		for(JButton bt : occupants)
			panel.add(bt);
		
		right.add(cBts.avaC);
		command.info.add(right);
		panel.revalidate();
		panel.repaint();
		
//		cb.addItemListener(new ItemListener() {
//			
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange() == ItemEvent.SELECTED) {
//					if(cb.getSelectedIndex()!=0) {
//						citizenpressed cp = new citizenpressed(command, occ.get(cb.getSelectedIndex()-1));
//						cp.actionPerformed(null);
//				}
//					cb.setSelectedIndex(0);
//				}
//			}
//		});
//	//	System.out.println("here" + x.getText());
//		command.info.add(cb);
		left.revalidate();
		left.repaint();
		right.revalidate();
		right.repaint();
		command.info.repaint();
		command.info.revalidate();
	}

}
