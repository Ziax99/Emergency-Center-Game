package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import view.GUI;

public class Info extends JPanel{

	JPanel text, combo;
	
	public Info() {
		setBackground(GUI.bg);
		setPreferredSize(new Dimension(1000, 500));
		setMaximumSize(new Dimension(1000, 500));
		setBorder(BorderFactory.createTitledBorder(null, "Info", 0, 0, new Font(Font.SERIF, 0, 20), Color.WHITE));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		text = new JPanel();
		text.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
	}
}
