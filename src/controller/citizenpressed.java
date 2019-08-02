package controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import model.people.Citizen;

public class citizenpressed implements ActionListener {

	CommandCenter command;
	Citizen c;
	
	public citizenpressed(CommandCenter command, Citizen c) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		command.info.removeAll();
		command.info.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel b = new JLabel("<html>" + c.toString().replaceAll("\n", "<br>") + "<br>National ID: " + c.getNationalID() + "<br>" + "Age: " + c.getAge()
				+ (c.getDisaster() == null? "" : "<br>" + c.getDisaster().toString().replaceAll("\n", "<br>") + "</html>"));
		b.setForeground(Color.white);
		b.setFont(new Font(Font.SERIF, 0, 20));
		command.info.add(b);
		command.info.repaint();
		command.info.revalidate();
	}
}